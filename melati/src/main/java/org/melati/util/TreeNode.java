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
 *     Myles Chippendale <MylesC@paneris.org>
 */

package org.melati.util;

import java.util.Vector;

public class TreeNode {
    private Treeable data;
    int depth;
    protected TreeNode parent = null;
    private TreeNode[] children = null;
    private boolean checkedForParent = false;
    private boolean checkedForChildren = false;
    
    public TreeNode(Treeable n, int d) {
        data = n;
        depth = d;
    }

    public boolean isRoot() {
        return (parent == null);
    }

    public boolean isLeaf() {
        return (getChildren() == null);
    }

    public int getDepth() {
        return depth;
    }

    public Treeable getData() {
        return data;
    }

    public TreeNode getParent() {
        return parent;
    }

    public String getUniqueName() {
        int code = hashCode();
        String name = "";
        if (code < 0) {
            code = -code;
            name = "Z";
        }
        return name + Integer.toString(code, java.lang.Character.MAX_RADIX);
    }

    public synchronized TreeNode[] getChildren() {
        if (checkedForChildren == true)
            return children;
       
        Treeable[] kids = data.getChildren();
        if (kids == null || kids.length == 0)
            children = null;
        else {
            children = augment(kids, depth + 1);
            for (int i = 0; i<children.length; i++)
                children[i].parent = this;
        }
        checkedForChildren = true;

        return children;
    }

    public TreeNode[] getNodeToRootPath(boolean includeNode, boolean reverse) {
        Vector path = new Vector();
        if (includeNode)
            path.addElement(this);

        TreeNode current = this;

        while (!current.isRoot()) {
            current = current.parent;
            if (reverse)
                path.insertElementAt(current, 0);
            else
                path.addElement(current);
        }

        TreeNode[] result = new TreeNode[path.size()];
        return (TreeNode[])path.toArray(result);
    }

    public TreeNode[] getPathToRoot() {
        return getNodeToRootPath(false, false);
    }

    public TreeNode[] getPathFromRoot() {
        return getNodeToRootPath(false, true);
    }

    public static TreeNode[] augment(Treeable[] nodes, int depth) {
        TreeNode[] t = new TreeNode[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            t[i] = new TreeNode(nodes[i], depth);
        }
        return t;
    }
}






