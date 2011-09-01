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

import java.util.Vector;

import org.melati.poem.util.Order;
import org.melati.poem.util.SortUtils;
import org.melati.poem.Treeable;

/**
 * A whole tree.
 */
public class Tree {
  private Treeable[] orig_roots;
  private TreeNode[] roots;
  private int depth;

  /**
   * Constructor from root node.
   * @param node root node
   */
  public Tree(Treeable node) {
    orig_roots = new Treeable[1];
    orig_roots[0] = node;
    this.depth = 0;
    roots = new TreeNode[] { new TreeNode(node, 0) };
  }

  /**
   * Constructor from root node with anticipated depth.
   * @param node root node
   * @param depth the anticipated depth
   */
  public Tree(Treeable node, int depth) {
    orig_roots = new Treeable[1];
    orig_roots[0] = node;
    this.depth = depth;
    roots = new TreeNode[] { new TreeNode(node, depth) };
  }

  /**
   * Constructor for a multi-rooted tree.
   * @param nodes the root nodes
   */
  public Tree(Treeable[] nodes) {
    orig_roots = nodes;
    this.depth = 0;
    roots = TreeNode.augment(nodes, 0);
  }

  /**
   * Constructor for a multi-rooted tree with anticipated depth.
   * @param nodes the root nodes
   * @param depth anticipated depth
   */
  public Tree(Treeable[] nodes, int depth) {
    orig_roots = nodes;
    this.depth = depth;
    roots = TreeNode.augment(nodes, depth);
  }

  /**
   * @return the roots
   */
  public Treeable[] getTreeableRoots() {
    return orig_roots;
  }

  /**
   * @return the depth, possibly anticipated
   */
  public int getDepth() {
    return depth;
  }

  /**
   * Retrieve all the nodes down to a given depth.
   * @param depthP
   *        Only apply the function to nodes at or above this depth. A negative
   *        depth means apply this to all nodes in the tree
   * @param depthFirst
   *        If true, traverse the tree depth-first, otherwise traverse it
   *        breadth-first
   * @return all the nodes down to the given depth
   */
  public Vector<TreeNode> flattened(int depthP, boolean depthFirst) {

    Vector<TreeNode> results = new Vector<TreeNode>();
    Vector<TreeNode> agenda = new Vector<TreeNode>();
    for (int i = 0; i < roots.length; i++) {
      agenda.addElement(roots[i]);
    }

    while (!agenda.isEmpty()) {
      TreeNode current = (TreeNode)agenda.firstElement();
      agenda.removeElementAt(0);
      results.addElement(current);

      if (depthP < 0 || current.depth < depthP) {
        TreeNode[] kids = current.getChildren();

        if (kids != null) {
          for (int i = 0; i < kids.length; i++) {
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

  /**
   * Retrieve all the nodes down to a given depth, depth-first.
   * @param depthP
   *        Only apply the function to nodes at or above this depth. A negative
   *        depth means apply this to all nodes in the tree
   * @return all the nodes down to the given depth
   */
  public Vector<TreeNode> flattened(int depthP) {
    return flattened(depthP, true);
  }

  /**
   * @return all the nodes, depth-first
   */
  public Vector<TreeNode> flattened() {
    return flattened(-1);
  }

  /**
   * Apply the Function to each node in the tree.
   * 
   * @param func the Function to apply
   * @param depthP
   *        Only apply the function to nodes at or above this depth. A negative
   *        depth means apply this to all nodes in the tree
   * @param depthFirst
   *        If true, traverse the tree depth-first, otherwise traverse it
   *        breadth-first
   * @return a Vector nodes that have had func applied to them
   */
  public Vector<TreeNode> apply(Function func, int depthP, boolean depthFirst) {
    Vector<TreeNode> flattened = flattened(depthP, depthFirst);
    for (int i = 0; i < flattened.size(); i++) {
      flattened.setElementAt((TreeNode)func.apply(flattened.elementAt(i)), i);
    }
    return flattened;
  }

  /**
   * Apply a function to all nodes, depth first.
   * @param func the function to apply
   * @return a vector of nodes to which the function has been applied
   */
  public Vector<TreeNode> applyDepthFirst(Function func) {
    return apply(func, -1, true);
  }

  /**
   * Apply a function to all nodes to a given depth, depth first.
   * @param func the function to apply
   * @param depthP
   *        Only apply the function to nodes at or above this depth. A negative
   *        depth means apply this to all nodes in the tree
   * @return a vector of nodes to which the function has been applied
   */
  public Vector<TreeNode> applyDepthFirst(Function func, int depthP) {
    return apply(func, depthP, true);
  }

  /**
   * Apply a function to all nodes, breadth first.
   * @param func the function to apply
   * @return a vector of nodes to which the function has been applied
   */
  public Vector<TreeNode> applyBreadthFirst(Function func) {
    return apply(func, -1, false);
  }

  /**
   * Apply a function to all nodes to a given depth, breadth first.
   * @param func the function to apply
   * @param depthP
   *        Only apply the function to nodes at or above this depth. A negative
   *        depth means apply this to all nodes in the tree
   * @return a vector of nodes to which the function has been applied
   */
  public Vector<TreeNode> applyBreadthFirst(Function func, int depthP) {
    return apply(func, depthP, false);
  }

  /**
   * Retrieve a sorted Vector of the tree's contents, down to the given depth. 
   * @param cmp an Ordering
   * @param depthP
   *        Only return nodes at or above this depth. A negative
   *        depth means return all nodes in the tree
   * @return a sorted Vector of the tree's contents, down to the given depth
   */
  public Vector<TreeNode> sorted(Order cmp, int depthP) {
    Vector<TreeNode> flattened = flattened(depthP);
    TreeNode[] sorted = SortUtils.sorted(cmp, flattened);
    System.arraycopy(sorted, 0, flattened, 0, sorted.length);
    return flattened;
  }

}
