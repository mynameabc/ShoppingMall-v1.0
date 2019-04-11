/*时间格式化*/
function changeDateFormat(cellval) {
    var dateVal = cellval + "";
    if (cellval != null) {
        var date = new Date(parseInt(dateVal.replace("/Date(", "").replace(")/", ""), 10));
        var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
        var currentDate = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();

        var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
        var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
        var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();

        return date.getFullYear() + "-" + month + "-" + currentDate + " " + hours + ":" + minutes + ":" + seconds;
    }
}

/**
 * 传入节点id返回获取完整的地区:福建省/福州市/仓山区
 */
/*------------------------------------------------------------------*/
var parentName = "";
function getNodeName(nodeID) {
    parentName = "";
    getNode(areaData, nodeID);
    var array = parentName.split("/");
    parentName = "";
    for (var index = array.length - 1; index >= 0; index--) {
        parentName = parentName + array[index] + "/";
    }
    parentName = parentName.substr(0,parentName.length - 1);
    parentName = parentName.substr(1,parentName.length - 1);

    return parentName;
}

function getNode(json, nodeID) {

    for (var i = 0; i < json.length; i++) {
        var node = json[i];
        if (node.id == nodeID) {
            parentName = parentName + node.name + "/";
            getNode(areaData, node.pId);
        } else {
            if (node.children) {
                getNode(node.children, nodeID);
            }
        }
    }
}
/*------------------------------------------------------------------*/
