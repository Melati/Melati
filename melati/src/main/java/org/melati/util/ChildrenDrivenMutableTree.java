package org.melati.util;

import javax.swing.tree.DefaultMutableTreeNode;
import java.io.*;
import java.util.*;

/**
 * A tree of <code>DefaultMutableTreeNode</code>s, the userObjects of which
 * are <code>Treeable</code> objects which supply their own
 * <code>getChildren()</code> functions. This is used to build the tree of
 * <code>DefaultMutableTreeNode</code>s.
 * <p>
 * It also allows you to search the subtree for a particular
 * <code>Treeable</code> object and returns the corresponding
 * <code>DefaultMutableTreeNode</code> object if one exists.
 *
 * @see DefaultMutableTreeNode
 *
 * @version 1.00 04/10/2000
 * @author Myles Chippendale
 **/


public class ChildrenDrivenMutableTree
{

    /**
     * An enumeration that is always empty. This is used when an enumeration
     * of a leaf node's children is requested.
     */
    static public final Enumeration EMPTY_ENUMERATION
	= new Enumeration() {
	    public boolean hasMoreElements() { return false; }
	    public Object nextElement() {
		throw new NoSuchElementException("No more elements");
	    }
    };

    /** root node */
    protected DefaultMutableTreeNode root;

    public ChildrenDrivenMutableTree() {
      this(null);
    }

    public ChildrenDrivenMutableTree(Treeable userObject) {
      root = new DefaultMutableTreeNode(userObject);
      buildTree();
    }

    public void buildTree() {
      buildTree(computeChildren(root));
    }

    public void buildTree(Enumeration nodes) {
      while (nodes.hasMoreElements())
        buildTree(computeChildren((DefaultMutableTreeNode)nodes.nextElement()));
    }

    static private Enumeration computeChildren(DefaultMutableTreeNode node) {
      if (node == null)
        return EMPTY_ENUMERATION;
      Treeable[] kids = ((Treeable)node.getUserObject()).getChildren();
      for(int i = 0; i<kids.length; i++) {
          node.add(new DefaultMutableTreeNode(kids[i]));
      }
      return node.children();
    }


    public DefaultMutableTreeNode getTreeNodeFor(Treeable search) {

        Vector agenda = new Vector();
        agenda.addElement(root);

        while (!agenda.isEmpty()) {
            DefaultMutableTreeNode current =
                (DefaultMutableTreeNode)agenda.firstElement();
            if (current == null)
              return null;
            if (current.getUserObject() == search)
              return current;

            agenda.removeElementAt(0);
            Enumeration kids = current.children();
            while(kids.hasMoreElements()) {
              agenda.addElement(kids.nextElement());
            }
        }
        return null;
    }

    public DefaultMutableTreeNode getRoot() {
      return root;
    }

    public Enumeration preorderEnumeration() {
      return root.preorderEnumeration();
    }
    public Enumeration postorderEnumeration() {
      return root.postorderEnumeration();
    }
    public Enumeration breadthFirstEnumeration() {
      return root.breadthFirstEnumeration();
    }
    public Enumeration depthFirstEnumeration() {
      return root.depthFirstEnumeration();
    }
}
