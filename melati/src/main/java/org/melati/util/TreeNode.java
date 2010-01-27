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
 *     Myles Chippendale <MylesC At paneris.org>
 */

package org.melati.util;

import java.util.Vector;
import org.melati.poem.Treeable;

/**
 * A {@link Tree} node.
 *
 * @author MylesC At paneris.org
 *
 */
public class TreeNode {
    private Treeable data;
    int depth;
    protected TreeNode parent = null;
    private TreeNode[] children = null;
    private boolean checkedForChildren = false;
    
    /**
     * Constructor.
     * @param n the Treeable object 
     * @param d the depth of this node
     */
    public TreeNode(Treeable n, int d) {
        data = n;
        depth = d;
    }

    /**
     * @return whether this is a root node
     */
    public boolean isRoot() {
        return (parent == null);
    }

    /**
     * @return whether this is a terminal node
     */
    public boolean isLeaf() {
        return (getChildren() == null);
    }

    /**
     * @return the depth in the tree
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return the Treeable data object this wraps 
     */
    public Treeable getData() {
        return data;
    }

    /**
     * @return theis nodes parent, null if this is a root node
     */
    public TreeNode getParent() {
        return parent;
    }

    /**
     * @return the name unique within the Tree
     */
    public String getUniqueName() {
        int code = hashCode();
        String name = "";
        if (code < 0) {
            code = -code;
            name = "Z";
        }
        return name + Integer.toString(code, java.lang.Character.MAX_RADIX);
    }

    /**
     * @return the descendants of this node, maybe an empty Array
     */
    public synchronized TreeNode[] getChildren() {
        if (checkedForChildren)
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

    /**
     * Retrieve an Array of TreeNodes which is the shortest path from 
     * this node to its root.
     *  
     * @param includeNode whether to include this node in the path
     * @param reverse if true returns a path from root to this
     * @return an Array of TreeNodes
     */
    public TreeNode[] getNodeToRootPath(boolean includeNode, boolean reverse) {
        Vector<TreeNode> path = new Vector<TreeNode>();
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

    /**
     * @return the path to this node's root excluding this node 
     */
    public TreeNode[] getPathToRoot() {
        return getNodeToRootPath(false, false);
    }

    /**
     * @return the path from this node's root excluding this node
     */
    public TreeNode[] getPathFromRoot() {
        return getNodeToRootPath(false, true);
    }

    /**
     * Create a new Array of TreeNodes specifying a new depth.
     * @param nodes the nodes to copy
     * @param depth the new depth
     * @return a new Array of TreeNodes with the new depth
     */
    public static TreeNode[] augment(Treeable[] nodes, int depth) {
        TreeNode[] t = new TreeNode[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            t[i] = new TreeNode(nodes[i], depth);
        }
        return t;
    }
}






