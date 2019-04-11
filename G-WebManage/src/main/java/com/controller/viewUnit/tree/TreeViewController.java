package com.controller.viewUnit.tree;

import auxiliary.subassembly.tree.treeview.ITreeView;
import com.alibaba.fastjson.JSON;
import model.vo.TreeViewNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "treeView")
public class TreeViewController {

    @Autowired
    @Qualifier("memberGradeTreeViewImpl")
    private ITreeView memberGradeTreeViewImpl;

    @Autowired
    @Qualifier("officeTreeViewPorxyImpl")
    private ITreeView officeTreeViewPorxyImpl;

    /**
     * 会员等级
     * @return
     */
    @GetMapping(value="getMemberGradeTreeView")
    @ResponseBody
    public String getMemberGradeTreeView() {
        List<TreeViewNode> zTreeNodeList = memberGradeTreeViewImpl.getTree();
        return JSON.toJSONString(zTreeNodeList);
    }

    /**
     * 员工机构
     * @return
     */
    @GetMapping(value="getOfficeTreeView")
    @ResponseBody
    public String getOfficeTreeView() {
        List<TreeViewNode> treeViewNodeList = officeTreeViewPorxyImpl.getTree();
        return JSON.toJSONString(treeViewNodeList);
    }
}
