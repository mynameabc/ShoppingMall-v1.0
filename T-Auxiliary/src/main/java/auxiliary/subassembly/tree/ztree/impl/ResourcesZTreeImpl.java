package auxiliary.subassembly.tree.ztree.impl;

import auxiliary.subassembly.tree.ztree.IZTree;
import communal.util.LogUtil;
import com.mapper.table.ResourcesMapper;
import model.entity.Resources;
import model.vo.ZTreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("resourcesZTreeImpl")
public class ResourcesZTreeImpl implements IZTree {

    private static Logger logger = LoggerFactory.getLogger(ResourcesZTreeImpl.class);

    @Autowired
    private ResourcesMapper resourcesMapper;

    //这个传入id和type两个参数
    public List<ZTreeNode> getTree(String ... parameter) {

        List<Resources> list = null;

        //排除掉主键值为id的资源
        try {
            Example example = new Example(Resources.class);
            Example.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(parameter[0])) {
                Integer id = Integer.valueOf(parameter[0].toString());
                criteria.andNotEqualTo("resourcesID", id);
            }
            example.orderBy("sort");
            list = resourcesMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }

        if (parameter[1].equals("MENU")) {
            list = list.stream().filter(object -> object.getType() == 1).collect(Collectors.toList());      //过滤掉 按钮
        }

        List<Resources> firstList = list.stream().filter(object -> object.getParentID() == 0).collect(Collectors.toList());
        List<Resources> otherList = list.stream().filter(object -> object.getParentID() != 0).collect(Collectors.toList());

        List<ZTreeNode> nodeList = new ArrayList<>();

        for (Resources object : firstList) {
            nodeList.add(getNode(otherList, object));
        }

        return nodeList;
    }

    /**
     * 返回菜单JSON
     * @param treeNodeList
     * @return
     */
    public List<ZTreeNode> getMenuString(List<Resources> treeNodeList) {

        List<Resources> firstList = treeNodeList.stream().filter(object -> object.getParentID() == 0).collect(Collectors.toList());
        List<Resources> otherList = treeNodeList.stream().filter(object -> object.getParentID() != 0).collect(Collectors.toList());

        List<ZTreeNode> nodeList = new ArrayList<>();

        for (Resources object : firstList) {
            nodeList.add(getNode(otherList, object));
        }

        return nodeList;
    }

    private ZTreeNode getNode(List<Resources> otherList, Resources resourcesPar) {

        ZTreeNode node = this.setValues(resourcesPar);
        if (otherList.size() != 0) {
            List<ZTreeNode> zTreeNodeList = new ArrayList<>();
            List<Resources> tempList = otherList.stream().filter(object -> object.getParentID() == resourcesPar.getResourcesID()).collect(Collectors.toList());
            for (Resources object : tempList) {
                zTreeNodeList.add(getNode(otherList, object));
            }
            if (zTreeNodeList.size() >= 1) {
                node.setChildren(zTreeNodeList);
            }
        } else {
            node.setChildren(null);
        }

        return node;
    }

    private ZTreeNode setValues(Resources resourcesPar) {
        ZTreeNode node = new ZTreeNode();
        node.setId(resourcesPar.getResourcesID());
        node.setName(resourcesPar.getTitle());
        node.setpId(resourcesPar.getParentID());
        node.setHref(resourcesPar.getHref());

        if (resourcesPar.getType() == 1) {

        } else {
            resourcesPar.setIcon("../../static/plugin/zTree/v3/css/bootstrapStyle/img/3.png");
        }

        node.setIcon(resourcesPar.getIcon());
        return node;
    }
}
