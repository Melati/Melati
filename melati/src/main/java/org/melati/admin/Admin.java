package org.melati.admin;

import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;
import org.melati.*;
import org.melati.util.*;
import org.melati.poem.*;
import org.webmacro.*;
import org.webmacro.util.*;
import org.webmacro.servlet.*;
import org.webmacro.engine.*;
import org.webmacro.resource.*;
import org.webmacro.broker.*;

/**
 * FIXME getting a bit big, wants breaking up
 */

public class Admin extends MelatiServlet {

  protected Persistent create(Table table, final WebContext context) {
    return table.create(
        new Initialiser() {
          public void init(Persistent object)
              throws AccessPoemException, ValidationPoemException {
            copyFields(context, object);
          }
        });
  }

  protected final Template adminTemplate(WebContext context, String name)
      throws NotFoundException, InvalidTypeException {
    return getTemplate("admin/" + name);
  }

  protected Template tablesViewTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    context.put("database", PoemThread.database());
    return adminTemplate(context, "Tables.wm");
  }

  protected Template tableCreateTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    Database database = melati.getDatabase();

    // Compose field for naming the TROID column: the display name and
    // description are redundant, since they not used in the template

    Field troidNameField = new Field(
        "id",
        new BaseFieldAttributes(
            "troidName", "Troid column", "Name of TROID column",
	    database.getColumnInfoTable().getNameColumn().getType(), null));

    context.put("troidNameField", troidNameField);

    Table tit = database.getTableInfoTable();
    Enumeration tableInfoFields =
	new MappedEnumeration(tit.columns()) {
	  public Object mapped(Object column) {
	    return new Field((Object)null, (Column)column);
	  }
	};

    context.put("tableInfoFields", tableInfoFields);

    return adminTemplate(context, "CreateTable.wm");
  }

  protected Template tableCreate_doitTemplate(WebContext context,
                                              Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    Database database = melati.getDatabase();
    database.addTableAndCommit(
        (TableInfo)create(database.getTableInfoTable(), context),
        context.getForm("field-troidName"));

    return adminTemplate(context, "CreateTable_doit.wm");
  }

  protected Template tableListTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException,
             HandlerException {
    final Table table = melati.getTable();
    context.put("table", table);

    final Database database = table.getDatabase();

    // sort out search criteria

    final Data data = table.newData();

    for (Enumeration c = table.columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String value = context.getForm("field-" + column.getName());
      if (value != null && !value.equals(""))
        column.setIdentString(data, value);
    }

    context.put("criteria",
                new MappedEnumeration(table.getSearchCriterionColumns()) {
                  public Object mapped(Object c) {
                    Column column = (Column)c;
                    final PoemType nullable =
                        column.getType().withNullable(true);
                    return
                        new Field(column.getIdent(data), column) {
                          public PoemType getType() {
                            return nullable;
                          }
                        };
                  }
                });

    // sort out ordering (FIXME this is a bit out of control)

    PoemType searchColumnsType =
        new ReferencePoemType(database.getColumnInfoTable(), true) {
          protected Enumeration _possibleIdents() {
            return
                new MappedEnumeration(table.getSearchCriterionColumns()) {
                  public Object mapped(Object column) {
                    return ((Column)column).getColumnInfo().getTroid();
                  }
                };
          }
        };

    Vector orderingNames = new Vector();
    Vector orderings = new Vector();

    for (int o = 1; o <= 3; ++o) {
      String name = "order-" + o;
      String orderColumnIDString = context.getForm("field-" + name);
      Integer orderColumnID = null;
      if (orderColumnIDString != null && !orderColumnIDString.equals("")) {
        orderColumnID =
            (Integer)searchColumnsType.identOfString(orderColumnIDString);
        ColumnInfo info =
            (ColumnInfo)searchColumnsType.valueOfIdent(orderColumnID);
        orderingNames.addElement(database.quotedName(info.getName()));
      }

      orderings.addElement(
          new Field(orderColumnID,
                    new BaseFieldAttributes(name, searchColumnsType)));
    }

    context.put("orderings", orderings);

    String orderByClause = EnumUtils.concatenated(", ",
                                                  orderingNames.elements());

    int start = 0;
    String startString = context.getForm("start");
    if (startString != null) {
      try {
        start = Math.max(0, Integer.parseInt(startString));
      }
      catch (NumberFormatException e) {
        throw new HandlerException("`start' param to `List' must be a number");
      }
    }

    context.put("objects", table.selection(table.whereClause(data),
                                           orderByClause, false, start, 20));

    return adminTemplate(context, "Select.wm");
  }

  protected Template columnCreateTemplate(WebContext context,
					  Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    
    final ColumnInfoTable cit = melati.getDatabase().getColumnInfoTable();
    final Column tic = cit.getTableinfoColumn();

    Enumeration columnInfoFields =
	new MappedEnumeration(cit.columns()) {
	  public Object mapped(Object column) {
	    if (column == tic)
	      column = new BaseFieldAttributes(
                  tic.getName(), tic.getDisplayName(), tic.getDescription(),
		  tic.getType(), tic.getRenderInfo());

	    return new Field((Object)null, (FieldAttributes)column);
	  }
	};

    context.put("columnInfoFields", columnInfoFields);

    return adminTemplate(context, "CreateColumn.wm");
  }

  protected Template columnCreate_doitTemplate(final WebContext context,
					       final Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {

    ColumnInfo columnInfo =
        (ColumnInfo)melati.getDatabase().getColumnInfoTable().create (
	    new Initialiser() {
	      public void init(Persistent object)
		  throws AccessPoemException, ValidationPoemException {
		((ColumnInfo)object).setTableinfoTroid(
                    melati.getTable().tableInfoID());
		copyFields(context, object);
	      }
	    });

    melati.getTable().addColumnAndCommit(columnInfo);

    return adminTemplate(context, "CreateTable_doit.wm");
  }

  protected Template editTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    melati.getObject().assertCanRead();
    context.put("object", melati.getObject());
    return adminTemplate(context, "Edit.wm");
  }

  protected Template addTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {

    context.put("table", melati.getTable());

    Enumeration fields =
        new MappedEnumeration(melati.getTable().columns()) {
          public Object mapped(Object column) {
            return new Field((Object)null, (Column)column);
          }
        };
    context.put("fields", fields);

    return adminTemplate(context, "Add.wm");
  }

  private void copyFields(WebContext context, Persistent object)
      throws PoemException {
    for (Enumeration c = object.getTable().columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String value = context.getForm("field-" + column.getName());
      if (value != null)
        if (value.equals(""))
          if (column.getType().getNullable())
            column.setIdent(object, null);
          else
            column.setIdentString(object, "");
        else
          column.setIdentString(object, value);
    }
  }

  protected Template updateTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    copyFields(context, melati.getObject());
    return adminTemplate(context, "Update.wm");
  }

  protected Template addUpdateTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    create(melati.getTable(), context);
    return adminTemplate(context, "Update.wm");
  }

  protected Template deleteTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException {
    try {
      melati.getObject().deleteAndCommit();
      return adminTemplate(context, "Delete.wm");
    }
    catch (DeletionIntegrityPoemException e) {
      context.put("object", e.object);
      context.put("references", e.references);
      return adminTemplate(context, "DeleteFailure.wm");
    }
  }

   protected Template duplicateTemplate(WebContext context, Melati melati)
       throws NotFoundException, InvalidTypeException, PoemException {
     // FIXME the ORIGINAL object is the one that will get edited when the
     // update comes in from Edit.wm, because it will be identified from
     // the path info!

     melati.getObject().duplicated();
     context.put("object", melati.getObject());
     return adminTemplate(context, "Edit.wm");
   }

  protected Template modifyTemplate(WebContext context, Melati melati)
      throws NotFoundException, InvalidTypeException, PoemException,
	     HandlerException {
    String action = context.getRequest().getParameter("action");
    if ("Update".equals(action))
      return updateTemplate(context, melati);
    else if ("Delete".equals(action))
      return deleteTemplate(context, melati);
    else if ("Duplicate".equals(action))
      return duplicateTemplate(context, melati);
    else
      throw new HandlerException("bad action from Edit.wm: " + action);
  }

  protected Template handle(WebContext context, Melati melati)
      throws PoemException, HandlerException {
    try {
      context.put("admin",
                  new AdminUtils(context.getRequest().getServletPath(),
                                 melati.getLogicalDatabaseName()));

      if (melati.getObject() != null) {
        if (melati.getMethod().equals("Edit"))
          return editTemplate(context, melati);
        else if (melati.getMethod().equals("Update"))
          return modifyTemplate(context, melati);
      }
      else if (melati.getTable() != null) {
        if (melati.getMethod().equals("View"))
          return tableListTemplate(context, melati);
        else if (melati.getMethod().equals("Add"))
          return addTemplate(context, melati);
        else if (melati.getMethod().equals("AddUpdate"))
          return addUpdateTemplate(context, melati);
        else if (melati.getMethod().equals("CreateColumn"))
          return columnCreateTemplate(context, melati);
        else if (melati.getMethod().equals("CreateColumn_doit"))
          return columnCreate_doitTemplate(context, melati);
      }
      else {
        if (melati.getMethod().equals("View"))
          return tablesViewTemplate(context, melati);
        else if (melati.getMethod().equals("Create"))
          return tableCreateTemplate(context, melati);
        else if (melati.getMethod().equals("Create_doit"))
          return tableCreate_doitTemplate(context, melati);
      }

      throw new InvalidUsageException(this, melati.getContext());
    }
    catch (PoemException e) {
      // we want to let these through untouched, since MelatiServlet handles
      // AccessPoemException specially ...
      throw e;
    }
    catch (Exception e) {
      e.printStackTrace();
      throw new HandlerException("Bollocks: " + e);
    }
  }
}
