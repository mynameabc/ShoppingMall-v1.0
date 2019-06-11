package auxiliary.subassembly.tree.ztree.impl;

import auxiliary.subassembly.tree.ztree.IZTree;
import communal.util.LogUtil;
import com.mapper.table.OfficeMapper;
import model.entity.Office;
import model.vo.ZTreeNode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("officeZTreeImpl")
public class OfficeZTreeImpl implements IZTree {

    private static Logger logger = LogManager.getLogger(OfficeZTreeImpl.class);

    @Autowired
    private OfficeMapper officeMapper;

    public List<ZTreeNode> getTree(String ... parameter) {

        Integer id;
        List<Office> list = null;
        try {
            Example example = new Example(Office.class);
            Example.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(parameter[0])) {
                id = Integer.valueOf(parameter[0].toString());
                criteria.andNotEqualTo("officeID", id);
            }
            example.orderBy("sort");
            list = officeMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }

        List<Office> firstList = list.stream().filter(office -> office.getParentID() == 0).collect(Collectors.toList());
        List<Office> otherList = list.stream().filter(office -> office.getParentID() != 0).collect(Collectors.toList());

        ZTreeNode node = null;
        List<ZTreeNode> treeNodeList = new ArrayList<>();

        for (Office office : firstList) {
            node = getNode(otherList, office);
            treeNodeList.add(node);
        }

        return treeNodeList;
    }

    private ZTreeNode getNode(List<Office> officeList, Office officePar) {

        ZTreeNode node = this.setValues(officePar);
        if (officeList.size() != 0) {
            List<ZTreeNode> zTreeNodeList = new ArrayList<>();
            List<Office> _tempList = officeList.stream().filter(Office -> Office.getParentID() == officePar.getOfficeID()).collect(Collectors.toList());
            for (Office office : _tempList) {
                zTreeNodeList.add(getNode(officeList, office));
            }
            if (zTreeNodeList.size() >= 1) {
                node.setChildren(zTreeNodeList);
            }
        } else {
            node.setChildren(null);
        }

        return node;
    }

    private ZTreeNode setValues(Office officePar) {
        ZTreeNode node = new ZTreeNode();
        node.setId(officePar.getOfficeID());
        node.setName(officePar.getName());
        node.setpId(officePar.getParentID());
        return node;
    }
}
