package org.melati.doc.example.contacts;

import java.io.IOException;

import org.melati.Melati;
import org.melati.MelatiUtil;
import org.melati.servlet.MelatiContext;
import org.melati.servlet.PathInfoException;
import org.melati.template.webmacro.WebmacroMelatiServlet;
import org.melati.template.TemplateContext;

import org.webmacro.servlet.WebContext;
import org.webmacro.WebMacroException;

public class ContactView extends WebmacroMelatiServlet {

  public String handle( Melati melati, WebContext context )
  throws WebMacroException {

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
      String[] categories =
      context.getRequest().getParameterValues("field_category");
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
        throw new WebMacroException(e.toString());
      }
      return null;
    }
    //  delete a record
    else if (melati.getMethod().equals("Delete")) {
      deleteCategories(db,contact);
      contact.deleteAndCommit();
      try {
        context.getResponse().sendRedirect
        ("/melati/org.melati.doc.example.contacts.Search/contacts");
      } catch (IOException e) {
        throw new WebMacroException(e.toString());
      }
      return null;
    }
    //  view an existing record
    else if (melati.getMethod().equals("View")) {
    }
  else { throw new WebMacroException("Invalid Method");}
    context.put("contact",contact);
    context.put("categories",db.getCategoryTable().selection());
    return "doc/example/contacts/ContactView.wm";
  }

  public void deleteCategories(ContactsDatabase db, Contact contact) {
    db.sqlUpdate("DELETE FROM contactcategory WHERE contact = " + contact.getTroid());
  }

  protected MelatiContext melatiContext(Melati melati)
  throws PathInfoException {
    return melatiContextWithLDB(melati,"contacts");
  }

}
