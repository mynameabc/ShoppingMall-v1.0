package auxiliary.subassembly.tree.treeview.impl;

import auxiliary.subassembly.tree.treeview.ITreeView;
import com.mapper.table.MemberMapper;
import com.mapper.table.MemberGradeMapper;
import model.vo.TreeViewNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("memberGradeTreeViewImpl")
public class MemberGradeTreeViewImpl implements ITreeView {

    @Autowired
    private MemberGradeMapper memberGradeMapper;

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 会员等级树
     * @return
     */
    public List<TreeViewNode> getTree() {

        TreeViewNode treeViewNode = new TreeViewNode();
        treeViewNode.setId(0);
        treeViewNode.setText("全部 (" + memberMapper.countAll() + ")");

        treeViewNode.setNodes(memberGradeMapper.getMemberGrade_for_TreeView());

        List<TreeViewNode> treeNodeList = new ArrayList<>();
        treeNodeList.add(treeViewNode);

        return treeNodeList;
    }
}
