package org.melati.doc.example.contacts;

import org.melati.Melati;
import org.melati.servlet.MelatiContext;
import org.melati.servlet.PathInfoException;
import org.melati.poem.*;
import org.melati.template.webmacro.WebmacroMelatiServlet;
import org.melati.poem.Field;
import org.melati.MelatiUtil;

import org.webmacro.servlet.WebContext;
import org.webmacro.WebMacroException;

public class Search extends WebmacroMelatiServlet {

  public String handle( Melati melati, WebContext context )
  throws WebMacroException {

    ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
    String name = MelatiUtil.getFormNulled(melati.getTemplateContext(),
                                            "field_name");
    Integer category = MelatiUtil.getFormInteger(melati.getTemplateContext(),
                                                "field_category");
    String submit = MelatiUtil.getFormNulled(melati.getTemplateContext(),
                                              "submit");
    context.put("name",new Field(name, db.getContactTable().getNameColumn()));
    context.put("category", new Field
               (category, db.getContactCategoryTable().getCategoryColumn()));

    String where = "";
    if (name != null) where += "\"name\" = '" + name + "' ";
    if (category != null) {
      if (!where.equals("")) where += " AND ";
      where += "exists (SELECT id FROM contactcategory WHERE \"category\" = " + 
      category.toString() + " AND contact = contact.id)";
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
