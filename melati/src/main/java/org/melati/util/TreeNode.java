package org.melati.util;

import org.melati.util.*;
import java.util.*;

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

    synchronized public TreeNode[] getChildren() {
        if (checkedForChildren == true)
            return children;
       
        Treeable[] kids = data.getChildren();
        if (kids == null || kids.length == 0)
            children = null;
        else {
            children = augment(kids, depth+1);
            for(int i=0; i<children.length; i++)
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

    static public TreeNode[] augment(Treeable[] nodes, int depth) {
        TreeNode[] t = new TreeNode[nodes.length];
        for (int i=0; i<nodes.length; i++) {
            t[i] = new TreeNode(nodes[i], depth);
        }
        return t;
    }
}

