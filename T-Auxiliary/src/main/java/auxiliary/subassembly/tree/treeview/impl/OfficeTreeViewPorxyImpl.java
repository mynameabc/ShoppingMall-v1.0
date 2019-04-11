package auxiliary.subassembly.tree.treeview.impl;

import auxiliary.subassembly.tree.treeview.ITreeView;
import model.vo.TreeViewNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("officeTreeViewPorxyImpl")
public class OfficeTreeViewPorxyImpl implements ITreeView {

    private static Logger logger = LoggerFactory.getLogger(OfficeTreeViewPorxyImpl.class);

    @Autowired
    @Qualifier("officeTreeViewImpl")
    private ITreeView officeTreeViewImpl;

    private List<TreeViewNode> newNodeList;

    public List<TreeViewNode> getTree() {

        newNodeList = new ArrayList<>();
        List<TreeViewNode> treeViewNodeList = officeTreeViewImpl.getTree();
        this.format(treeViewNodeList);
        this.computNodeIDS();

        return treeViewNodeList;
    }

    /**
     * 格式化
     * @param treeViewNodeList
     */
    public void format(List<TreeViewNode> treeViewNodeList) {

        for (TreeViewNode treeViewNode : treeViewNodeList) {
            if (null != treeViewNode.getNodes()) {
                treeViewNode.setParentState(1);
                newNodeList.add(treeViewNode);
                format(treeViewNode.getNodes());
            } else {
                treeViewNode.setParentState(0);
            }
        }
    }

    /**
     * 拼接ids
     */
    public void computNodeIDS() {

        int count = this.newNodeList.size();
        for (int index = count - 1; index >= 0; index--) {

            TreeViewNode treeViewNode = this.newNodeList.get(index);
            String ids = "";
            for (TreeViewNode treeViewNode1 : treeViewNode.getNodes()) {

                if (null != treeViewNode1.getNodes()) {
                    ids += "," + treeViewNode1.getOneFormatIDS();
                } else {
                    ids += "," + treeViewNode1.getId();
                }
            }

            treeViewNode.setOneFormatIDS(removeComma(ids));
        }
    }

    /**
     * 去掉字符前后的逗号
     * @param par
     * @return
     */
    public String removeComma(String par) {
        String regex = "^,*|,*$";
        return par.replaceAll(regex, "");
    }

    /**
     * 根据ID返回node
     * @param nodeID
     * @param treeViewNodeList
     * @return
     */
/*
    public TreeViewNode getTreeViewNode(Long nodeID, List<TreeViewNode> treeViewNodeList) {

        if (null == treeViewNodeList)
            return null;

        TreeViewNode _treeViewNode = null;
        for (TreeViewNode treeViewNode : treeViewNodeList) {
            if (treeViewNode.getId() == nodeID) {
                _treeViewNode = treeViewNode;
                break;
            } else {
                _treeViewNode = getTreeViewNode(nodeID, treeViewNode.getNodes());
                if (null != _treeViewNode) {
                    return _treeViewNode;
                }
            }
        }

        return _treeViewNode;
    }
*/
}
