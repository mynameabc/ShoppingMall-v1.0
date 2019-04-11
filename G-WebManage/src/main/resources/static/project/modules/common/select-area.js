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

    var areaID = paramMap.get("areaID");
    zTreeObj = $.fn.zTree.init($("#targetZTree"), setting, areaData);      //Ztree初始化
    zTreeObj.expandAll(false);

    if((typeof areaID != "undefined") && (typeof areaID.valueOf() == "string") && (areaID.toString().length > 0)){
        var node = zTreeObj.getNodeByParam("id", areaID);         //根据ID找到该节点
        zTreeObj.selectNode(node);                                //让该被节点选中
        setValues(paramMap);                                              //回写父窗口的值
    }
}

//点击确定按钮, 传值回父窗口
function doSubmit(index, paramMap) {
    var node = zTreeObj.getSelectedNodes()[0];
    if (node.isParent) {
        layer.alert("请选择区/县!");
    } else {
        var checkedID = zTreeObj.getSelectedNodes()[0].id;
        if((typeof checkedID != "undefined") && (typeof checkedID.valueOf() == "number") && (checkedID.toString().length > 0)){
            setValues(paramMap);     //回写父窗口的值
        }
        top.layer.close(index);
    }
}

function setValues(paramMap) {

    //回写父窗口的值
    {
        var iframeIndex = paramMap.get("iframeIndex");
        if(!((typeof iframeIndex != "undefined") && (typeof iframeIndex.valueOf() == "number") && (iframeIndex.toString().length > 0))){
            iframeIndex = 0;
        }

        top.parent.$("iframe")[iframeIndex].contentWindow.$("#areaID").val(zTreeObj.getSelectedNodes()[0].id);
        top.parent.$("iframe")[iframeIndex].contentWindow.$("#areaName").val(getFilePath(zTreeObj.getSelectedNodes()[0]));
    }
}

//拼接父节点name值, 返回格式:福建省/福州市/仓山区
function getFilePath(treeObj){
    if(treeObj == null)return;
    var filename = treeObj.name;
    var pNode = treeObj.getParentNode();
    if (pNode != null) {
        filename = getFilePath(pNode) + "/" + filename;
    }
    return filename;
}
