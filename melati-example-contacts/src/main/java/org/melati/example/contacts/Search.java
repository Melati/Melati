package org.melati.example.contacts;

import org.melati.Melati;
import org.melati.template.TemplateContext;
import org.melati.servlet.MelatiContext;
import org.melati.servlet.PathInfoException;
import org.melati.poem.Field;
import org.melati.MelatiUtil;


 /**
  *  Example servlet to search contacts and display them.
  *
  **/
public class Search extends ContactsServlet {

  protected String doTemplateRequest(Melati melati, TemplateContext context)
     throws Exception {

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
  
  protected MelatiContext melatiContext(Melati melati)
  throws PathInfoException {
    return melatiContextWithLDB(melati,"contacts");
  }
}
