package org.melati.util;

import org.melati.util.*;
import java.util.*;

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

    public Vector flattened(int depth, boolean depthFirst) {

        Vector results = new Vector();
        Vector agenda = new Vector();
        for(int i=0; i<roots.length; i++) {
            agenda.addElement(roots[i]);
        }

        while (!agenda.isEmpty()) {
            TreeNode current = (TreeNode)agenda.firstElement();
            agenda.removeElementAt(0);
            results.addElement(current);

            if (depth < 0 || current.depth < depth) {
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

    public Vector flattened(int depth) {
        return flattened(depth, true);
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
    public Vector apply(Function func, int depth, boolean depthFirst) {
        Vector flattened = flattened(depth, depthFirst);
        for(int i=0; i<flattened.size(); i++) {
            flattened.setElementAt(func.apply(flattened.elementAt(i)), i);
        }
        return flattened;
    }

    public Vector applyDepthFirst(Function func) {
        return apply(func, -1, true);
    }

    public Vector applyDepthFirst(Function func, int depth) {
        return apply(func, depth, true);
    }

    public Vector applyBreadthFirst(Function func) {
        return apply(func, -1, false);
    }

    public Vector applyBreadthFirst(Function func, int depth) {
        return apply(func, depth, false);
    }

    public Vector sorted(Order cmp, int depth) {
        Vector flattened = flattened(depth);
        Object[] sorted = SortUtils.sorted(cmp, flattened);
        System.arraycopy(sorted, 0, flattened, 0, sorted.length);
        return flattened;
    }

}

