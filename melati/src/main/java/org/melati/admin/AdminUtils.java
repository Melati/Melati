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

import org.melati.poem.Table;
import org.melati.poem.Persistent;
import org.melati.poem.AccessPoemException;
import org.melati.poem.Field;
import org.melati.poem.ReferencePoemType;
import org.melati.Melati;
import org.melati.template.MarkupLanguage;

public class AdminUtils {

  private String adminURL;
  private String adminStaticURL;
  private String logicalDatabase;

  public AdminUtils(String adminURL, String adminStaticURL,
                    String logicalDatabase) {
    this.adminURL = adminURL;
    this.adminStaticURL = adminStaticURL;
    this.logicalDatabase = logicalDatabase;
  }

  public String TopURL() {
    return adminURL + "/" + logicalDatabase + "/Top";
  }

  public String BottomURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/Bottom";
  }

  public String LeftURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/Left";
  }

  public String PrimarySelectURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/PrimarySelect";
  }

  public String SelectionURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/Selection";
  }

  public String SelectionRightURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/SelectionRight";
  }

  public String NavigationURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() +
               "/Navigation";
  }

  public String RightURL(Persistent object) throws AccessPoemException {
    return
        adminURL + "/" + logicalDatabase + "/" +
        object.getTable().getName() + "/" + object.troid() + "/Right";
  }

  public String EditHeaderURL(Persistent object) throws AccessPoemException {
    return
        adminURL + "/" + logicalDatabase + "/" +
        object.getTable().getName() + "/" + object.troid() + "/EditHeader";
  }

  public String EditURL(Persistent object) throws AccessPoemException {
    return
        adminURL + "/" + logicalDatabase + "/" +
        object.getTable().getName() + "/" + object.troid() + "/Edit";
  }

  public String AddURL(Table table) throws AccessPoemException {
    return
        adminURL + "/" + logicalDatabase + "/" +
            (table instanceof org.melati.poem.ColumnInfoTable ?
               Admin.METHOD_CREATE_COLUMN :
             table instanceof org.melati.poem.TableInfoTable ?
               Admin.METHOD_CREATE_TABLE :
               table.getName() + "/" + Admin.METHOD_ADD_RECORD);
  }

  public String PopupURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() + "/PopUp";
  }

  public String SelectionWindowURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + 
               table.getName() + "/SelectionWindow";
  }

  public String SelectionWindowPrimarySelectURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() + 
               "/SelectionWindowPrimarySelect";
  }

  public String SelectionWindowSelectionURL(Table table) {
    return adminURL + "/" + logicalDatabase + "/" + table.getName() + 
               "/SelectionWindowSelection";
  }

  /*
   * in an insert situation, we will not have a Troid, so cannot pass it through
   * if your upload handler depends on haviung a persistent, then you should 
   * override your upload template so that it prevents uploading in an insert
   * situation
   */
  public String UploadURL(Table table, Persistent object, Field field) {
    return upload(table,object) + "/Upload?field=" + field.getName();
  }

  public String UploadHandlerURL(Table table, Persistent object, String field) {
    return upload(table,object) + "/UploadDone?field=" + field;
  }
  
  private String upload(Table table,Persistent object) {
    String url = adminURL + "/" + logicalDatabase + "/" + table.getName();
    if (object != null) url += "/" + object.troid();
    return url;
  }

  // establish if this is a reference poem type field

  public boolean isReferencePoemType(Field field) {
    return field.getType() instanceof ReferencePoemType;
  }

  public String getStaticURL() {
    return adminStaticURL;
  }

  public String specialFacilities(Melati melati, MarkupLanguage ml,
                                  Persistent object)
      throws Exception {
    if (object instanceof AdminSpecialised)
      melati.getTemplateEngine().expandTemplate(
               melati.getWriter(),
               ((AdminSpecialised)object).adminSpecialFacilities(melati, ml),
               melati.getTemplateContext());
    return "";
  }
}
