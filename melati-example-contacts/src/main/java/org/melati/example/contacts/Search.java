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

public class Search extends MelatiServlet {

  protected Template handle(WebContext context, Melati melati)
      throws PoemException, WebMacroException {
        
        ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
        String name = context.getForm("field_name");
        String category = context.getForm("field_category");
        String submit = context.getForm("submit");
        Integer categoryInteger = null;
        if (category != null && !category.equals("")) categoryInteger = new Integer(category);
        context.put("name",new Field(name, db.getContactTable().getNameColumn()));
        context.put("category", new Field(categoryInteger, db.getContactCategoryTable().getCategoryColumn()));
        
        String where = "";
        if (name != null && !name.equals("")) {
           where += "\"name\" = '" + name + "' ";
        }
        if (category != null && !category.equals("")) {
           if (!where.equals("")) where += " AND ";
           where += "exists (SELECT id FROM contactcategory WHERE \"category\" = " + category + " AND contact = contact.id)";
        }
        if (submit != null) {
          context.put("results", db.getContactTable().selection(where));
        }
        return getTemplate("doc/example/contacts/Search.wm");
      }

}
