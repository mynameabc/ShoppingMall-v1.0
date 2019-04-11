package auxiliary.subassembly.tree.treeview.impl;

import auxiliary.subassembly.tree.treeview.ITreeView;
import communal.util.LogUtil;
import com.mapper.table.OfficeMapper;
import model.entity.Office;
import model.vo.TreeViewNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("officeTreeViewImpl")
public class OfficeTreeViewImpl implements ITreeView {

    private static Logger logger = LoggerFactory.getLogger(OfficeTreeViewImpl.class);

    @Autowired
    private OfficeMapper officeMapper;

    public List<TreeViewNode> getTree() {

        List<Office> list = null;
        try {
            Example example = new Example(Office.class);
            list = officeMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
            return null;
        }

        List<Office> firstList = list.stream().filter(office -> office.getParentID() == 0).collect(Collectors.toList());
        List<Office> otherList = list.stream().filter(office -> office.getParentID() != 0).collect(Collectors.toList());

        TreeViewNode node = null;
        List<TreeViewNode> treeNodeList = new ArrayList<>();

        for (Office office : firstList) {
            node = getNode(otherList, office);
            treeNodeList.add(node);
        }

        if (treeNodeList.size() >= 2) {
            List<TreeViewNode> allTreeNodeList = new ArrayList<>();
            TreeViewNode treeViewNode = new TreeViewNode();
            treeViewNode.setId(0);
            treeViewNode.setText("全部");
            treeViewNode.setNodes(treeNodeList);

            allTreeNodeList.add(treeViewNode);
            return allTreeNodeList;
        } else {
            return treeNodeList;
        }
    }

    private TreeViewNode getNode(List<Office> officeList, Office officePar) {

        TreeViewNode node = this.setValues(officePar);
        if (officeList.size() != 0) {
            List<TreeViewNode> treeViewNodeList = new ArrayList<>();
            List<Office> _tempList = officeList.stream().filter(Office -> Office.getParentID() == officePar.getOfficeID()).collect(Collectors.toList());
            for (Office office : _tempList) {
                treeViewNodeList.add(getNode(officeList, office));
            }

            if (treeViewNodeList.size() >= 1) {
                node.setNodes(treeViewNodeList);
            }
        }

        return node;
    }

    private TreeViewNode setValues(Office officePar) {
        TreeViewNode node = new TreeViewNode();
        node.setId(officePar.getOfficeID());
        node.setText(officePar.getName());
        node.setParentID(officePar.getParentID());
        return node;
    }
}
