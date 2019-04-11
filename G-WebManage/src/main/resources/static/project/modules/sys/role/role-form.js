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
            break;
        case "edit":
            //查询
            $.getJSON("get.html?id=" + paramMap.get("id"),function(data) {
                if (data.success) {
                    $("#id").val(data.data.roleID);
                    $("#name").val(data.data.name);
                    $("#sort").val(data.data.sort);
                    $("#createTime").val(data.data.createTime);
                    $("#validFlag").bootstrapSwitch('state', data.data.validFlag);
                    $("#remark").val(data.data.remark);
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
    focusCleanup:true,
    stopOnError:false,
    timely: 2,
    fields: {
        "name": {
            rule: "required;",
            msg:{
                required: "角色名称不能为空!",
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
        }
    },
    //验证成功
    valid: function(form) {
        var target = this;
        target.holdSubmit();        //ajax提交表单之前, 先禁用submit
        $.post("save.html",$("form").serialize(),function(data){
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
    invalid: function(form) {}//验证失败
});

var width = "300px";
var height = "450px";

$(function () {

    /*----------------------------------------------------------------------------------------------------------------*/

    //是否有效
    $("#validFlag").bootstrapSwitch({
        onText:"启用",
        offText:"停用",
        onColor:"success",
        offColor:"danger",
        size:"small"
    });
});
