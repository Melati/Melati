package org.melati.doc.example.contacts;

import org.melati.Melati;
import org.melati.servlet.MelatiContext;
import org.melati.servlet.PathInfoException;
import org.melati.poem.*;
import org.melati.template.webmacro.WebmacroMelatiServlet;
import org.melati.poem.Field;

import org.webmacro.servlet.WebContext;
import org.webmacro.WebMacroException;

public class Search extends WebmacroMelatiServlet {

  public String handle( Melati melati, WebContext context )
  throws WebMacroException {

    ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
    String name = context.getForm("field_name");
    String category = context.getForm("field_category");
    String submit = context.getForm("submit");
    Integer categoryInteger = null;
    if (category != null && !category.equals("")) 
    categoryInteger = new Integer(category);
    context.put("name",new Field(name, db.getContactTable().getNameColumn()));
    context.put("category", new Field
    (categoryInteger, db.getContactCategoryTable().getCategoryColumn()));

    String where = "";
    if (name != null && !name.equals("")) {
      where += "\"name\" = '" + name + "' ";
    }
    if (category != null && !category.equals("")) {
      if (!where.equals("")) where += " AND ";
      where += "exists (SELECT id FROM contactcategory WHERE \"category\" = " + 
      category + " AND contact = contact.id)";
    }
    if (submit != null) {
      context.put("results", db.getContactTable().selection(where));
    }
    return "doc/example/contacts/Search.wm";
  }
  
  protected MelatiContext melatiContext(Melati melati)
  throws PathInfoException {
    return melatiContextWithLDB(melati,"contacts");
  }
}
