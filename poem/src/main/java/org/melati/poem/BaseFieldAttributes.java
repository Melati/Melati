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
 * In the meantime, if you want to use Melati on non-GPL terms,
 * contact me!
 */

package org.melati.poem;

public class BaseFieldAttributes implements FieldAttributes {

  private String name;
  private String displayName;
  private String description;
  private PoemType type;
  private String renderInfo;

  public BaseFieldAttributes(String name, String displayName,
                             String description, PoemType type,
                             String renderInfo) {
    this.name = name;
    this.displayName = displayName;
    this.description = description;
    this.type = type;
    this.renderInfo = renderInfo;
  }

  public BaseFieldAttributes(FieldAttributes other, boolean nullable) {
    this(other.getName(), other.getDisplayName(), other.getDescription(),
	 other.getType().withNullable(nullable), other.getRenderInfo());
  }

  public BaseFieldAttributes(String name, PoemType type) {
    this(name, name, null, type, null);
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
    return false;
  }

  public boolean getUserEditable() {
    return true;
  }

  public boolean getUserCreateable() {
    return false;
  }

  public String getRenderInfo() {
    return renderInfo;
  }
}
