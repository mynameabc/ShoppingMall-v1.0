package model.vo;

import java.util.List;

public class TreeViewNode implements java.io.Serializable {

    private Integer id;
    private String text;
    private String icon;
    private String selectedIcon;
    private String href;
    private String selectable;
    private String color;
    private String backColor;
    private String tags;
    private String oneFormatIDS;
    private Integer parentID;
    private int parentState;

    private List<TreeViewNode> nodes;

    public int getParentState() {
        return parentState;
    }

    public void setParentState(int parentState) {
        this.parentState = parentState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getSelectedIcon() {
        return selectedIcon;
    }

    public void setSelectedIcon(String selectedIcon) {
        this.selectedIcon = selectedIcon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSelectable() {
        return selectable;
    }

    public void setSelectable(String selectable) {
        this.selectable = selectable;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBackColor() {
        return backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getOneFormatIDS() {
        return oneFormatIDS;
    }

    public void setOneFormatIDS(String oneFormatIDS) {
        this.oneFormatIDS = oneFormatIDS;
    }

    public Integer getParentID() {
        return parentID;
    }

    public void setParentID(Integer parentID) {
        this.parentID = parentID;
    }

    public List<TreeViewNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewNode> nodes) {
        this.nodes = nodes;
    }
}
