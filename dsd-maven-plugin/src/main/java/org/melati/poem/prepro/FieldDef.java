/*
 * $Source$
 * $Revision$
 *
 * Part of Melati (http://melati.org), a framework for the rapid
 * development of clean, maintainable web applications.
 *
 * -------------------------------------
 *  Copyright (C) 2000 William Chesters
 * -------------------------------------
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 * A copy of the GPL should be in the file org/melati/COPYING in this tree.
 * Or see http://melati.org/License.html.
 *
 * Contact details for copyright holder:
 *
 *     William Chesters <williamc@paneris.org>
 *     http://paneris.org/~williamc
 *     Obrechtstraat 114, 2517VX Den Haag, The Netherlands
 *
 *
 * ------
 *  Note
 * ------
 *
 * I will assign copyright to PanEris (http://paneris.org) as soon as
 * we have sorted out what sort of legal existence we need to have for
 * that to make sense.  When WebMacro's "Simple Public License" is
 * finalised, we'll offer it as an alternative license for Melati.
 * In the meantime, if you want to use WebMacro on non-GPL terms,
 * contact me!
 */

package org.melati.poem.prepro;

import java.util.*;
import java.io.*;
import org.melati.util.*;

public abstract class FieldDef {
  protected final TableDef table;
  protected final String name;
  protected final String suffix;
  protected final int displayOrder;
  String displayName;
  String description;
  protected final String type;
  protected final String rawType;
  protected final Vector qualifiers;

  final String baseClass;
  final String mainClass;
  final String tableMainClass;
  final String tableAccessorMethod;
  boolean isNullable;
  boolean isTroidColumn;
  boolean isDeletedColumn;
  boolean isPrimaryDisplayColumn;
  int displayOrderPriority = -1;
  boolean isEditable = true;
  boolean recorddisplay = true;
  boolean summarydisplay = true;
  boolean searchcriterion = true;
  boolean isIndexed = false;
  boolean isUnique = false;
  boolean isCompareOnly = false;
  int width = -1, height = -1;

  public FieldDef(TableDef table, String name,
                  String type, String rawType,
                  int displayOrder, Vector qualifiers)
      throws IllegalityException {
    this.table = table;
    this.name = name;
    this.displayOrder = displayOrder;
    this.suffix = StringUtils.capitalised(name);
    this.type = type;
    this.rawType = rawType;
    this.qualifiers = qualifiers;

    this.baseClass = table.baseClass;
    this.mainClass = table.mainClass;
    this.tableMainClass = table.tableMainClass;
    this.tableAccessorMethod = table.tableAccessorMethod;

    for (int q = 0; q < qualifiers.size(); ++q)
      ((FieldQualifier)qualifiers.elementAt(q)).apply(this);
  }

  public String toString() {
    return
        table.name + "." + name +
        " (" + (isNullable ? "nullable " : "") + type + ")";
  }

  private static void fieldQualifiers(Vector qualifiers, StreamTokenizer tokens)
      throws ParsingDSDException, IOException {
    while (tokens.ttype == '(') {
      tokens.nextToken();
      qualifiers.addElement(FieldQualifier.from(tokens));
      DSD.expect(tokens, ')');
      tokens.nextToken();
    }
  }

  public static FieldDef from(TableDef table, StreamTokenizer tokens,
                              int displayOrder)
      throws ParsingDSDException, IOException, IllegalityException {
    Vector qualifiers = new Vector();
    fieldQualifiers(qualifiers, tokens);
    if (tokens.ttype != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field type>", tokens);
    String type = tokens.sval;
    if (tokens.nextToken() != StreamTokenizer.TT_WORD)
      throw new ParsingDSDException("<field name>", tokens);
    String name = tokens.sval;
    tokens.nextToken();
    fieldQualifiers(qualifiers, tokens);
    DSD.expect(tokens, ';');

    if (type.equals("Integer"))
      return new IntegerFieldDef(table, name, displayOrder, qualifiers);
    else if (type.equals("Double"))
      return new DoubleFieldDef(table, name, displayOrder, qualifiers);
    else if (type.equals("Boolean"))
      return new BooleanFieldDef(table, name, displayOrder, qualifiers);
    else if (type.equals("String"))
      return new StringFieldDef(table, name, displayOrder, qualifiers);
    else if (type.equals("ColumnType"))
      return new ColumnTypeFieldDef(table, name, displayOrder, qualifiers);
    else
      return new ReferenceFieldDef(table, name, displayOrder, type,
                                   qualifiers);
  }

  public void generateBaseMethods(Writer w) throws IOException {
    w.write("  public " + rawType + " get" + suffix + "_unsafe() {\n" +
            "    return " + name + ";\n" +
            "  }\n" +
            "\n" +
            "  public void set" + suffix + "_unsafe(" + rawType + " cooked) {\n" +
            "    " + name + " = cooked;\n" +
            "  }\n");
  }

  public void generateFieldCreator(Writer w) throws IOException {
    w.write("  public final Field get" + suffix + "Field() " +
                  "throws AccessPoemException {\n" +
            "    return " + tableAccessorMethod + "()." +
                    "get" + suffix + "Column().asField(this);\n" +
            "  }\n");
  }

  public abstract void generateJavaDeclaration(Writer w) throws IOException;

  public void generateColDecl(Writer w) throws IOException {
    w.write("Column col_" + name);
  }

  public void generateColAccessor(Writer w) throws IOException {
    w.write("  public final Column get" + suffix + "Column() {\n" +
            "    return col_" + name + ";\n" +
            "  }\n");
  }

  protected void generateColRawAccessors(Writer w)
      throws IOException {
    w.write(
      "          public Object getRaw_unsafe(Persistent g)\n" +
      "              throws AccessPoemException {\n" +
      "            return ((" + mainClass + ")g)." +
                      "get" + suffix + "_unsafe();\n" +
      "          }\n" +
      "\n" +
      "          public void setRaw_unsafe(Persistent g, Object raw)\n" +
      "              throws AccessPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "_unsafe((" +
                       rawType + ")raw);\n" +
      "          }\n");
  }

  public void generateColDefinition(Writer w) throws IOException {
    w.write(
      "    defineColumn(col_" + name + " =\n" +
      "        new Column(this, \"" + name + "\", " + poemTypeJava() + ", " +
                    "DefinitionSource.dsd) { \n" +
      "          public Object getCooked(Persistent g)\n" +
      "              throws AccessPoemException, PoemException {\n" +
      "            return ((" + mainClass + ")g).get" + suffix + "();\n" +
      "          }\n" +
      "\n" +
      "          public void setCooked(Persistent g, Object cooked)\n" +
      "              throws AccessPoemException, ValidationPoemException {\n" +
      "            ((" + mainClass + ")g).set" + suffix + "((" +
                       type + ")cooked);\n" +
      "          }\n" +
      "\n");

    if (isTroidColumn || !isEditable)
      w.write("          protected boolean defaultUserEditable() {\n" +
              "            return false;\n" +
              "          }\n" +
              "\n");

    if (!recorddisplay)
      w.write("          protected boolean defaultRecordDisplay() {\n" +
              "            return false;\n" +
              "          }\n" +
              "\n");

    if (!summarydisplay)
      w.write("          protected boolean defaultSummaryDisplay() {\n" +
              "            return false;\n" +
              "          }\n" +
              "\n");

    if (!searchcriterion)
      w.write("          protected boolean defaultSearchCriterion() {\n" +
              "            return false;\n" +
              "          }\n" +
              "\n");

    if (isPrimaryDisplayColumn)
      w.write("          protected boolean defaultPrimaryDisplay() {\n" +
              "            return true;\n" +
              "          }\n" +
              "\n");

    if (displayOrderPriority != -1)
      w.write("          protected Integer defaultDisplayOrderPriority() {\n" +
              "            return new Integer(" + displayOrderPriority + ");\n" +
              "          }\n" +
              "\n");

    if (displayName != null)
      w.write("          protected String defaultDisplayName() {\n" +
              "            return " +
                               StringUtils.quoted(displayName, '"') + ";\n" +
              "          }\n" +
              "\n");

    w.write("          protected int defaultDisplayOrder() {\n" +
            "            return " + displayOrder + ";\n" +
            "          }\n" +
            "\n");

    if (description != null)
      w.write("          protected String defaultDescription() {\n" +
              "            return " +
                               StringUtils.quoted(description, '"') + ";\n" +
              "          }\n" +
              "\n");

    if (isIndexed)
      w.write("          protected boolean defaultIndexed() {\n" +
              "            return true;\n" +
              "          }\n" +
              "\n");

    if (isUnique)
      w.write("          protected boolean defaultUnique() {\n" +
              "            return true;\n" +
              "          }\n" +
              "\n");

    if (width != -1)
      w.write("          protected int defaultWidth() {\n" +
              "            return " + width + ";\n" +
              "          }\n" +
              "\n");

    if (height != -1)
      w.write("          protected int defaultHeight() {\n" +
              "            return " + height + ";\n" +
              "          }\n" +
              "\n");

    generateColRawAccessors(w);

    w.write(
      "        });\n");
  }

  public abstract String poemTypeJava();
}
