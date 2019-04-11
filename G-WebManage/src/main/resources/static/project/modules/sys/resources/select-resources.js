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

    var currentID = paramMap.get("id");
    $.ajax({
        type: "get",
        async: false,
        url: "../../ztree/getResourcesZTree?id=" + currentID + "&type=" + paramMap.get("type"),
        dataType: "json",
        success: function (data) {
            zTreeObj = $.fn.zTree.init($("#targetZTree"), setting, data);
        }
    });

    var parentID = paramMap.get("parentID");
    if((typeof parentID != "undefined") && (typeof parentID.valueOf() == "string") && (parentID.toString().length > 0) && (parentID.toString() != "0")){
        var node = zTreeObj.getNodeByParam("id", parentID);          //根据ID找到该节点
        zTreeObj.selectNode(node);                                   //让该被节点选中
        setValues(paramMap);                                                //回写父窗口的值
    }
}

//传值回父窗口
function doSubmit(index, paramMap) {

    var getSelectedNodes = zTreeObj.getSelectedNodes()[0];
    console.log(getSelectedNodes);
/*    if((typeof getSelectedNodes != "undefined") && (typeof getSelectedNodes.valueOf() == "number") && (getSelectedNodes.toString().length > 0)){*/
    if((typeof getSelectedNodes != "undefined")) {
        setValues(paramMap);     //回写父窗口的值
    }
    top.layer.close(index);
}

function setValues(paramMap) {

    {
        var iframeIndex = paramMap.get("iframeIndex");
        if(!((typeof iframeIndex != "undefined") && (typeof iframeIndex.valueOf() == "number") && (iframeIndex.toString().length > 0))){
            iframeIndex = 0;
        }

        top.parent.$("iframe")[iframeIndex].contentWindow.$("#parentID").val(zTreeObj.getSelectedNodes()[0].id);
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#parentName").val(zTreeObj.getSelectedNodes()[0].name);
    }
}
