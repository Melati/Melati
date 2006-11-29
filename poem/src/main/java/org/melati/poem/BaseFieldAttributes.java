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

package org.melati.poem;

import org.melati.util.StringUtils;

/**
 * Base class of all {@link Field}s.
 *
 * @author WilliamC At paneris.org
 *
 */
public class BaseFieldAttributes implements FieldAttributes {

  private String name;
  private String displayName;
  private String description;
  private PoemType type;
  private String renderInfo;
  private int width, height;
  private boolean indexed;
  private boolean userEditable;
  private boolean userCreateable;

  /**
   * Full Constructor.
   * 
   * @param name simple name
   * @param displayName human readable name
   * @param description a short description 
   * @param type the PoemType of the new Field
   * @param width how wide input widgets should be 
   * @param height how high input widgets should be 
   * @param renderInfo the name of a special templet to render this field
   * @param indexed whether the Field is indexed.
   * @param userEditable whether users should be able to modify the value
   * @param userCreateable whether users should be allowed to create new instances
   */
  public BaseFieldAttributes(
      String name, String displayName, String description, PoemType type,
      int width, int height, String renderInfo, boolean indexed,
      boolean userEditable, boolean userCreateable) {
    this.name = name;
    this.displayName = displayName;
    this.description = description;
    this.type = type;
    this.width = width;
    this.height = height;
    this.renderInfo = renderInfo;
    this.indexed = indexed;
    this.userEditable = userEditable;
    this.userCreateable = userCreateable;
  }

  /*
  public BaseFieldAttributes(String name, String displayName,
                             String description, PoemType type,
                             int width, int height, String renderInfo) {
    this(name, displayName, description, type, width, height, renderInfo,
         false, true, false);
  }
  */

  public BaseFieldAttributes(String name, PoemType type) {
    this(name, StringUtils.capitalised(name), null, type, 0, 0, null, 
         false, true, true);
  }

  public BaseFieldAttributes(FieldAttributes other, PoemType type) {
    this(other.getName(), other.getDisplayName(), other.getDescription(),
         type, other.getWidth(), other.getHeight(), other.getRenderInfo(),
         other.getIndexed(), other.getUserEditable(),
         other.getUserCreateable());
  }

  public BaseFieldAttributes(FieldAttributes other, String name) {
    this(name, other.getDisplayName(), other.getDescription(),
         other.getType(), other.getWidth(), other.getHeight(), 
         other.getRenderInfo(),other.getIndexed(), other.getUserEditable(),
         other.getUserCreateable());
  }

  /** 
   * Allow the description to vary as well
   */
  public BaseFieldAttributes(FieldAttributes other, String name, 
                             String description) {
    this(name, other.getDisplayName(), description,
         other.getType(), other.getWidth(), other.getHeight(), 
         other.getRenderInfo(),
         other.getIndexed(), other.getUserEditable(),
         other.getUserCreateable());
  }

  public BaseFieldAttributes(FieldAttributes other, boolean nullable) {
    this(other, other.getType().withNullable(nullable));
  }

  public String getName() {
    return name;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getDescription() {
    return description;
  }

  public PoemType getType() {
    return type;
  }

  public boolean getIndexed() {
    return indexed;
  }

  public boolean getUserEditable() {
    return userEditable;
  }

  public boolean getUserCreateable() {
    return userCreateable;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public String getRenderInfo() {
    return renderInfo;
  }
}
