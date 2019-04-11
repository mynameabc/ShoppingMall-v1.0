package auxiliary.subassembly.tree.ztree;

import model.vo.ZTreeNode;

import java.util.List;

public interface IZTree {

    List<ZTreeNode> getTree(String  ... parameters);
}
