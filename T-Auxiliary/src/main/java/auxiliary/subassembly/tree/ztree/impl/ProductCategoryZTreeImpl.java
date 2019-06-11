package auxiliary.subassembly.tree.ztree.impl;

import auxiliary.subassembly.tree.ztree.IZTree;
import com.mapper.table.ProductCategoryMapper;
import communal.util.LogUtil;
import model.entity.ProductCategory;
import model.vo.ZTreeNode;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component("productCategoryZTreeImpl")
public class ProductCategoryZTreeImpl implements IZTree {

    private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(ProductCategoryZTreeImpl.class);

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    public List<ZTreeNode> getTree(String ... parameter) {

        List<ProductCategory> list = null;

        //排除掉主键值为id的资源
        try {
            Example example = new Example(ProductCategory.class);
            Example.Criteria criteria = example.createCriteria();
            if (!StringUtils.isEmpty(parameter[0])) {
                Integer id = Integer.valueOf(parameter[0].toString());
                criteria.andNotEqualTo("categoryID", id);
            }
            example.orderBy("sort");
            list = productCategoryMapper.selectByExample(example);
        } catch (Exception e) {
            LogUtil.error(logger);
        }

        List<ProductCategory> firstList = list.stream().filter(object -> object.getParentID() == 0).collect(Collectors.toList());
        List<ProductCategory> otherList = list.stream().filter(object -> object.getParentID() != 0).collect(Collectors.toList());

        List<ZTreeNode> nodeList = new ArrayList<>();

        for (ProductCategory object : firstList) {
            nodeList.add(getNode(otherList, object));
        }

        return nodeList;
    }

    private ZTreeNode getNode(List<ProductCategory> otherList, ProductCategory productCategoryPar) {

        ZTreeNode node = this.setValues(productCategoryPar);
        if (otherList.size() != 0) {
            List<ZTreeNode> zTreeNodeList = new ArrayList<>();
            List<ProductCategory> tempList = otherList.stream().filter(object -> object.getParentID() == productCategoryPar.getCategoryID()).collect(Collectors.toList());
            for (ProductCategory object : tempList) {
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

    private ZTreeNode setValues(ProductCategory productCategoryPar) {
        ZTreeNode node = new ZTreeNode();
        node.setId(productCategoryPar.getCategoryID());
        node.setName(productCategoryPar.getName());
        node.setpId(productCategoryPar.getParentID());
        return node;
    }
}
