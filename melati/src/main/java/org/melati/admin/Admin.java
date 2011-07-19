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
 *     William Chesters <williamc At paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 */

package org.melati.admin;

import java.util.Vector;
import java.util.Enumeration;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.HeadMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.melati.Melati;
import org.melati.PoemContext;
import org.melati.servlet.FormDataAdaptor;
import org.melati.servlet.InvalidUsageException;
import org.melati.servlet.Form;
import org.melati.servlet.TemplateServlet;
import org.melati.template.ServletTemplateContext;
import org.melati.template.FormParameterException;

import org.melati.poem.AccessToken;
import org.melati.poem.AccessPoemException;
import org.melati.poem.BaseFieldAttributes;
import org.melati.poem.Capability;
import org.melati.poem.Column;
import org.melati.poem.ColumnInfo;
import org.melati.poem.ColumnInfoTable;
import org.melati.poem.ColumnTypePoemType;
import org.melati.poem.Database;
import org.melati.poem.DeletionIntegrityPoemException;
import org.melati.poem.DisplayLevel;
import org.melati.poem.ExecutingSQLPoemException;
import org.melati.poem.Field;
import org.melati.poem.Initialiser;
import org.melati.poem.Persistent;
import org.melati.poem.PoemException;
import org.melati.poem.PoemThread;
import org.melati.poem.PoemTypeFactory;
import org.melati.poem.ReferencePoemType;
import org.melati.poem.Setting;
import org.melati.poem.Table;
import org.melati.poem.TableInfo;
import org.melati.poem.TableInfoTable;
import org.melati.poem.ValidationPoemException;

import org.melati.util.CountedDumbPagedEnumeration;
import org.melati.poem.util.EnumUtils;
import org.melati.poem.util.MappedEnumeration;
import org.melati.util.MelatiBugMelatiException;
import org.melati.util.MelatiIOException;
import org.melati.util.MelatiRuntimeException;

/**
 * Melati template servlet for database administration.
 * <p>
 * This class defines {@link #doTemplateRequest(Melati, ServletTemplateContext)}
 * and methods it calls to interpret requests, depending on the current table
 * and object, if any.
 * <p>
 * Java methods with names ending "<code>Template</code>" and taking a
 * {@link ServletTemplateContext} and {@link Melati} as arguments are generally
 * called by {@link #doTemplateRequest(Melati, ServletTemplateContext)}) to
 * implement corresponding request methods.
 * {@link #modifyTemplate(ServletTemplateContext, Melati)} and associated
 * methods are slight variations.
 * <p>
 * {@link #adminTemplate(String)} is called in all cases
 * to return the template path. The name of the template is usually the same as
 * the request method but not if the same template is used for more than one
 * method or the template served depends on how request processing proceeds.
 * <p>
 * These methods are called to modify the context:
 * <ul>
 * <li>{@link #popupSelect(ServletTemplateContext, Melati)}</li>
 * </ul>
 * 
 * TODO Review working of where clause for dates
 * TODO Move Nav icons into PrimarySelect
 * TODO Make Chooser JS agnostic
 * TODO Make Navigation JS agnostic
 * TODO Logout fails to work if remember me is ticked
 * TODO Order by field f orders on fields troid, not field ordering
 * TODO Enable non-paged output of selection by adding paged parameter to selectionTemplate
 * FIXME primaryDisplayTable should not be static as this messes with DB switching
 */

public class Admin extends TemplateServlet {
  private static final long serialVersionUID = 1L;

  private static String screenStylesheetURL = null;
  private static String primaryDisplayTable = null;
  private static String homepageURL = null;

  /**
   * Creates a row for a table using field data in a template context.
   */
  protected static Persistent create(Table table,
      final ServletTemplateContext context) {
    Persistent result = table.create(new Initialiser() {
      public void init(Persistent object) throws AccessPoemException,
          ValidationPoemException {
        Form.extractFields(context, object);
      }
    });
    result.postEdit(true);
    return result;
  }

  /**
   * Return the resource path for an admin template.
   */
  protected static String adminTemplate(String name) {
    return "org/melati/admin/" + name;
  }

  /**
   * @return a DSD for the database
   */
  protected static String dsdTemplate(ServletTemplateContext context) {
    // Webmacro security prevents access from within template

    // Note: getPackage() can return null dependant upon
    // the classloader so we have to chomp the class name

    String c = PoemThread.database().getClass().getName();
    int dot = c.lastIndexOf('.');
    String p = c.substring(0, dot);

    context.put("package", p);
    return adminTemplate("DSD");
  }

  /**
   * @return primary select template
   */
  protected static String primarySelectTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {
    final Table table = melati.getTable();

    Field primaryCriterion;

    Column column = table.primaryCriterionColumn();
    if (column != null) {
      String sea = context.getFormField("field_" + column.getName());
      primaryCriterion = new Field(
          sea == null ? 
           (
            melati.getObject() == null ? 
                null : column.getRaw(melati.getObject()))
          : column.getType().rawOfString(sea), 
          new BaseFieldAttributes(column,column.getType().withNullable(true)));
    } else
      primaryCriterion = null;

    context.put("primaryCriterion", primaryCriterion);
    return adminTemplate("PrimarySelect");
  }


  /**
   * Return template for a selection of records from a table.
   */
  protected static String selectionTemplate(ServletTemplateContext context,
      Melati melati) {
    String templateName = context.getFormField("template");
    if (templateName == null) {
      selection(context, melati, true);
      return adminTemplate("Selection");
    } else { 
      selection(context, melati, false);
      return adminTemplate(templateName);
    }
  }

  /**
   * Implements request to display a selection of records from a table in the
   * right hand pane.
   * 
   * @return SelectionRight template.
   */
  protected static String selectionRightTemplate(
      ServletTemplateContext context, Melati melati) {
    selection(context, melati, true);
    context.put("inRight", Boolean.TRUE);
    return adminTemplate("Selection");
  }

  /**
   * Modifies the context in preparation for serving a template to view a
   * selection of rows.
   * <p>
   * Any form fields in the context with names starting "field_" are assumed to
   * hold values that must be matched in selected rows (if not null).
   * <p>
   * An encoding of the resulting whereClause is added to the context. "AND" is
   * replaced by an &amp; separator.
   * <p>
   * A form field with name "start" is assumed to hold the number of the start
   * row in the result set. The default is zero. The next 20 rows are selected
   * and added as to the context as "results".
   * 
   * @return The modified context.
   */
  protected static ServletTemplateContext selection(
      ServletTemplateContext context, Melati melati, boolean paged) {
    final Table table = melati.getTable();

    final Database database = table.getDatabase();

    // sort out search criteria

    final Persistent criteria = table.newPersistent();

    Vector<Object> whereClause = new Vector<Object>();

    for (Enumeration<Column<?>> c = table.columns(); c.hasMoreElements();) {
      Column<?> column = c.nextElement();
      String name = "field_" + column.getName();
      String fieldValue = Form.getFieldNulled(context, name);
      if (fieldValue != null) {
        column
            .setRaw_unsafe(criteria, column.getType().rawOfString(fieldValue));

        // FIXME Needs to work for dates
        whereClause.addElement(name + "=" + melati.urlEncode(fieldValue));
      }
    }

    context.put("whereClause", EnumUtils.concatenated("&", whereClause
        .elements()));

    // sort out ordering 

    ReferencePoemType searchColumnsType = getSearchColumnsType(database, table);

    Vector<Object> orderings = new Vector<Object>();
    Vector<Object> orderClause = new Vector<Object>();

    
    for (int o = 1; o <= table.displayColumnsCount(DisplayLevel.summary); ++o) {
      String name = "field_order-" + o;
      String orderColumnIDString = Form.getFieldNulled(context, name);
      Integer orderColumnID;

      if (orderColumnIDString != null) {
        String toggleName = "field_order-" + o + "-toggle";
        String orderColumnSortOrderToggle = Form.getFieldNulled(context,
            toggleName);
        Boolean toggle = new Boolean(orderColumnSortOrderToggle);
        orderColumnID = (Integer) searchColumnsType
            .rawOfString(orderColumnIDString);
        ColumnInfo info = (ColumnInfo) searchColumnsType
            .cookedOfRaw(orderColumnID);
        String desc = Boolean.TRUE.equals(info.getSortdescending()) ? (Boolean.TRUE
            .equals(toggle) ? "" : " DESC")
            : (Boolean.TRUE.equals(toggle) ? " DESC" : "");
        orderings.addElement(database.quotedName(info.getName()) + desc);
        orderClause.addElement(name + "=" + orderColumnIDString);
      }
    }

    String orderBySQL = null;
    if (orderings.elements().hasMoreElements())
      orderBySQL = EnumUtils.concatenated(", ", orderings.elements());
    context.put("orderClause", EnumUtils.concatenated("&", orderClause
        .elements()));

    int start = 0;
    String startString = Form.getFieldNulled(context, "start");
    if (startString != null) {
      try {
        start = Math.max(0, Integer.parseInt(startString));
      } catch (NumberFormatException e) {
        throw new MelatiBugMelatiException("How did you get that in there?",
            new FormParameterException("start", "Param must be an Integer"));
      }
    }
    if (paged) { 
      final int resultsPerPage = 20;
      context.put("results", 
                  new CountedDumbPagedEnumeration<Persistent>(
                          table.selection(criteria, orderBySQL, false, false),
                          start, resultsPerPage,
                          table.cachedCount(criteria, false, false).count())
      );
    } else { 
      context.put("results", table.selection(criteria, orderBySQL, false, false));
    }
    return context;
  }

  /**
   * Implements the field search/selection request method.
   */
  protected static String popupSelectTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {
    popupSelect(context, melati);
    return adminTemplate("PopupSelect");
  }

  protected static ServletTemplateContext popupSelect(ServletTemplateContext context,
      Melati melati) throws PoemException {
    final Table table = melati.getTable();

    final Database database = table.getDatabase();

    // sort out search criteria

    final Persistent criteria = table.newPersistent();

    MappedEnumeration<Field<?>, Column<?>> criterias = new MappedEnumeration<Field<?>, Column<?>>(table
        .getSearchCriterionColumns()) {
      public Field<?> mapped(Column<?> c) {
        return c.asField(criteria).withNullable(true);
      }
    };

    context.put("criteria", EnumUtils.vectorOf(criterias));
    ReferencePoemType searchColumnsType = getSearchColumnsType(database, table);

    Vector<Field> orderings = new Vector<Field>();
    // NOTE Order by searchable columns, this could be summary columns
    Enumeration<Integer> searchColumns = searchColumnsType.possibleRaws();
    int o = 0;
    while (searchColumns.hasMoreElements()) {
      String name = "order-" + o++;
      orderings.addElement(new Field(searchColumns.nextElement(), 
          new BaseFieldAttributes(name, searchColumnsType)));
    }

    context.put("orderings", orderings);

    return context;
  }

  /**
   * @return a type whose whose possible members are the search columns of the table
   */
  private static ReferencePoemType getSearchColumnsType(final Database database, final Table table) {
    return new ReferencePoemType(database
        .getColumnInfoTable(), false) {
      protected Enumeration<Integer> _possibleRaws() {
        return new MappedEnumeration<Integer, Column<?>>(table.getSearchCriterionColumns()) {
          public Integer mapped(Column<?> column) {
            return column.getColumnInfo().getTroid();
          }
        };
      }
    };
  }

  /**
   * @return primary select template
   */
  protected static String selectionWindowPrimarySelectTemplate(
      ServletTemplateContext context, Melati melati) throws PoemException {
    context.put("inPopup", Boolean.TRUE);
    return primarySelectTemplate(context, melati);
  }

  /**
   * @return select template (a selection of records from a table)
   */
  protected static String selectionWindowSelectionTemplate(
      ServletTemplateContext context, Melati melati) {
    selection(context, melati, true);
    context.put("inPopup", Boolean.TRUE);
    return adminTemplate("Selection");
  }

  /**
   * Returns the Add template after placing the table and fields for the new row
   * in the context using any field values already in the context.
   * 
   * If the table is a table meta data table, or a column meta data table then
   * the appropriate extras are added to the co0ntext.
   * 
   * The Form does not normally contain values, but this could be used as a
   * mechanism for providing defaults.
   */
  protected static String addTemplate(final ServletTemplateContext context,
      Melati melati) throws PoemException {

    /*
     * Enumeration fields = new MappedEnumeration(melati.getTable().columns()) {
     * public Object mapped(Object column) { String stringValue =
     * context.getForm("field_" + ((Column)column).getName()); Object value =
     * null; if (stringValue != null) value =
     * ((Column)column).getType().rawOfString(stringValue); return new
     * Field(value, (Column)column); } }; context.put("fields", fields);
     */

    // getDetailDisplayColumns() == columns() but could exclude some in theory
    Enumeration<Column<?>> columns = melati.getTable().getDetailDisplayColumns();
    Vector<Field> fields = new Vector<Field>();
    while (columns.hasMoreElements()) {
      Column column = columns.nextElement();
      String stringValue = context.getFormField("field_" + column.getName());
      Object value = null;
      if (stringValue != null)
        value = column.getType().rawOfString(stringValue);
      else if (column.getType() instanceof ColumnTypePoemType)
        value = PoemTypeFactory.STRING.getCode();
      fields.add(new Field(value, column));
    }
    if (melati.getTable() instanceof TableInfoTable) {
      Database database = melati.getDatabase();

      // Compose field for naming the TROID column: the display name and
      // description are redundant, since they not used in the template

      final int troidHeight = 1;
      final int troidWidth = 20;
      Field troidNameField = new Field("id", new BaseFieldAttributes(
          "troidName", "Troid column", "Name of TROID column", database
              .getColumnInfoTable().getNameColumn().getType(), troidWidth,
          troidHeight, null, false, true, true));

      fields.add(troidNameField);
    }
    context.put("fields", fields.elements());
    return adminTemplate("Add");
  }

  /**
   * Returns the Updated template after creating a new row using field data in
   * the context.
   * <p>
   * If successful the template will say so while reloading according to the
   * returnTarget and returnURL values from the Form in context.
   */
  protected static String addUpdateTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {

    Persistent newPersistent = create(melati.getTable(), context);

    if (melati.getTable() instanceof TableInfoTable)
      melati.getDatabase().addTableAndCommit((TableInfo) newPersistent,
          context.getFormField("field_troidName"));
    if (melati.getTable() instanceof ColumnInfoTable)
      ((ColumnInfo) newPersistent).getTableinfo().actualTable()
          .addColumnAndCommit((ColumnInfo) newPersistent);
    melati.setPoemContext(new PoemContext(newPersistent));
    melati.loadTableAndObject();
    //context.put("object", newPersistent);
    melati.getResponse().setStatus(201);
    return adminTemplate("Updated");
  }

  /**
   * Returns the Updated template after modifying the current row according to
   * field values in the context.
   * <p>
   * If successful the template will say so while reloading according to the
   * returnTarget and returnURL values from the Form in context.
   */
  protected static String updateTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {
    Persistent object = melati.getObject();
    object.preEdit();
    Form.extractFields(context, object);
    object.postEdit(false);
    return adminTemplate("Updated");
  }

  protected static String deleteTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {
    try {
      if (melati.getTable().getName().equals("tableinfo")) {
        TableInfo tableInfo = (TableInfo) melati.getObject();
        melati.getDatabase().deleteTableAndCommit(tableInfo);
      } else if (melati.getTable().getName().equals("columninfo")) {
        ColumnInfo columnInfo = (ColumnInfo) melati.getObject();
        columnInfo.getTableinfo().actualTable().deleteColumnAndCommit(
            columnInfo);
      } else
        melati.getObject().delete();

      return adminTemplate("Updated");
    } catch (DeletionIntegrityPoemException e) {
      context.put("references", e.references);
      context.put("returnURL", melati.getSameURL() + "?action=Delete");
      return adminTemplate("DeleteFailure");
    }
  }

  protected static String duplicateTemplate(ServletTemplateContext context,
      Melati melati) throws PoemException {
    Persistent dup = melati.getObject().duplicated();
    Form.extractFields(context, dup);
    try {
      dup.getTable().create(dup);
    } catch (ExecutingSQLPoemException e) {
      throw new NonUniqueKeyValueAnticipatedException(e);
    }
    melati.setPoemContext(new PoemContext(dup));
    melati.loadTableAndObject();
    //context.put("object", dup);
    return adminTemplate("Updated");
  }

  /**
   * Implements request method "Update".
   * <p>
   * Calls another method depending on the requested action.
   * 
   * @see #updateTemplate(ServletTemplateContext, Melati)
   * @see #deleteTemplate(ServletTemplateContext, Melati)
   * @see #duplicateTemplate(ServletTemplateContext, Melati)
   */
  protected static String modifyTemplate(ServletTemplateContext context,
      Melati melati) throws FormParameterException {
    String action = melati.getRequest().getParameter("action");
    if ("Update".equals(action))
      return updateTemplate(context, melati);
    if ("Delete".equals(action))
      return deleteTemplate(context, melati);
    if ("Duplicate".equals(action))
      return duplicateTemplate(context, melati);
    else
      throw new MelatiBugMelatiException("How did you get that in there?",
          new FormParameterException(
            "action", "Bad action from Edit: " + action));
  }

  protected static String uploadTemplate(ServletTemplateContext context)
      throws PoemException {
    context.put("field", context.getFormField("field"));
    return adminTemplate("Upload");
  }

  /**
   * Finished uploading.
   * 
   * If you want the system to display the file you need to set your melati-wide
   * FormDataAdaptorFactory, in org.melati.MelatiConfig.properties, to something
   * that returns a valid URL, for instance, PoemFileDataAdaptorFactory;
   * (remember to set your UploadDir and UploadURL in the Setting table).
   * 
   * @param context
   *          the {@link ServletTemplateContext} in use
   * @return a template name
   */

  protected static String uploadDoneTemplate(ServletTemplateContext context)
      throws PoemException {
    String field = context.getFormField("field");
    context.put("field", field);
    String url = context.getMultipartFormField("file").getDataURL();
    if (url == null)
      throw new NullUrlDataAdaptorException(context.getMultipartFormField("file").getFormDataAdaptor());
    context.put("url", url);
    return adminTemplate("UploadDone");
  }

  static class NullUrlDataAdaptorException extends MelatiRuntimeException {
    private static final long serialVersionUID = 1L;
    private FormDataAdaptor fda;
    NullUrlDataAdaptorException(FormDataAdaptor fda) { 
      this.fda = fda;
    }

    /** @return the message */
    public String getMessage() {
      return "The configured FormDataAdaptor (" + fda.getClass().getName() + ") returns a null URL.";
    }
  }

  protected static String setupTemplate(ServletTemplateContext context,
      Melati melati) {
    screenStylesheetURL = melati.getDatabase().getSettingTable().ensure(
        Admin.class.getName() + ".ScreenStylesheetURL", "/blue.css",
        "ScreenStylesheetURL",
        "path to stylesheet, relative to melati-static, starting with a slash")
        .getValue();
    primaryDisplayTable = melati.getDatabase().getSettingTable().ensure(
        Admin.class.getName() + ".PrimaryDisplayTable", "setting",
        "PrimaryDisplayTable", "The default table to display").getValue();
    Setting homepageURLSetting = melati.getDatabase().getSettingTable().ensure(
        Admin.class.getName() + ".HomepageURL", "http://www.melati.org/",
        "HomepageURL", "The home page for this database");
    homepageURL = homepageURLSetting.getValue();
    // HACK Not very satisfactory, but only to enable testing
    //context.put("object", homepageURLSetting);
    // If we wanted to get RESTful at this point, but it is a bit nasty as a UI
    // melati.getResponse().setHeader("Location",melati.sameURLWith("action", ""));
    
    return adminTemplate("Updated");
  }

  protected String doTemplateRequest(Melati melati,
      ServletTemplateContext context) throws Exception {
    if (melati.getMethod().equals("Proxy"))
      return proxy(melati, context);
    melati.getSession().setAttribute("generatedByMelatiClass",this.getClass().getName());

    context.put("admin", new AdminUtils(melati));
    
    String table = Form.getFieldNulled(context, "table");
    if (table != null) {
      if (!table.equals(melati.getTable().getName())) {
        melati.getPoemContext().setTable(table);
        melati.getPoemContext().setTroid(null);
        melati.loadTableAndObject();
      }
    }
    if (Form.getFieldNulled(context, "goto") != null)
      melati.getResponse().sendRedirect(Form.getField(context, "goto", null));

    melati.setPassbackExceptionHandling();
    melati.setResponseContentType("text/html");

    Capability admin = PoemThread.database().getCanAdminister();
    AccessToken token = PoemThread.accessToken();
    if (!token.givesCapability(admin))
      throw new AccessPoemException(token, admin);


    if (melati.getMethod() == null)
      return adminTemplate("Main");
    if (melati.getMethod().equals("blank"))
      return adminTemplate("blank");
    if (melati.getMethod().equals("setup"))
      return setupTemplate(context, melati);
    if (melati.getMethod().equals("Main"))
      return adminTemplate("Main");
    if (melati.getMethod().equals("Top"))
      return adminTemplate("Top");
    if (melati.getMethod().equals("UploadDone"))
      return uploadDoneTemplate(context);
    if (melati.getMethod().equals("Record"))
      return adminTemplate("Record");
    if (melati.getMethod().equals("Selection"))
      return selectionTemplate(context, melati);

    if (melati.getObject() != null) {
      if (melati.getMethod().equals("Update"))
        return modifyTemplate(context, melati);
      if (melati.getObject() instanceof AdminSpecialised) {
        String templateName = ((AdminSpecialised) melati.getObject())
            .adminHandle(melati, melati.getMarkupLanguage());
        if (templateName != null)
          return templateName;
      }
    }

    if (melati.getTable() != null) {
      if (melati.getMethod().equals("Tree"))
        return adminTemplate("Tree");
      if (melati.getMethod().equals("Bottom"))
        return adminTemplate("Bottom");
      if (melati.getMethod().equals("Table"))
        return adminTemplate("Table");
      if (melati.getMethod().equals("PrimarySelect"))
        return primarySelectTemplate(context, melati);
      if (melati.getMethod().equals("EditHeader"))
        return adminTemplate("EditHeader");
      if (melati.getMethod().equals("Edit"))
        return adminTemplate("Edit");
      if (melati.getMethod().equals("Upload"))
        return uploadTemplate(context);

      if (melati.getMethod().equals("SelectionRight"))
        return selectionRightTemplate(context, melati);
      if (melati.getMethod().equals("Navigation"))
        return adminTemplate("Navigation");
      if (melati.getMethod().equals("PopUp"))
        return popupSelectTemplate(context, melati);
      if (melati.getMethod().equals("SelectionWindow"))
        return adminTemplate("SelectionWindow");
      if (melati.getMethod().equals("SelectionWindowPrimarySelect"))
        return selectionWindowPrimarySelectTemplate(context, melati);
      if (melati.getMethod().equals("SelectionWindowSelection"))
        return selectionWindowSelectionTemplate(context, melati);
      if (melati.getMethod().equals("Add"))
        return addTemplate(context, melati);
      if (melati.getMethod().equals("Created"))
        return addUpdateTemplate(context, melati);
    }
    if (melati.getMethod().equals("DSD"))
      return dsdTemplate(context);

    throw new InvalidUsageException(this, melati.getPoemContext());
  }

  private String proxy(Melati melati, ServletTemplateContext context) {
    if (melati.getSession().getAttribute("generatedByMelatiClass") == null)
      throw new AnticipatedException("Only available from within an Admin generated page");
    String method = melati.getRequest().getMethod();
    String url =  melati.getRequest().getQueryString();
    HttpServletResponse response = melati.getResponse();
    HttpMethod httpMethod = null; 
    try { 

      HttpClient client = new HttpClient();
      if (method.equals("GET"))
        httpMethod = new GetMethod(url);
      else if (method.equals("POST"))
        httpMethod = new PostMethod(url);
      else if (method.equals("PUT"))
        httpMethod = new PutMethod(url);
      else if (method.equals("HEAD"))
        httpMethod = new HeadMethod(url);
      else
        throw new RuntimeException("Unexpected method '" + method + "'");
      try {
        httpMethod.setFollowRedirects(true);
        client.executeMethod(httpMethod);
        for (Header h : httpMethod.getResponseHeaders()) { 
          response.setHeader(h.getName(), h.getValue());
        }
        response.setStatus(httpMethod.getStatusCode());
        response.setHeader("Cache-Control", "no-cache");
        byte[] outputBytes = httpMethod.getResponseBody();
        if (outputBytes != null) { 
          response.setBufferSize(outputBytes.length);
          response.getWriter().write(new String(outputBytes));
          response.getWriter().flush();
        }
      } catch (Exception e) {
        throw new MelatiIOException(e);
      }
    } finally {
      if (httpMethod != null)
        httpMethod.releaseConnection();
    }
    return null;
  }

  /**
   * @return the screenStylesheetURL
   */
  static String getScreenStylesheetURL() {
    return screenStylesheetURL;
  }

  /**
   * @param screenStylesheetURL the screenStylesheetURL to set
   */
  static void setScreenStylesheetURL(String screenStylesheetURL) {
    Admin.screenStylesheetURL = screenStylesheetURL;
  }

  /**
   * @return the primaryDisplayTable
   */
  static String getPrimaryDisplayTable() {
    return primaryDisplayTable;
  }

  /**
   * @param primaryDisplayTable the primaryDisplayTable to set
   */
  static void setPrimaryDisplayTable(String primaryDisplayTable) {
    Admin.primaryDisplayTable = primaryDisplayTable;
  }

  /**
   * @return the homepageURL
   */
  static String getHomepageURL() {
    return homepageURL;
  }

  /**
   * @param homepageURL the homepageURL to set
   */
  static void setHomepageURL(String homepageURL) {
    Admin.homepageURL = homepageURL;
  }
}
