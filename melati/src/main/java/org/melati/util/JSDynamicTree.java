/*
 * $Source$
 * $Revision$
 *
 * Copyright (C) 2001 Myles Chippendale
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
 *     Myles Chippendale <mylesc At paneris.org>
 */
package org.melati.util;

/**
 * A JavaScript tree which can be rendered as Dynamic HTML.
 * 
 * @todo Rename to JsDhtmlTree
 */
public class JSDynamicTree extends Tree {

  private Integer x = new Integer(10);
  private Integer y = new Integer(10);
  private Integer nodeHeight = new Integer(19);
  private Integer nodeWidth = new Integer(400);
  private String nodeColour = "#ffffff";
  private Integer indent = new Integer(20);
  private String nodeLabelTemplet = "";
  private String openedImage = "";
  private String closedImage = "";
  private String leafImage = "";
  private Integer depthPerDownload = new Integer(-1);

  /**
   * Constructor.
   * 
   * @param tree
   *        the Tree to render
   */
  public JSDynamicTree(Tree tree) {
    super(tree.getTreeableRoots(), tree.getDepth());
  }

  /**
   * @return the starting X co-ordinate
   */
  public Integer getX() {
    return x;
  }

  /**
   * Set the starting X co-ordinate.
   * @param x the starting X co-ordinate to set
   */
  public void setX(Integer x) {
    this.x = x;
  }

  /**
   * @return the starting Y co-ordinate
   */
  public Integer getY() {
    return y;
  }

  /**
   * Set the starting Y co-ordinate.
   * @param y the starting Y co-ordinate to set
   */
  public void setY(Integer y) {
    this.y = y;
  }

  /**
   * @return the node height
   */
  public Integer getNodeHeight() {
    return nodeHeight;
  }

  /**
   * Set the node height.
   * @param nh the node height to set
   */
  public void setNodeHeight(Integer nh) {
    nodeHeight = nh;
  }

  /**
   * @return the node width
   */
  public Integer getNodeWidth() {
    return nodeWidth;
  }

  /**
   * Set the node width.
   * @param nw the node width to set
   */
  public void setNodeWidth(Integer nw) {
    nodeWidth = nw;
  }

  /**
   * @return the node colour
   */
  public String getNodeColour() {
    return nodeColour;
  }

  /**
   * Set the node colour.
   * @param nc the node colour to set
   */
  public void setNodeColour(String nc) {
    nodeColour = nc;
  }

  /**
   * @return the indent 
   */
  public Integer getIndent() {
    return indent;
  }

  /**
   * Set the indent.
   * @param i the indent to set
   */
  public void setIndent(Integer i) {
    indent = i;
  }

  /**
   * @return the node label templet
   */
  public String getNodeLabelTemplet() {
    return nodeLabelTemplet;
  }

  /**
   * Set the node label templet.
   * @param nlt the node label templet to set
   */
  public void setNodeLabelTemplet(String nlt) {
    nodeLabelTemplet = nlt;
  }

  /**
   * @return the opened image
   */
  public String getOpenedImage() {
    return openedImage;
  }

  /**
   * Set the opened image.
   * @param oi the opened image to set
   */
  public void setOpenedImage(String oi) {
    openedImage = oi;
  }

  /**
   * @return the closed image
   */
  public String getClosedImage() {
    return closedImage;
  }

  /**
   * Set the closed image.
   * @param ci the closed image to set 
   */
  public void setClosedImage(String ci) {
    closedImage = ci;
  }

  /**
   * @return the leaf image
   */
  public String getLeafImage() {
    return leafImage;
  }

  /**
   * Set the leaf image.
   * 
   * @param li
   *        the leaf image to set
   */
  public void setLeafImage(String li) {
    leafImage = li;
  }

  /**
   * @return the depth per download
   */
  public Integer getDepthPerDownload() {
    return depthPerDownload;
  }

  /**
   * Set the depth per download.
   * 
   * @param dpd
   *        the depth per download to set
   */
  public void setDepthPerDownload(Integer dpd) {
    depthPerDownload = dpd;
  }

}
