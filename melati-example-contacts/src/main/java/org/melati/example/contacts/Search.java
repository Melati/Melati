package org.melati.example.contacts;

import org.melati.Melati;
import org.melati.template.ServletTemplateContext;
import org.melati.PoemContext;
import org.melati.servlet.PathInfoException;
import org.melati.poem.Field;
import org.melati.MelatiUtil;


 /**
  *  Example servlet to search contacts and display them.
  *
  **/
public class Search extends ContactsServlet {

  protected String doTemplateRequest(Melati melati, ServletTemplateContext context)
     throws Exception {

    ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
    String name = MelatiUtil.getFormNulled(melati.getServletTemplateContext(),
                                            "field_name");
    Integer category = MelatiUtil.getFormInteger(melati.getServletTemplateContext(),
                                                "field_category");
    String submit = MelatiUtil.getFormNulled(melati.getServletTemplateContext(),
                                              "submit");
    context.put("name",new Field(name, db.getContactTable().getNameColumn()));
    context.put("category", new Field
               (category, db.getContactCategoryTable().getCategoryColumn()));

    String where = "";
    if (name != null) where += "\"name\" = '" + name + "' ";
    if (category != null) {
      if (!where.equals("")) where += " AND ";
      where += "exists (SELECT \"id\" FROM \"contactcategory\" " + 
               "WHERE \"category\" = " + category.toString() + 
               " AND \"contact\" = \"contact\".\"id\")";
    }
    if (submit != null) {
      context.put("results", db.getContactTable().selection(where));
    }
    // The file extension is added by the TemplateEngine
    return "org/melati/example/contacts/Search";
  }
  
  protected PoemContext poemContext(Melati melati)
  throws PathInfoException {
    return poemContextWithLDB(melati,"contacts");
  }
}
