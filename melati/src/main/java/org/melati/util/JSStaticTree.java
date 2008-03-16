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

import org.melati.poem.Treeable;

/**
 * A JavaScript tree which can be rendered as ordinary HTML.
 */
public class JSStaticTree extends Tree {

  private String backgroundColour = "FFFFFF";
  private Integer depthPerDownload = new Integer(-1);
  private String verticalLinkImage;
  private String spacerImage;
  private String openedFolderImage;
  private String closedFolderImage;
  private String openedTImage;
  private String closedTImage;
  private String openedLImage;
  private String closedLImage;
  private String leafTImage;
  private String leafLImage;
  private String leafImage;
  private String imageBaseRef;

  /**
   * Constructor.
   * 
   * @param tree
   *        the Tree to render
   * @param staticURL
   *        images base url
   */
  public JSStaticTree(Tree tree, String staticURL) {
    super(tree.getTreeableRoots(), tree.getDepth());
    verticalLinkImage = staticURL + "/vertline.gif";
    spacerImage = staticURL + "/spacer.gif";
    openedFolderImage = staticURL + "/openfolder.gif";
    closedFolderImage = staticURL + "/closedfolder.gif";
    openedTImage = staticURL + "/node_minus.gif";
    closedTImage = staticURL + "/node_plus.gif";
    openedLImage = staticURL + "/lastnode_minus.gif";
    closedLImage = staticURL + "/lastnode_plus.gif";
    leafTImage = staticURL + "/node.gif";
    leafLImage = staticURL + "/last_node.gif";
    leafImage = staticURL + "/file.gif";
    imageBaseRef = staticURL;
  }

  /**
   * Constructor for an array.
   * 
   * @param nodes
   * @param staticURL
   */
  public JSStaticTree(Treeable[] nodes, String staticURL) { 
    super(nodes);    
    verticalLinkImage = staticURL + "/vertline.gif";
    spacerImage = staticURL + "/spacer.gif";
    openedFolderImage = staticURL + "/openfolder.gif";
    closedFolderImage = staticURL + "/closedfolder.gif";
    openedTImage = staticURL + "/node_minus.gif";
    closedTImage = staticURL + "/node_plus.gif";
    openedLImage = staticURL + "/lastnode_minus.gif";
    closedLImage = staticURL + "/lastnode_plus.gif";
    leafTImage = staticURL + "/node.gif";
    leafLImage = staticURL + "/last_node.gif";
    leafImage = staticURL + "/file.gif";
    imageBaseRef = staticURL;
  }

  /**
   * @return the background colour hex string (without a hash)
   */
  public String getBackgroundColour() {
    return backgroundColour;
  }

  /**
   * Set the background colour. 
   * 
   * @param bg the background colour hex string (without a hash)
   */
  public void setBackgroundColour(String bg) {
    backgroundColour = bg;
  }

  /**
   * @return the vertical link image
   */
  public String getVerticalLinkImage() {
    return verticalLinkImage;
  }

  /**
   * Set the vertical link image.
   * @param vli the vertical link image
   */
  public void setVerticalLinkImage(String vli) {
    verticalLinkImage = vli;
  }

  /**
   * @return the spacer image name
   */
  public String getSpacerImage() {
    return spacerImage;
  }

  /**
   * Set the spacer image name.
   * @param si the spacer image name to set
   */
  public void setSpacerImage(String si) {
    spacerImage = si;
  }

  /**
   * @return the opened folder image name
   */
  public String getOpenedFolderImage() {
    return openedFolderImage;
  }

  /**
   * Set the opened folder image name.
   * @param ofi the opened folder image name to set
   */
  public void setOpenedFolderImage(String ofi) {
    openedFolderImage = ofi;
  }

  /**
   * @return the closed folder image name
   */
  public String getClosedFolderImage() {
    return closedFolderImage;
  }

  /**
   * Set the closed folder image name.
   * @param cfi the closed folder image name to set
   */
  public void setClosedFolderImage(String cfi) {
    closedFolderImage = cfi;
  }

  /**
   * @return the opened Tee image 
   */
  public String getOpenedTImage() {
    return openedTImage;
  }

  /**
   * Set the opened Tee image.
   * @param oti the opened Tee image to set
   */
  public void setOpenedTImage(String oti) {
    openedTImage = oti;
  }

  /**
   * @return the opened L image
   */
  public String getOpenedLImage() {
    return openedLImage;
  }

  /**
   * Set the opened L image.
   * @param oli the opened L image to set
   */
  public void setOpenedLImage(String oli) {
    openedLImage = oli;
  }

  /**
   * @return the closed Tee image
   */
  public String getClosedTImage() {
    return closedTImage;
  }

  /**
   * Set the closed Tee image.
   * @param cti the closed Tee image
   */
  public void setClosedTImage(String cti) {
    closedTImage = cti;
  }

  /**
   * @return the closed L image
   */
  public String getClosedLImage() {
    return closedLImage;
  }

  /**
   * Set the closed L image.
   * @param cli the closed L image
   */
  public void setClosedLImage(String cli) {
    closedLImage = cli;
  }

  /**
   * @return the Leaf Tee image
   */
  public String getLeafTImage() {
    return leafTImage;
  }

  /**
   * Set the Leaf Tee image.
   * @param lti the Leaf Tee image to set
   */
  public void setLeafTImage(String lti) {
    leafTImage = lti;
  }

  /**
   * @return the leaf L image
   */
  public String getLeafLImage() {
    return leafLImage;
  }

  /**
   * Set the leaf L image.
   * @param lli the leaf L image to set
   */
  public void setLeafLImage(String lli) {
    leafLImage = lli;
  }

  /**
   * @return the leaf image
   */
  public String getLeafImage() {
    return leafImage;
  }

  /**
   * Set the leaf image.
   * @param li the leaf image to set
   */
  public void setLeafImage(String li) {
    leafImage = li;
  }

  /**
   * @return the image base href
   */
  public String getImageBaseRef() {
    return imageBaseRef;
  }

  /**
   * Set the image base href.
   * @param ibr the image base href to set
   */
  public void setImageBaseRef(String ibr) {
    imageBaseRef = ibr;
  }

  /**
   * @return the depth per download
   */
  public Integer getDepthPerDownload() {
    return depthPerDownload;
  }

  /**
   * Set the depth per download.
   * @param dpd the depth per download to set
   */
  public void setDepthPerDownload(Integer dpd) {
    depthPerDownload = dpd;
  }

}
