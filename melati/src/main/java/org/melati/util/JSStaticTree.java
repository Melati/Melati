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
 *     Myles Chippendale <mylesc@paneris.org>
 */
package org.melati.util;

/**
 * A JavaScript tree which can be rendered as ordinary HTML.
 *
 * @todo Rename to JsHtmlTree
 */
public class JSStaticTree extends Tree {

    private String nodeLabelTemplet = 
           "org/melati/template/webmacro/templets/html/StaticNode.wm";
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
     * @deprecated Use {@link #JSStaticTree(Tree, String)}
     */
    public JSStaticTree(Tree tree) {
      this(tree, "/melati-static/admin");
    }

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

    public String getNodeLabelTemplet() {
        return nodeLabelTemplet;
    }

    public void setNodeLabelTemplet(String nlt) {
        nodeLabelTemplet = nlt;
    }

    public String getBackgroundColour() {
        return backgroundColour;
    }

    public void setBackgroundColour(String bg) {
        backgroundColour = bg;
    }

    public String getVerticalLinkImage() {
        return verticalLinkImage;
    }

    public void setVerticalLinkImage(String vli) {
        verticalLinkImage = vli;
    }

    public String getSpacerImage() {
        return spacerImage;
    }

    public void setSpacerImage(String si) {
        spacerImage = si;
    }

    public String getOpenedFolderImage() {
        return openedFolderImage;
    }

    public void setOpenedFolderImage(String ofi) {
        openedFolderImage = ofi;
    }

    public String getClosedFolderImage() {
        return closedFolderImage;
    }

    public void setClosedFolderImage(String cfi) {
        closedFolderImage = cfi;
    }

    public String getOpenedTImage() {
        return openedTImage;
    }

    public void setOpenedTImage(String oti) {
        openedTImage = oti;
    }

    public String getOpenedLImage() {
        return openedLImage;
    }

    public void setOpenedLImage(String oli) {
        openedLImage = oli;
    }

    public String getClosedTImage() {
        return closedTImage;
    }

    public void setClosedTImage(String cti) {
        closedTImage = cti;
    }

    public String getClosedLImage() {
        return closedLImage;
    }

    public void setClosedLImage(String cli) {
        closedLImage = cli;
    }

    public String getLeafTImage() {
        return leafTImage;
    }

    public void setLeafTImage(String li) {
        leafTImage = li;
    }

    public String getLeafLImage() {
        return leafLImage;
    }

    public void setLeafLImage(String li) {
        leafLImage = li;
    }

    public String getLeafImage() {
        return leafImage;
    }

    public void setLeafImage(String li) {
        leafImage = li;
    }

    public String getImageBaseRef() {
        return imageBaseRef;
    }

    public void setImageBaseRef(String ibr) {
        imageBaseRef = ibr;
    }

    public Integer getDepthPerDownload() {
        return depthPerDownload;
    }

    public void setDepthPerDownload(Integer dpd) {
        depthPerDownload = dpd;
    }

}




