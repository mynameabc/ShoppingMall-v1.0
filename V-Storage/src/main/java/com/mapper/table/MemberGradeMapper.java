package com.mapper.table;

import com.MyMapper;
import model.entity.MemberGrade;
import model.vo.TreeViewNode;
import model.vo.ZTreeNode;

import java.util.List;

public interface MemberGradeMapper extends MyMapper<MemberGrade> {

    List<ZTreeNode> getMemberGrade_for_ZTree();

    List<TreeViewNode> getMemberGrade_for_TreeView();
}