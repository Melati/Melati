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

import java.util.Vector;

/**
 * A whole tree.
 *
 */
public class Tree {
    private Treeable[] orig_roots;
    private TreeNode[] roots;
    private int depth;

    public Tree(Treeable node) {
        orig_roots = new Treeable[1];
        orig_roots[0] = node;
        this.depth = 0;
        roots = new TreeNode[] {new TreeNode(node, 0)};
    }

    public Tree(Treeable node, int depth) {
        orig_roots = new Treeable[1];
        orig_roots[0] = node;
        this.depth = depth;
        roots = new TreeNode[] {new TreeNode(node, depth)};
    }

    public Tree(Treeable[] nodes) {
        orig_roots = nodes;
        this.depth = 0;
        roots = TreeNode.augment(nodes, 0);
    }

    public Tree(Treeable[] nodes, int depth) {
        orig_roots = nodes;
        this.depth = depth;
        roots = TreeNode.augment(nodes, depth);
    }

    public Treeable[] getTreeableRoots() {
        return orig_roots;
    }

    public int getDepth() {
        return depth;
    }

    public Vector flattened(int depthP, boolean depthFirst) {

        Vector results = new Vector();
        Vector agenda = new Vector();
        for(int i=0; i<roots.length; i++) {
            agenda.addElement(roots[i]);
        }

        while (!agenda.isEmpty()) {
            TreeNode current = (TreeNode)agenda.firstElement();
            agenda.removeElementAt(0);
            results.addElement(current);

            if (depthP < 0 || current.depth < depthP) {
                TreeNode[] kids = current.getChildren();

                if (kids != null) {
                    for(int i=0; i<kids.length; i++) {
                        if (depthFirst)
                            // Maybe not the most efficient ...?
                            agenda.insertElementAt(kids[i], i);
                        else
                            agenda.addElement(kids[i]);
                    }
                }
            }
        }
        return results;
    }

    public Vector flattened(int depthP) {
        return flattened(depthP, true);
    }

    public Vector flattened() {
        return flattened(-1);
    }

    /**
     * Apply the Function to each node in the tree.
     *
     * @param depth  Only apply the function to nodes at or above
     *               this depth. A negative depth means apply this
     *               to all nodes in the tree
     * @param depthFirst If true, traverse the tree depth-first, otherwise
     *                   traverse it breadth-first
     **/
    public Vector apply(Function func, int depthP, boolean depthFirst) {
        Vector flattened = flattened(depthP, depthFirst);
        for(int i=0; i<flattened.size(); i++) {
            flattened.setElementAt(func.apply(flattened.elementAt(i)), i);
        }
        return flattened;
    }

    public Vector applyDepthFirst(Function func) {
        return apply(func, -1, true);
    }

    public Vector applyDepthFirst(Function func, int depthP) {
        return apply(func, depthP, true);
    }

    public Vector applyBreadthFirst(Function func) {
        return apply(func, -1, false);
    }

    public Vector applyBreadthFirst(Function func, int depthP) {
        return apply(func, depthP, false);
    }

    public Vector sorted(Order cmp, int depthP) {
        Vector flattened = flattened(depthP);
        Object[] sorted = SortUtils.sorted(cmp, flattened);
        System.arraycopy(sorted, 0, flattened, 0, sorted.length);
        return flattened;
    }

}

