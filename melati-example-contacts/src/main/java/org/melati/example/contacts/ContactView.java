package org.melati.doc.example.contacts;

import org.melati.doc.example.*;
import org.melati.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.broker.*;
import java.util.*;
import java.text.*;

public class ContactView extends MelatiServlet {

  protected Template handle(WebContext context, Melati melati)
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
            Melati.extractFields(context,contact);
            db.getContactTable().create(contact);
          } else {  
            Melati.extractFields(context,contact);
          }
          deleteCategories(db,contact);
          String[] categories = context.getRequest().getParameterValues("field_category");
          if (categories != null) {
            for (int i=0; i< categories.length; i++) {
              ContactCategory cat = (ContactCategory)db.getContactCategoryTable().newPersistent();
              cat.setContact(contact);
              cat.setCategoryTroid(new Integer(categories[i]));
              db.getContactCategoryTable().create(cat);
            }
          }
          context.getResponse().sendRedirect("/melati/org.melati.doc.example.contacts.Search/contacts");
          return null;
        }
        //  delete a record
        else if (melati.getMethod().equals("Delete")) {
          deleteCategories(db,contact);
          contact.deleteAndCommit();
          context.getResponse().sendRedirect("/melati/org.melati.doc.example.contacts.Search/contacts");
          return null;
        }
        //  view an existing record
        else if (melati.getMethod().equals("View")) {
        }
        else { throw new HandlerException("Invalid Method");}
        context.put("contact",contact);
        context.put("categories",db.getCategoryTable().selection());
        return getTemplate("doc/example/contacts/ContactView.wm");
      }
      
      public void deleteCategories(ContactsDatabase db, Contact contact) {
        db.sqlUpdate("DELETE FROM contactcategory WHERE contact = " + contact.getTroid());
      }
        

}
