package org.melati.admin;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.melati.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.broker.*;

class MethodRef {
  String database = null;
  String table = null;
  Integer troid = null;
  String method = null;

  MethodRef(String pathInfo) throws PathInfoMethodRefException {
    try {
      String[] parts = StringUtils.split(pathInfo, '/');

      method = parts[parts.length - 1];

      switch (parts.length - 1) {
        case 4:
          troid = new Integer(parts[3]);
        case 3:
          table = parts[2];
        default:
          database = parts[1];  // provoke exception if 0-length
      }
    }
    catch (Exception e) {
      throw new PathInfoMethodRefException(null);
    }
  }
}

public class Admin extends MelatiServlet {

  protected Table tableFromPathInfo(MethodRef ref) throws PoemException {
    if (ref.table == null) throw new NullPointerException();
    Database database = PoemThread.database();
    database.logSQL = true;
    return database.getTable(ref.table);
  }

  protected Persistent objectFromPathInfo(MethodRef ref) throws PoemException {
    if (ref.troid == null) throw new NullPointerException();
    return tableFromPathInfo(ref).getObject(ref.troid);
  }

  protected final Template adminTemplate(WebContext context, String name)
      throws ResourceUnavailableException {
    return (Template)context.getBroker().getValue(TemplateProvider.TYPE,
                                                  "admin/" + name);
  }

  protected Template tablesViewTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException {
    context.put("tables", PoemThread.database().tables());
    return adminTemplate(context, "Tables.wm");
  }

  protected Template tableListTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException {
    Table table = tableFromPathInfo(ref);
    context.put("table", table);
    context.put("objects", table.selection());
    return adminTemplate(context, "List.wm");
  }

  protected Template editTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException {
    context.put("object", objectFromPathInfo(ref));
    return adminTemplate(context, "Edit.wm");
  }

  protected Template updateTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException {
    Persistent object = objectFromPathInfo(ref);
    HttpServletRequest request = context.getRequest();

    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String value = request.getParameter("field-" + column.getName());
      if (value != null)
        if (value.equals(""))
          column.setIdent(object, null);
        else
          column.setIdentString(object, value);
      else
        // FIXME gross hack
        column.setIdent(object, Boolean.FALSE);
    }

    return adminTemplate(context, "Update.wm");
  }

  protected Template deleteTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException {
    try {
      objectFromPathInfo(ref).deleteAndCommit();
      return adminTemplate(context, "Delete.wm");
    }
    catch (DeletionIntegrityPoemException e) {
      context.put("object", e.object);
      context.put("references", e.references);
      return adminTemplate(context, "DeleteFailure.wm");
    }
  }

   protected Template duplicateTemplate(WebContext context, MethodRef ref)
       throws ResourceUnavailableException, PoemException {
     objectFromPathInfo(ref).duplicated();
     return adminTemplate(context, "Duplicate.wm");
   }

  protected Template modifyTemplate(WebContext context, MethodRef ref)
      throws ResourceUnavailableException, PoemException, HandlerException {
    String action = context.getRequest().getParameter("action");
    if ("Update".equals(action))
      return updateTemplate(context, ref);
    else if ("Delete".equals(action))
      return deleteTemplate(context, ref);
    else if ("Duplicate".equals(action))
      return duplicateTemplate(context, ref);
    else
      throw new HandlerException("bad action from Edit.wm: " + action);
  }

  public Template template(WebContext context) throws HandlerException {
    try {
      String pathInfo = context.getRequest().getPathInfo();
      MethodRef ref = new MethodRef(pathInfo);
      context.put("admin",
                  new AdminUtils(context.getRequest().getServletPath(),
                                 ref.database));

      if (ref.troid != null) {
        if (ref.method.equals("Edit"))
          return editTemplate(context, ref);
        else if (ref.method.equals("Update"))
          return modifyTemplate(context, ref);
      }
      else if (ref.table != null) {
        if (ref.method.equals("View"))
          return tableListTemplate(context, ref);
      }
      else {
        if (ref.method.equals("View"))
          return tablesViewTemplate(context, ref);
      }

      throw new InvalidPathInfoException(pathInfo);
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new HandlerException("Bollocks: " + e);
    }
  }
}
