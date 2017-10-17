package org.melati.example.contacts;

import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.poem.Column;
import org.melati.poem.Field;
import org.melati.servlet.Form;
import org.melati.servlet.PathInfoException;
import org.melati.template.ServletTemplateContext;


 /**
  *  Example servlet to search contacts and display them.
  *
  **/
public class Search extends ContactsServlet {
  private static final long serialVersionUID = 1L;

  protected String doTemplateRequest(Melati melati, ServletTemplateContext context)
     throws Exception {

    ContactsDatabase db = (ContactsDatabase)melati.getDatabase();
    String name = Form.getFieldNulled(melati.getServletTemplateContext(),
    "field_name");
    Integer category = Form.getIntegerField(melati.getServletTemplateContext(),
                                                "field_category");
    String search = Form.getFieldNulled(melati.getServletTemplateContext(),
        "search");
    Column<String> nameColumn = db.getContactTable().getNameColumn();
    Column<Integer> contactColumn = db.getContactCategoryTable().getContactColumn();
    Column<Integer> categoryColumn = db.getContactCategoryTable().getCategoryColumn();
    context.put("name",new Field<String>(name, nameColumn));
    context.put("category", new Field<Integer>
               (category, categoryColumn));

    String where = "";
    if (name != null) where += nameColumn.quotedName() + " = '" + name + "' ";
    if (category != null) {
      if (!where.equals("")) where += " AND ";
      where += "exists (SELECT " + 
               db.getContactTable().troidColumn().quotedName() + // "id"
          " FROM " +  db.getContactCategoryTable().quotedName() +   // "contactcategory " +
               " WHERE " + categoryColumn.quotedName() + " = " + category.toString() + 
               " AND "  + contactColumn.quotedName() + " =  " + 
                   db.getContactTable().quotedName() + "." + db.getContactTable().troidColumn().quotedName() +")";
    }
    if (search != null) {
      context.put("results", db.getContactTable().selection(where));
    } else {
      System.err.println("search not clicked");
    }
    // The file extension is added by the ServletTemplateEngine
    return "org/melati/example/contacts/Search";
  }
  
  protected PoemContext poemContext(Melati melati)
  throws PathInfoException {
    return poemContextWithLDB(melati,"contacts");
  }
}
