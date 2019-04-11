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
        url: "../ztree/getProductCategoryZTree?id=" + currentID,
        dataType: "json",
        success: function (data) {
            zTreeObj = $.fn.zTree.init($("#targetZTree"), setting, data);
            zTreeObj.expandAll(true);                                //全部展开
        }
    });

    var parentID = paramMap.get("parentID");
    if((typeof parentID != "undefined") && (typeof parentID.valueOf() == "string") && (parentID.toString().length > 0) && (parentID.toString() != "0")){
        var node = zTreeObj.getNodeByParam("id", parentID);          //根据ID找到该节点
        zTreeObj.selectNode(node);                                   //让该被节点选中
        setValues(paramMap);                                         //回写父窗口的值
    }
}

//传值回父窗口
function doSubmit(index, paramMap) {

    if((typeof zTreeObj.getSelectedNodes()[0].name != "undefined") && (typeof zTreeObj.getSelectedNodes()[0].name.valueOf() == "string") && (zTreeObj.getSelectedNodes()[0].name.toString().length > 0)){

        setValues(paramMap);     //回写父窗口的值
    }
    top.layer.close(index);
}

function setValues(paramMap) {

    {
        var iframeIndex = paramMap.get("iframeIndex");
        console.log(iframeIndex);
        if(!((typeof iframeIndex != "undefined") && (typeof iframeIndex.valueOf() == "number") && (iframeIndex.toString().length > 0))){
            iframeIndex = 0;
        }

        top.parent.$("iframe")[iframeIndex].contentWindow.$("#parentID").val(zTreeObj.getSelectedNodes()[0].id);
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#parentName").val(zTreeObj.getSelectedNodes()[0].name);
    }
}
