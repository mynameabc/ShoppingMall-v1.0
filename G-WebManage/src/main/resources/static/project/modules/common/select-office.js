/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
var zTreeObj;
var setting = {
    data: {
        simpleData: {
            enable:true
        }
    },callback: {}
};
/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
function Init(index, paramMap) {

    var url = "../ztree/getOfficeZTree";
    var id = paramMap.get("id");
    if((typeof id != "undefined") && (typeof id.valueOf() == "string") && (id.toString().length > 0) && (id.toString() != "0")){
        url = url + "?id=" + id;
    }

    $.ajax({
        type: "get",
        async: false,
        url: url,
        dataType: "json",
        success: function (data) {
            zTreeObj = $.fn.zTree.init($("#targetZTree"), setting, data);           //Ztree初始化
            zTreeObj.expandAll(true);                                               //全部展开
        }
    });

    var officeID = paramMap.get("officeID");
    if((typeof officeID != "undefined") && (typeof officeID.valueOf() == "string") && (officeID.toString().length > 0) && (officeID.toString() != "0")){
        var node = zTreeObj.getNodeByParam("id", officeID);          //根据ID找到该节点
        zTreeObj.selectNode(node);                                   //让该被节点选中
    }
}

//传值回父窗口
function doSubmit(index, paramMap) {

    var type = paramMap.get("type");    //是否允许点击父节点的标识
    if((typeof type != "undefined") && (typeof type.valueOf() == "string") && (type.toString().length > 0)){
        var node = zTreeObj.getSelectedNodes()[0];
        if (node.isParent) {
            top.layer.alert("不允许选择父节点!");
        } else {
            action(index, paramMap);
        }
    } else {
        action(index, paramMap);
    }
}

function action(index, paramMap) {
    var checkedID = zTreeObj.getSelectedNodes()[0].id;
    if((typeof checkedID != "undefined") && (typeof checkedID.valueOf() == "number") && (checkedID.toString().length > 0)){

        //回写父窗口的值
        {
            var iframeIndex = paramMap.get("iframeIndex");
            if(!((typeof iframeIndex != "undefined") && (typeof iframeIndex.valueOf() == "number") && (iframeIndex.toString().length > 0))){
                iframeIndex = 0;
            }

            top.parent.$("iframe")[iframeIndex].contentWindow.$("#officeID").val(zTreeObj.getSelectedNodes()[0].id);
            top.parent.$("iframe")[iframeIndex].contentWindow.$("#officeName").val(zTreeObj.getSelectedNodes()[0].name);
        }
    }
    top.layer.close(index);
}
