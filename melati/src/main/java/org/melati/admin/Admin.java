/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2000 William Chesters
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * Melati is free software; Permission is granted to copy, distribute
 * and/or modify this software under the terms either:
 *
 * a) the GNU General Public License as published by the Free Software
 *    Foundation; either version 2 of the License, or (at your option)
 *    any later version,
 *
 *    or
 *
 * b) any version of the Melati Software License, as published
 *    at http://melati.org
 *
 * You should have received a copy of the GNU General Public License and
 * the Melati Software License along with this program;
 * if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA to obtain the
 * GNU General Public License and visit http://melati.org to obtain the
 * Melati Software License.
 *
 * Feel free to contact the Developers of Melati (http://melati.org),
 * if you would like to work out a different arrangement than the options
 * outlined here.  It is our intention to allow Melati to be used by as
 * wide an audience as possible.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.admin;

import java.net.URLEncoder;

import java.util.Vector;
import java.util.Enumeration;

import org.melati.servlet.TemplateServlet;
import org.melati.MelatiContext;
import org.melati.InvalidUsageException;
import org.melati.template.TemplateContext;
import org.melati.template.FormParameterException;

import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.Capability;
import org.melati.poem.Column;
import org.melati.poem.ColumnInfo;
import org.melati.poem.ColumnInfoTable;
import org.melati.poem.Database;
import org.melati.poem.DeletionIntegrityPoemException;
import org.melati.poem.Field;
import org.melati.poem.FieldAttributes;
import org.melati.poem.Initialiser;
import org.melati.poem.Persistent;
import org.melati.poem.PoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemType;
import org.melati.poem.PoemTypeFactory;
import org.melati.poem.ReferencePoemType;
import org.melati.poem.Table;
import org.melati.poem.TableInfo;
import org.melati.poem.TableInfoTable;
import org.melati.poem.ValidationPoemException;

import org.melati.util.EnumUtils;
import org.melati.util.MappedEnumeration;

/**
 * FIXME getting a bit big, wants breaking up
 */

public class Admin extends TemplateServlet {

  protected Persistent create(Table table, final TemplateContext context, 
  final MelatiContext melatiContext) {
    return table.create(
    new Initialiser() {
      public void init(Persistent object)
      throws AccessPoemException, ValidationPoemException {
        melatiContext.getMelati().extractFields(context, object);
      }
    });
  }

  protected final TemplateContext adminTemplate(TemplateContext context, 
  String name) {
    context.setTemplateName("admin/" + name);
    return context;
  }

  // return the 'Main' admin frame
  protected TemplateContext mainTemplate(TemplateContext context)
 {
    context.put("database", PoemThread.database());
    return adminTemplate(context, "Main.wm");
  }

  // return top template
  protected TemplateContext topTemplate(TemplateContext context)
  throws PoemException {
    context.put("database", PoemThread.database());
    return adminTemplate(context, "Top.wm");
  }

  // return the 'bottom' admin page
  protected TemplateContext bottomTemplate(TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    context.put("database", PoemThread.database());
    final Table table = melatiContext.getTable();
    context.put("table", table);
    return adminTemplate(context, "Bottom.wm");
  }

  // return the 'left' admin page
  protected TemplateContext leftTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    context.put("database", PoemThread.database());
    final Table table = melatiContext.getTable();
    context.put("table", table);
    return adminTemplate(context, "Left.wm");
  }

  // return primary select template
  protected TemplateContext primarySelectTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(primarySelect(context, melatiContext), 
    "PrimarySelect.wm");
  }

  protected TemplateContext primarySelect(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    final Table table = melatiContext.getTable();
    context.put("table", table);

    final Database database = table.getDatabase();
    context.put("database", database);

    Field primaryCriterion;

    Column column = table.primaryCriterionColumn();
    if (column != null) {
      String sea = context.getForm("field_" + column.getName());
      primaryCriterion = new Field(
      sea == null || sea.equals("") ? null :
      column.getType().rawOfString(sea),
      new BaseFieldAttributes(column,
      column.getType().withNullable(true)));
    }
    else
    primaryCriterion = null;

    context.put("primaryCriterion", primaryCriterion);
    return context;
  }

  // return select template (a selection of records from a table)
  protected TemplateContext selectionTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws FormParameterException {
    return adminTemplate(selection(context, melatiContext), "Selection.wm");
  }

  // return select template (a selection of records from a table)
  protected TemplateContext selectionRightTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws FormParameterException {
    return adminTemplate(selection(context, melatiContext), 
    "SelectionRight.wm");
  }

  protected TemplateContext selection(TemplateContext context, 
  MelatiContext melatiContext)
  throws FormParameterException {
    final Table table = melatiContext.getTable();
    context.put("table", table);

    final Database database = table.getDatabase();
    context.put("database", database);

    // sort out search criteria

    final Persistent criteria = table.newPersistent();

    Vector whereClause = new Vector();

    for (Enumeration c = table.columns(); c.hasMoreElements();) {
      Column column = (Column)c.nextElement();
      String name = "field_" + column.getName();
      String string = context.getForm(name);
      if (string != null && !string.equals("")) {
        column.setRaw_unsafe(criteria, column.getType().rawOfString(string));

        // FIXME needs to work for dates?
        whereClause.addElement(name + "=" + URLEncoder.encode(string));
        //        whereClause.addElement(column.eqClause(string));
      }
    }

    context.put("whereClause",
    EnumUtils.concatenated("&", whereClause.elements()));

    // sort out ordering (FIXME this is a bit out of control)

    PoemType searchColumnsType =
    new ReferencePoemType(database.getColumnInfoTable(), true) {
      protected Enumeration _possibleRaws() {
        return
        new MappedEnumeration(table.getSearchCriterionColumns()) {
          public Object mapped(Object column) {
            return ((Column)column).getColumnInfo().getTroid();
          }
        };
      }
    };

    Vector orderingNames = new Vector();
    Vector orderClause = new Vector();
    for (int o = 1; o <= 2; ++o) {
      String name = "order-" + o;
      String orderColumnIDString = context.getForm("field_" + name);
      Integer orderColumnID = null;
      if (orderColumnIDString != null && !orderColumnIDString.equals("")) {
        orderColumnID =
        (Integer)searchColumnsType.rawOfString(orderColumnIDString);
        ColumnInfo info =
        (ColumnInfo)searchColumnsType.cookedOfRaw(orderColumnID);
        orderingNames.addElement(database.quotedName(info.getName()));
        orderClause.addElement(name+"="+orderColumnIDString);
      }
    }

    String orderBySQL = null;
    if (orderingNames.elements().hasMoreElements())
    orderBySQL = EnumUtils.concatenated(", ", orderingNames.elements());
    context.put("orderClause",
    EnumUtils.concatenated("&", orderClause.elements()));

    int start = 0;
    String startString = context.getForm("start");
    if (startString != null) {
      try {
        start = Math.max(0, Integer.parseInt(startString));
      }
      catch (NumberFormatException e) {
        //FIXME - surely not a PoemException
        throw new FormParameterException("start", "param to must be an Integer");
      }
    }

    context.put("results", table.selection(table.whereClause(criteria),
    orderBySQL, false, start, 20));

    return context;
  }

  // return the 'navigation' admin page
  protected TemplateContext navigationTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    context.put("database", PoemThread.database());
    final Table table = melatiContext.getTable();
    context.put("table", table);
    return adminTemplate(context, "Navigation.wm");
  }

  protected TemplateContext popupTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(popup(context, melatiContext), "PopupSelect.wm");
  }


  protected TemplateContext popup(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    final Table table = melatiContext.getTable();
    context.put("table", table);

    final Database database = table.getDatabase();
    context.put("database", database);

    // sort out search criteria

    final Persistent criteria = table.newPersistent();

    MappedEnumeration criterias =
    new MappedEnumeration(table.getSearchCriterionColumns()) {
      public Object mapped(Object c) {
        return ((Column)c).asField(criteria).withNullable(true);
      }
    };

    context.put("criteria", EnumUtils.vectorOf(criterias));

    // sort out ordering (FIXME this is a bit out of control)

    PoemType searchColumnsType =
    new ReferencePoemType(database.getColumnInfoTable(), true) {
      protected Enumeration _possibleRaws() {
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

    for (int o = 1; o <= 2; ++o) {
      String name = "order-" + o;
      String orderColumnIDString = context.getForm("field_" + name);
      Integer orderColumnID = null;
      if (orderColumnIDString != null && !orderColumnIDString.equals("")) {
        orderColumnID =
        (Integer)searchColumnsType.rawOfString(orderColumnIDString);
        ColumnInfo info =
        (ColumnInfo)searchColumnsType.cookedOfRaw(orderColumnID);
        orderingNames.addElement(database.quotedName(info.getName()));
      }

      orderings.addElement(
      new Field(orderColumnID,
      new BaseFieldAttributes(name, searchColumnsType)));
    }

    context.put("orderings", orderings);

    return context;
  }

  protected TemplateContext selectionWindowTemplate(TemplateContext context, 
  MelatiContext melatiContext)
  throws PoemException {
    context.put("database", PoemThread.database());
    context.put("table", melatiContext.getTable());
    return adminTemplate(context, "SelectionWindow.wm");
  }

  // return primary select template
  protected TemplateContext selectionWindowPrimarySelectTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(primarySelect(context, melatiContext), 
    "SelectionWindowPrimarySelect.wm");
  }

  // return select template (a selection of records from a table)
  protected TemplateContext selectionWindowSelectionTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws FormParameterException {
    return adminTemplate(selection(context, melatiContext), 
    "SelectionWindowSelection.wm");
  }

  protected TemplateContext columnCreateTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {

    final ColumnInfoTable cit = melatiContext.getDatabase().getColumnInfoTable();
    final Column tic = cit.getTableinfoColumn();
    final Column typeColumn = cit.getTypefactoryColumn();

    Enumeration columnInfoFields =
    new MappedEnumeration(cit.getDetailDisplayColumns()) {
      public Object mapped(Object column) {
        if (column == typeColumn)
        return new Field(PoemTypeFactory.STRING.getCode(),
        typeColumn);
        else
        return new Field((Object)null, (FieldAttributes)column);
      }
    };

    context.put("columnInfoFields", columnInfoFields);

    return adminTemplate(context, "CreateColumn.wm");
  }

  protected TemplateContext tableCreateTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    Database database = melatiContext.getDatabase();

    // Compose field for naming the TROID column: the display name and
    // description are redundant, since they not used in the template

    Field troidNameField = new Field(
    "id",
    new BaseFieldAttributes(
    "troidName", "Troid column", "Name of TROID column",
    database.getColumnInfoTable().getNameColumn().getType(),
    20, 1, null, false, true, true));

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

  protected TemplateContext tableCreate_doitTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    Database database = melatiContext.getDatabase();
    database.addTableAndCommit(
    (TableInfo)create(database.getTableInfoTable(), context, melatiContext),
    context.getForm("field_troidName"));

    return adminTemplate(context, "CreateTable_doit.wm");
  }

  protected TemplateContext columnCreate_doitTemplate
  (final TemplateContext context, final MelatiContext melatiContext)
  throws PoemException {

    Database db = melatiContext.getDatabase();

    ColumnInfo columnInfo =
    (ColumnInfo)db.getColumnInfoTable().create(
    new Initialiser() {
      public void init(Persistent object)
      throws AccessPoemException, ValidationPoemException {
        melatiContext.getMelati().extractFields(context, object);
      }
    });

    columnInfo.getTableinfo().actualTable().addColumnAndCommit(columnInfo);

    return adminTemplate(context, "CreateTable_doit.wm");
  }

  protected TemplateContext editingTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    melatiContext.getObject().assertCanRead();
    context.put("object", melatiContext.getObject());
    Database database = melatiContext.getDatabase();
    context.put("database", database);
    context.put("table", melatiContext.getTable());
    return context;
  }

  protected TemplateContext rightTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(editingTemplate(context, melatiContext), "Right.wm");
  }

  protected TemplateContext editHeaderTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(editingTemplate(context, melatiContext),
    "EditHeader.wm");
  }

  protected TemplateContext editTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    return adminTemplate(editingTemplate(context, melatiContext), "Edit.wm");
  }

  protected TemplateContext addTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {

    context.put("table", melatiContext.getTable());

    Enumeration columns = melatiContext.getTable().columns();
    Vector fields = new Vector();
    while (columns.hasMoreElements()) {
      Column column = (Column)columns.nextElement();
      String stringValue = context.getForm("field_" + column.getName());
      Object value = null;
      if (stringValue != null)
      value = column.getType().rawOfString(stringValue);
      fields.add(new Field(value, column));
    }
    context.put("fields", fields.elements());

    return adminTemplate(context, "Add.wm");
  }

  protected TemplateContext updateTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    melatiContext.getMelati().extractFields(context, melatiContext.getObject());
    return adminTemplate(context, "Update.wm");
  }

  protected TemplateContext addUpdateTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    create(melatiContext.getTable(), context, melatiContext);
    return adminTemplate(context, "Update.wm");
  }

  protected TemplateContext deleteTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    try {
      melatiContext.getObject().deleteAndCommit();
      return adminTemplate(context, "Update.wm");
    }
    catch (DeletionIntegrityPoemException e) {
      context.put("object", e.object);
      context.put("references", e.references);
      return adminTemplate(context, "DeleteFailure.wm");
    }
  }

  protected TemplateContext duplicateTemplate(TemplateContext context, MelatiContext melatiContext)
  throws PoemException {
    // FIXME the ORIGINAL object is the one that will get edited when the
    // update comes in from Edit.wm, because it will be identified from
    // the path info!

    Persistent dup = melatiContext.getObject().duplicated();
    melatiContext.getMelati().extractFields(context, dup);
    dup.getTable().create(dup);
    context.put("object", dup);
    return adminTemplate(context, "Update.wm");
  }

  protected TemplateContext modifyTemplate
  (TemplateContext context, MelatiContext melatiContext)
  throws FormParameterException {
    String action = melatiContext.getRequest().getParameter("action");
    if ("Update".equals(action))
    return updateTemplate(context, melatiContext);
    else if ("Delete".equals(action))
    return deleteTemplate(context, melatiContext);
    else if ("Duplicate".equals(action))
    return duplicateTemplate(context, melatiContext);
    else
    throw new FormParameterException
    ("action", "bad action from Edit.wm: " + action);
  }

  protected TemplateContext uploadTemplate(TemplateContext context)
  throws PoemException {
    context.put("field", context.getForm("field"));
    return adminTemplate(context, "Upload.wm");
  }


  protected TemplateContext doTemplateRequest
  (MelatiContext melatiContext, TemplateContext context)
  throws Exception {
    Capability admin = PoemThread.database().getCanAdminister();
    AccessToken token = PoemThread.accessToken();
    if (!token.givesCapability(admin))
    throw new AccessPoemException(token, admin);

    context.put("admin", melatiContext.getAdminUtils());
    if (melatiContext.getObject() != null) {
      if (melatiContext.getMethod().equals("Right"))
      return rightTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("EditHeader"))
      return editHeaderTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Edit"))
      return editTemplate(context, melatiContext);
      else if (melatiContext.getMethod().equals("Update"))
      return modifyTemplate(context, melatiContext);
      else if (melatiContext.getObject() instanceof AdminSpecialised) {
        TemplateContext it =
        ((AdminSpecialised)melatiContext.getObject()).adminHandle(
        melatiContext, melatiContext.getHTMLMarkupLanguage());
        if (it != null) return it;
      }
    }
    else if (melatiContext.getTable() != null) {
      if (melatiContext.getMethod().equals("Bottom"))
      return bottomTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Left"))
      return leftTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("PrimarySelect"))
      return primarySelectTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Selection"))
      return selectionTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("SelectionRight"))
      return selectionRightTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Navigation"))
      return navigationTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("PopUp"))
      return popupTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("SelectionWindow"))
      return selectionWindowTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("SelectionWindowPrimarySelect"))
      return selectionWindowPrimarySelectTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("SelectionWindowSelection"))
      return selectionWindowSelectionTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Add"))
      return addTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("AddUpdate"))
      return addUpdateTemplate(context, melatiContext);
    }
    else {
      if (melatiContext.getMethod().equals("Main"))
      return mainTemplate(context);
      if (melatiContext.getMethod().equals("Top"))
      return topTemplate(context);
      if (melatiContext.getMethod().equals("Create"))
      return tableCreateTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Create_doit"))
      return tableCreate_doitTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("CreateColumn"))
      return columnCreateTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("CreateColumn_doit"))
      return columnCreate_doitTemplate(context, melatiContext);
      if (melatiContext.getMethod().equals("Upload"))
      return uploadTemplate(context);
    }

    throw new InvalidUsageException(this, melatiContext);
  }
}
