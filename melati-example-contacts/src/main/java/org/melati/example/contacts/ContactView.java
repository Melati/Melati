package org.melati.doc.example.contacts;

import java.io.IOException;

import org.melati.Melati;
import org.melati.MelatiUtil;
import org.melati.servlet.TemplateServlet;
import org.melati.servlet.MelatiContext;
import org.melati.servlet.PathInfoException;
import org.melati.template.TemplateContext;


 /**
  *  Example servlet to display or edit a contact and its categories.
  *
  **/

public class ContactView extends TemplateServlet {

  protected String doTemplateRequest(Melati melati, TemplateContext context)
            throws Exception {

    ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
    Contact contact = (Contact)melati.getObject();
    // used to display a blank page for new data entry
    if (melati.getMethod().equals("Insert")) {
      contact = (Contact) db.getContactTable().newPersistent();
    }
    // used to update or insert a record
    else if (melati.getMethod().equals("Update")) {
      if (contact == null) {
        contact = (Contact) db.getContactTable().newPersistent();
        MelatiUtil.extractFields(melati.getTemplateContext(),contact);
        db.getContactTable().create(contact);
      } else {
        MelatiUtil.extractFields(melati.getTemplateContext(),contact);
      }
      deleteCategories(db,contact);

      String[] categories = melati.getRequest().
                               getParameterValues("field_category");
      if (categories != null) {
        for (int i=0; i< categories.length; i++) {
          ContactCategory cat =
          (ContactCategory)db.getContactCategoryTable().newPersistent();
          cat.setContact(contact);
          cat.setCategoryTroid(new Integer(categories[i]));
          db.getContactCategoryTable().create(cat);
        }
      }
      try {
        melati.getResponse().sendRedirect
        ("/melati/org.melati.doc.example.contacts.Search/contacts");
      } catch (IOException e) {
        throw new Exception(e.toString());
      }
      return null;
    }
    //  delete a record
    else if (melati.getMethod().equals("Delete")) {
      deleteCategories(db,contact);
      contact.deleteAndCommit();
      try {
        melati.getResponse().sendRedirect
        ("/melati/org.melati.doc.example.contacts.Search/contacts");
      } catch (IOException e) {
        throw new Exception(e.toString());
      }
      return null;
    }
    //  view an existing record
    else if (melati.getMethod().equals("View")) {
    }
    else { 
       throw new Exception("Invalid Method");
    }
    context.put("contact",contact);
    context.put("categories",db.getCategoryTable().selection());
    // The file extension is added by the template engine
    return "org/melati/doc/example/contacts/ContactView";
  }

  public void deleteCategories(ContactsDatabase db, Contact contact) {

    db.sqlUpdate("DELETE FROM " + db.quotedName("contactcategory") + 
                 " WHERE " + db.quotedName("contact") + " = " + 
                 contact.getTroid());
  }

  protected MelatiContext melatiContext(Melati melati)
  throws PathInfoException {
    return melatiContextWithLDB(melati,"contacts");
  }

}


