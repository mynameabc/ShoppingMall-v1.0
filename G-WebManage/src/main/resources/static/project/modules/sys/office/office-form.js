var $table;          //父页面table表格对象
var $topIndex;      //openDialog的dialog index

function Init(index, paramMap) {
    var handlerType = paramMap.get("handlerType");
    $("#handlerType").val(handlerType);

    switch(handlerType)
    {
        case "add":
            break;
        case "addChild":
            $("#officeID").val(paramMap.get("parentID"));
            $("#officeName").val(paramMap.get("parentName"));
            break;
        case "edit":
            //查询
            $.getJSON("get?id=" + paramMap.get("id"),function(data) {
                if (data.success) {
                    //如果有父级就查询父级的Name
                    if((typeof data.data.parentID != "undefined") && (typeof data.data.parentID.valueOf() == "number") && (data.data.parentID.toString().length > 0)){
                        $.getJSON("get?id=" + data.data.parentID,function(result){
                            if (result.success) {
                                $("#officeName").val(result.data.name);
                            }
                        });
                    }

                    //如果有地区ID就查询其Name
                    if((typeof data.data.areaID != "undefined") && (typeof data.data.areaID.valueOf() == "string") && (data.data.areaID.toString().length > 0)){
                        $("#areaName").val(getNodeName(data.data.areaID));
                    }

                    $("#id").val(data.data.officeID);
                    $("#areaID").val(data.data.areaID);
                    $("#officeID").val(data.data.parentID);
                    $("#name").val(data.data.name);
                    $("#sort").val(data.data.sort);
                    $("#type").find("option[value = '" + data.data.type + "']").attr("selected","selected");
                    $("#zipCode").val(data.data.zipCode);
                    $("#address").val(data.data.address);
                    $("#master").val(data.data.master);
                    $("#phone").val(data.data.phone);
                    $("#fax").val(data.data.fax);
                    $("#email").val(data.data.email);
                    $("#useable").bootstrapSwitch('state', data.data.useable);
                    $("#remarks").val(data.data.remarks);
                }
            });
            break;
        default:
            break;
    }
}

function doSubmit(index, paramMap) {
    $topIndex = index;
    $table = paramMap.get("table");
    $('#inputForm').submit();
}

//验证初始化
$('#inputForm').validator({
    stopOnError:true,
    focusCleanup: true,
    msgMaker: false, //不要自动生成消息
    timely: 2,
    fields: {
        "name": {
            rule: "required;",
            msg:{
                required: "机构名称不能为空!",
            }
        },
        "type": {
            rule: "typeSelect;",
            msg: {
                required: "请选择机构类型!"
            }
        },
        "sort": {
            rule: "required;float",
            msg: {
                required: "排序不能为空!"
            }
        }
    },
    rules:{
        float:function(element, params) {
            return /^^\d+\.\d\d$/.test(element.value) || "范例 如:3.12"
        },
        typeSelect:function(element, params) {
            if (element.value == -1) {
                return "请选择机构类型";
            }
        }
    },
    //验证成功
    valid: function(form) {
        var target = this;
        target.holdSubmit();        //ajax提交表单之前, 先禁用submit
        $.post("save",$("form").serialize(),function(data){
            data = JSON.parse(data);
            if (data.success) {
                $table.bootstrapTable('refresh');
                top.layer.msg(data.message, {icon: 1,time:1100});
            } else {
                top.layer.msg(data.message, {icon: 2,time:1100});
            }
            target.holdSubmit(false);   //可以再次提交
            top.layer.close($topIndex);
        });
    },
    invalid: function(form, errors) {
        var html = '';
        $.map(errors, function(msg){
            html += '<h3 align="center">×&nbsp;'+ msg +'</h3>'
        });
        $('#msg_holder').html(html);
    }
});

var width = "300px";
var height = "450px";

//打开上级机构对话框
function openSelectOfficeDialog() {
    var paramMap = new Map();
    paramMap.put("iframeIndex", 1);
    paramMap.put("id", $("#id").val());
    paramMap.put("officeID", $("#officeID").val());
    wd.openDialog('选择上级机构',width,height,'common/select-office',paramMap);
}

//打开地区对话框
function openSelectAreaDialog() {
    var paramMap = new Map();
    paramMap.put("iframeIndex", 1);
    paramMap.put("areaID", $("#areaID").val());
    wd.openDialog('选择区域',width,height,'common/select-area',paramMap);
}

$(function () {

    /*----------------------------------------------------------------------------------------------------------------*/

    //删除上级机构
    $("#officeDeleteButton").click(function(){
        $("#officeID").val("");
        $("#officeName").val("");
    });

    //打开上级机构对话框
    $("#officeSelectButton").click(function(){
        openSelectOfficeDialog();
    });

    //打开上级机构对话框
    $("#officeName").click(function(){
        openSelectOfficeDialog();
    });

    /*----------------------------------------------------------------------------------------------------------------*/

    //删除区域
    $("#areaDeleteButton").click(function(){
        $("#areaID").val("");
        $("#areaName").val("");
    });

    //打开区域对话框
    $("#areaSelectButton").click(function(){
        openSelectAreaDialog();
    });

    //打开区域对话框
    $("#areaName").click(function(){
        openSelectAreaDialog();
    });

    /*----------------------------------------------------------------------------------------------------------------*/

    $("#type").selectInit({
        url:'../dictionary/getDictionaryListForType',
        data:{type:"sys_office_type"},
        IDFieldName:'value',               //下拉框选项的id字段名
        ValueFieldName:'lable'            //下拉框选项的value字段名
    });

    //是否可用
    $("#useable").bootstrapSwitch({
        onText:"启用",
        offText:"停用",
        onColor:"success",
        offColor:"danger",
        size:"small"
    });
});
