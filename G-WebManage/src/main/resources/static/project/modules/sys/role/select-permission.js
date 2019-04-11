/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
var roleID;
var zTreeObj;
var setting = {

    check:{
        enable:true
    },
    data: {
        simpleData: {
            enable:true
        }
    },
    callback: {

    }
};
/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
function Init(index, paramMap) {

/*    var zNodes =[
        { id:1, pid:0, name:"展开、折叠 自定义图标不同",open:true},
        { id:11, pid:1, name:"叶子节点1", icon:"../../static/plugin/zTree/v3/css/bootstrapStyle/img/3.png"},
        { id:2, pid:0, name:"展开、折叠 自定义图标相同", open:true},
        { id:21, pid:2, name:"叶子节点1"},
        { id:22, pid:2, name:"叶子节点2"},
        { id:23, pid:2, name:"叶子节点3"}
    ];*/


    roleID = paramMap.get("roleID");
    $.ajax({
        type: "get",
        async: false,
        url: "../../ztree/getResourcesZTree.html?roleID=" + roleID,
        dataType: "json",
        success: function (data) {
            zTreeObj = $.fn.zTree.init($("#targetZTree"), setting, data);           //Ztree初始化
            zTreeObj.expandAll(true);                                               //全部展开
        }
    });
}

//提交权限
function doSubmit(index, paramMap) {

    var node = zTreeObj.getCheckedNodes(true);
    if (node.length <= 0) {
        top.layer.alert("请选择您需要的权限!");
    } else {
        var IDS = "";
        for (var idsIndex = 0; idsIndex < node.length; idsIndex++) {
            IDS += node[idsIndex].id + ",";
        }
        $.post(
            "saveRoleResource.html",
            {
                IDS:IDS.substr(0,IDS.length - 1),
                roleID:roleID
            },
            function(data) {
                data = JSON.parse(data);
                if (data.success) {
                    top.layer.msg(data.message, {icon: 1,time:1100});
                } else {
                    top.layer.msg(data.message, {icon: 2,time:1100});
                }
                top.layer.close(index);
            }
        );
    }
}

$(function () {

    $(":checkbox").iCheck({
        checkboxClass: 'icheckbox_square-blue'
    });
});

