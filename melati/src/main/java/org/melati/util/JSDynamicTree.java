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

    public JSDynamicTree(Tree tree) {
        super(tree.getTreeableRoots(), tree.getDepth());
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public Integer getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(Integer nh) {
        nodeHeight = nh;
    }

    public Integer getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(Integer nw) {
        nodeWidth = nw;
    }

    public String getNodeColour() {
        return nodeColour;
    }

    public void setNodeColour(String nc) {
        nodeColour = nc;
    }

    public Integer getIndent() {
        return indent;
    }

    public void setIndent(Integer i) {
        indent = i;
    }

    public String getNodeLabelTemplet() {
        return nodeLabelTemplet;
    }

    public void setNodeLabelTemplet(String nlt) {
        nodeLabelTemplet = nlt;
    }

    public String getOpenedImage() {
        return openedImage;
    }

    public void setOpenedImage(String oi) {
        openedImage = oi;
    }

    public String getClosedImage() {
        return closedImage;
    }

    public void setClosedImage(String ci) {
        closedImage = ci;
    }

    public String getLeafImage() {
        return leafImage;
    }

    public void setLeafImage(String li) {
        leafImage = li;
    }

    public Integer getDepthPerDownload() {
        return depthPerDownload;
    }

    public void setDepthPerDownload(Integer dpd) {
        depthPerDownload = dpd;
    }

}
