package org.melati.doc.example.contacts;

import org.melati.MelatiContext;
import org.melati.template.webmacro.WebmacroMelatiServlet;
import org.melati.poem.Field;

import org.webmacro.servlet.WebContext;
import org.webmacro.WebMacroException;

public class Search extends WebmacroMelatiServlet {

  public String handle( MelatiContext melati, WebContext context )
  throws WebMacroException {

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
    return "doc/example/contacts/Search.wm";
  }

  public String getLogicalDatabase
  (MelatiContext melatiContext, String logicalDatabase) {
    return "contacts";
  }
}
