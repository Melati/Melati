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
