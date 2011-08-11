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

package org.melati.poem.prepro;

import java.util.Vector;
import java.io.StreamTokenizer;
import java.io.Writer;
import java.io.IOException;

/**
 * An abstract definition of a <tt>Field</tt> from which all other
 * <tt>FieldDef</tt>s are derived.
 *
 */
public abstract class FieldDef {

  protected final TableDef table;

  protected final String name;

  protected final String capitalisedName;

  protected int displayOrder;

  String displayName;

  String description;

  /** short name eg String, User */
  protected final String typeShortName;

  protected final String rawType;

  protected final Vector<FieldQualifier> fieldQualifiers;

  final String shortestUnambiguousClassname;

  final String tableAccessorMethod;

  protected String displayLevel = null;

  protected String searchability = null;

  private boolean sortDescending = false;

  int displayOrderPriority = -1;

  private boolean isNullable = false;

  private boolean isTroidColumn = false;

  private boolean isDeletedColumn = false;

  private boolean isEditable = true;

  private boolean isCreateable = true;

  private boolean isIndexed = false;

  private boolean isUnique = false;

  boolean isCompareOnly = false;

  private int width = -1, height = -1;

  String renderinfo = null;

  protected int lineNumber;

  /**
   * Constructor.
   *
   * @param table
   *          the {@link TableDef} that this <code>Field</code> is part of
   * @param name
   *          the name of this field
   * @param type
   *          the POEM type of this field
   * @param rawType
   *          the underlying java type of this field
   * @param displayOrder
   *          where to place this field in a list
   * @param qualifiers
   *          all the qualifiers to be applied to this field
   *
   * @throws IllegalityException
   *           if a semantic inconsistency is detected
   */
  public FieldDef(int lineNo, TableDef table, String name, String type,
      String rawType, int displayOrder, Vector<FieldQualifier> qualifiers)
      throws IllegalityException {
    this.lineNumber = lineNo;
    this.table = table;
    this.name = name;
    this.displayOrder = displayOrder;
    this.capitalisedName = StringUtils.capitalised(name);
    this.typeShortName = type;
    this.rawType = rawType;
    this.fieldQualifiers = qualifiers;

    this.shortestUnambiguousClassname = table.tableNamingInfo.mainClassUnambiguous();
    this.tableAccessorMethod = table.tableNamingInfo.tableAccessorMethod();

    for (int q = 0; q < qualifiers.size(); ++q) {
      ((FieldQualifier)qualifiers.elementAt(q)).apply(this);
    }

  }

  /** @return a name for this class */
  public String toString() {
    return table.name + "." + name + " (" + (isNullable ? "nullable " : "")
        + typeShortName + ")";
  }

  private static void readFieldQualifiers(Vector<FieldQualifier> qualifiers, StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    while (tokens.ttype == '(') {
      tokens.nextToken();
      qualifiers.addElement(FieldQualifier.from(tokens));
      DSD.expect(tokens, ')');
      tokens.nextToken();
    }
  }

  /**
   * Creates the appropriate type of <code>FieldDef</code> from the input
   * stream.
   *
   * @param table
   *          the {@link TableDef} we are dealing with
   * @param tokens
   *          the <code>StreamTokenizer</code> to get tokens from
   * @param displayOrder
   *          the ranking of this <code>Field</code>
   *
   * @throws ParsingDSDException
   *           if an unexpected token is encountered
   * @throws IOException
   *           if something goes wrong with the file system
   * @throws IllegalityException
   *           if a semantic incoherence is detected
   * @return a new <code>FieldDef</code> of the appropriate type
   */
  public static FieldDef from(TableDef table, StreamTokenizer tokens,
      int displayOrder) throws ParsingDSDException, IOException,
      IllegalityException {
    table.addImport("org.melati.poem.AccessPoemException", "both");
    table.addImport("org.melati.poem.ValidationPoemException", "table");
    table.addImport("org.melati.poem.Persistent", "table");

    table.definesColumns = true;
    Vector<FieldQualifier> qualifiers = new Vector<FieldQualifier>();
    readFieldQualifiers(qualifiers, tokens);
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field type>", tokens);
    String type = tokens.sval;
    // HACK we allow "byte[]" for binary data
    if (type.equals("byte")) {
      if (tokens.nextToken() != '[' || tokens.nextToken() != ']')
        throw new ParsingDSDException("[", tokens);
      type = "byte[]";
    }

    if (tokens.nextToken() != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field name>", tokens);
    String name = tokens.sval;
    tokens.nextToken();
    readFieldQualifiers(qualifiers, tokens);
    DSD.expect(tokens, ';');
    int lineNo = tokens.lineno();
    if (type.equals("Integer"))
      return new IntegerFieldDef(lineNo, table, name, displayOrder, qualifiers);
    if (type.equals("Long"))
      return new LongFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("Double"))
      return new DoubleFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("Boolean"))
      return new BooleanFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("String"))
      return new StringFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("Password"))
      return new PasswordFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("Date"))
      return new DateFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else if (type.equals("Timestamp"))
      return new TimestampFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("ColumnType"))
      return new ColumnTypeFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("DisplayLevel"))
      return new DisplayLevelFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("Searchability"))
      return new SearchabilityFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("IntegrityFix"))
      return new IntegrityFixFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("BigDecimal"))
      return new BigDecimalFieldDef(lineNo, table, name, displayOrder,
          qualifiers);
    else if (type.equals("byte[]"))
      return new BinaryFieldDef(lineNo, table, name, displayOrder, qualifiers);
    else
      return new ReferenceFieldDef(lineNo, table, name, displayOrder, type,
          qualifiers);
  }

  /**
   * Write out this <code>Column</code>'s base methods.
   *
   * @param w
   *          Persistent Base
   *
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public void generateBaseMethods(Writer w) throws IOException {
    w.write("\n /**\n" + "  * Retrieves the <code>" + capitalisedName
        + "</code> value, without locking, \n" + "  * for this <code>"
        + table.mixedCaseName + "</code> <code>Persistent</code>.\n" + "  *\n"
        + "  * see org.melati.poem.prepro.FieldDef"
        + "#generateBaseMethods \n" + "  * @return the " + rawType + " " + name
        + "\n" + "  */\n");
    w.write("  public " + rawType + " get" + capitalisedName + "_unsafe() {\n"
        + "    return " + name + ";\n" + "  }\n" + "\n");
    w.write("\n /**\n" + "  * Sets the <code>" + capitalisedName
        + "</code> value directly, without checking, \n" + "  * for this "
        + table.mixedCaseName + " <code>Persistent</code>.\n" + "  * \n"
        + "  * see org.melati.poem.prepro.FieldDef"
        + "#generateBaseMethods \n"
        + "  * @param cooked  the pre-validated value to set\n" + "  */\n");
    w.write("  public void set" + capitalisedName + "_unsafe(" + rawType
        + " cooked) {\n" + "    " + name + " = cooked;\n" + "  }\n");
  }

  /**
   * Write out this <code>Column</code>'s field creators.
   *
   * @param w
   *          Persistent Base
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public void generateFieldCreator(Writer w) throws IOException {
    w.write("\n /**\n" 
        + "  * Retrieves the <code>" + capitalisedName + "</code> value as a <code>Field</code>\n" + "  * from this <code>"
        + table.mixedCaseName + "</code> <code>Persistent</code>.\n" 
        + "  * \n"
        + "  * see org.melati.poem.prepro.FieldDef#generateFieldCreator \n" 
        + "  * @throws AccessPoemException \n"
        + "  *         if the current <code>AccessToken</code> \n"
        + "  *         does not confer write access rights\n"
        + "  * @return the " + rawType + " " + name + "\n" 
        + "  */\n");
    w.write("  public Field<" + rawType + "> get" + capitalisedName + "Field() throws AccessPoemException {\n" 
        + "    Column<"+rawType+"> c = _"+ tableAccessorMethod + "()." + "get" + capitalisedName + "Column();\n"
        + "    return new Field<"+rawType+">(("+rawType+")c.getRaw(this), c);\n" 
        + "  }\n");
  }

  /**
   * Write out this <code>Field</code>'s java declaration string.
   *
   * @param w
   *          PersistentBase
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public abstract void generateJavaDeclaration(Writer w) throws IOException;

  /**
   * Write out this <code>Column</code>'s java declaration string.
   *
   * @param w
   *          TableBase
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public void generateColDecl(Writer w) throws IOException {
    // FIXME This should be different for ref types
    w.write("Column<"+rawType+"> col_" + name);
  }

  /**
   * Write out this <code>Column</code>'s accessors.
   *
   * @param w
   *          TableBase
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public void generateColAccessor(Writer w) throws IOException {
    w.write("\n /**\n" 
        + "  * Retrieves the <code>" + capitalisedName + "</code> <code>Column</code> for this \n" 
        + "  * <code>"+ table.mixedCaseName + "</code> <code>Table</code>.\n" + "  * \n"
        + "  * see org.melati.poem.prepro.FieldDef#generateColAccessor \n" 
        + "  * @return the " + name + " <code>Column</code>\n" 
        + "  */\n");
    w.write("  public final Column<"+rawType+"> get" + capitalisedName + "Column() {\n"
        + "    return col_" + name + ";\n" + "  }\n");
  }

  /**
   * Write out this <code>Column</code>'s field accessors as part of the
   * anonymous definition of the <code>Column</code>.
   *
   * @param w
   *          TableBase
   * @throws IOException
   *           if something goes wrong with the file system
   */
  protected void generateColRawAccessors(Writer w) throws IOException {
    w.write("          public Object getRaw_unsafe(Persistent g)\n"
        + "              throws AccessPoemException {\n"
        + "            return ((" + shortestUnambiguousClassname + ")g)." + "get" + capitalisedName
        + "_unsafe();\n" + "          }\n" + "\n");

    w.write("          public void setRaw_unsafe(Persistent g, Object raw)\n"
        + "              throws AccessPoemException {\n" + "            (("
        + shortestUnambiguousClassname + ")g).set" + capitalisedName + "_unsafe((" + rawType + ")raw);\n"
        + "          }\n");
  }

  /**
   * Write out this <code>Column</code>'s definition using an anonymous
   * class.
   *
   * @param w
   *          TableBase
   * @throws IOException
   *           if something goes wrong with the file system
   */
  public void generateColDefinition(Writer w) throws IOException {
    w
        .write("    defineColumn(col_"
            + name
            + " =\n"
            + "        new Column<"+rawType +">(this, \""
            + name
            + "\",\n"
            + "                   "
            + poemTypeJava()
            + ",\n"
            + "                   DefinitionSource.dsd) { \n"
            + "          public Object getCooked(Persistent g)\n"
            + "              throws AccessPoemException, PoemException {\n"
            + "            return (("
            + shortestUnambiguousClassname
            + ")g).get"
            + capitalisedName
            + "();\n"
            + "          }\n"
            + "\n"
            + "          public void setCooked(Persistent g, Object cooked)\n"
            + "              throws AccessPoemException, ValidationPoemException {\n"
            + "            ((" + shortestUnambiguousClassname + ")g).set" + capitalisedName + "((" + typeShortName
            + ")cooked);\n" + "          }\n" + "\n"
            + "          public Field<"+rawType+"> asField(Persistent g) {\n"
            + "            return ((" + shortestUnambiguousClassname + ")g).get" + capitalisedName
            + "Field();\n" + "          }\n" + "\n");

    if (isTroidColumn || !isEditable)
      w.write("          protected boolean defaultUserEditable() {\n"
          + "            return false;\n" + "          }\n" + "\n");

    if (isTroidColumn || !isCreateable)
      w.write("          protected boolean defaultUserCreateable() {\n"
          + "            return false;\n" + "          }\n" + "\n");

    if (displayLevel != null)
      w.write("          protected DisplayLevel defaultDisplayLevel() {\n"
          + "            return DisplayLevel." + displayLevel + ";\n"
          + "          }\n" + "\n");

    if (searchability != null)
      w.write("          protected Searchability defaultSearchability() {\n"
          + "            return Searchability." + searchability + ";\n"
          + "          }\n" + "\n");

    if (displayOrderPriority != -1)
      w.write("          protected Integer defaultDisplayOrderPriority() {\n"
          + "            return new Integer(" + displayOrderPriority + ");\n"
          + "          }\n" + "\n");

    if (sortDescending)
      w.write("          protected boolean defaultSortDescending() {\n"
          + "            return true;\n" + "          }\n" + "\n");

    if (displayName != null)
      w.write("          protected String defaultDisplayName() {\n"
          + "            return " + StringUtils.quoted(displayName, '"')
          + ";\n" + "          }\n" + "\n");

    w
        .write("          protected int defaultDisplayOrder() {\n"
            + "            return " + displayOrder + ";\n" + "          }\n"
            + "\n");

    if (description != null)
      w.write("          protected String defaultDescription() {\n"
          + "            return " + StringUtils.quoted(description, '"')
          + ";\n" + "          }\n" + "\n");

    if (isIndexed)
      w.write("          protected boolean defaultIndexed() {\n"
          + "            return true;\n" + "          }\n" + "\n");

    if (isUnique)
      w.write("          protected boolean defaultUnique() {\n"
          + "            return true;\n" + "          }\n" + "\n");

    if (width != -1)
      w.write("          protected int defaultWidth() {\n"
          + "            return " + width + ";\n" + "          }\n" + "\n");

    if (height != -1)
      w.write("          protected int defaultHeight() {\n"
          + "            return " + height + ";\n" + "          }\n" + "\n");

    if (renderinfo != null)
      w.write("          protected String defaultRenderinfo() {\n"
          + "            return " + StringUtils.quoted(renderinfo, '"') + ";\n"
          + "          }\n" + "\n");

    generateColRawAccessors(w);

    w.write("        });\n");
  }

  /** @return the Java string for this <code>PoemType</code>. */
  public abstract String poemTypeJava();

  /**
   * @return whether this column is a deleted marker
   */
  public boolean isDeletedColumn() {
    return isDeletedColumn;
  }

  /**
   * Set whether this field represents a deleted marker.
   *
   * @param isDeletedColumn boolean
   */
  public void setDeletedColumn(boolean isDeletedColumn) {
    if (this.isDeletedColumn)
      throw new IllegalityException(lineNumber,
          "Deleted qualifier already set true.");
    this.isDeletedColumn = isDeletedColumn;
  }

  /**
   * @return whether this field represents a troid column.
   */
  public boolean isTroidColumn() {
    return isTroidColumn;
  }

  /**
   * Set the isTroidColumn property.
   *
   * @param isTroidColumn boolean
   */
  public void setTroidColumn(boolean isTroidColumn) {
    if (this.isTroidColumn)
      throw new IllegalityException(lineNumber,
          "Troid qualifier  already set true.");
    this.isTroidColumn = isTroidColumn;
  }

  /**
   * @return whether this column is nullable.
   */
  public boolean isNullable() {
    return isNullable;
  }

  /**
   * Set the nullable property.
   *
   * @param isNullable boolean
   */
  public void setNullable(boolean isNullable) {
    if (this.isNullable)
      throw new IllegalityException(lineNumber,
          "Nullable qualifier  already set true.");
    this.isNullable = isNullable;
  }

  /**
   * @return whether this field is editable
   */
  public boolean isEditable() {
    return isEditable;
  }

  /**
   * Set the isEditable property.
   *
   * @param isEditable boolean
   */
  public void setEditable(boolean isEditable) {
    if (!this.isEditable)
      throw new IllegalityException(lineNumber,
          "Editable qualifier  already set true.");
    this.isEditable = isEditable;
  }

  /**
   * @return whether this column shoudl be sorted in descending order
   */
  public boolean isSortDescending() {
    return sortDescending;
  }

  /**
   * Set the sortDescending property.
   *
   * @param sortDescending
   */
  public void setSortDescending(boolean sortDescending) {
    if (this.sortDescending)
      throw new IllegalityException(lineNumber,
          "Sort descending qualifier  already set true.");
    this.sortDescending = sortDescending;
  }

  /**
   * @return whether this column is user createable
   */
  public boolean isCreateable() {
    return isCreateable;
  }

  /**
   * Set the isCreatable property.
   *
   * @param isCreateable boolean
   */
  public void setCreateable(boolean isCreateable) {
    if (!this.isCreateable)
      throw new IllegalityException(lineNumber,
          "Creatable qualifier  already set true.");
    this.isCreateable = isCreateable;
  }

  /**
   * @return whether this column is indexed.
   */
  public boolean isIndexed() {
    return isIndexed;
  }

  /**
   * Set the isIndexed property.
   * @param isIndexed boolean
   */
  public void setIndexed(boolean isIndexed) {
    if (this.isIndexed)
      throw new IllegalityException(lineNumber,
          "Indexed qualifier  already set true.");
    this.isIndexed = isIndexed;
  }

  /**
   * @return whether this column is unique
   */
  public boolean isUnique() {
    return isUnique;
  }

  /**
   * Set the isUnique property.
   *
   * @param isUnique boolean
   */
  public void setUnique(boolean isUnique) {
    if (this.isUnique)
      throw new IllegalityException(lineNumber,
          "Unique qualifier  already set true.");
    this.isUnique = isUnique;
  }

  /**
   * @return the width
   */
  public int getWidth() {
    return width;
  }

  /**
   * Set the width property.
   *
   * @param width the width to set
   */
  public void setWidth(int width) {
    if (this.width != -1)
      throw new IllegalityException(lineNumber, "Size already set to "
          + this.width + " cannot overwrite with " + width);
    this.width = width;
  }

  /**
   * @return the height
   */
  public int getHeight() {
    return height;
  }

  /**
   * Set the height property.
   *
   * @param height the height to set
   */
  public void setHeight(int height) {
    if (this.height != -1)
      throw new IllegalityException(lineNumber, "Height already set to "
          + this.width + " cannot overwrite with " + width);
    this.height = height;
  }
}
