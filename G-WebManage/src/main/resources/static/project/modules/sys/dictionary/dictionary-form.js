var $table;          //父页面table表格对象
var $topIndex;      //openDialog的dialog index

function Init(index, paramMap) {
    var handlerType = paramMap.get("handlerType");
    $("#handlerType").val(handlerType);

    switch(handlerType)
    {
        case "add":
            break;
        case "edit":
            //发起请求
            $.getJSON("get?id=" + paramMap.get("id"),function(data) {
                if (data.success) {
                    $("#id").val(data.data.id);
                    $("#lable").val(data.data.lable);
                    $("#value").val(data.data.value);
                    $("#type").val(data.data.type);
                    $("#sort").val(data.data.sort);
                    $("#description").val(data.data.description);
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
        "lable": {
            rule: "required;",
            tip: "",
            ok: "",
            msg:{
                required: "Lable不能为空!",
            }
        },
        "type": {
            rule: "required;",
            tip: "",
            ok: "",
            msg: {
                required: "类型不能为空!"
            }
        },
        "sort": {
            rule: "required;float",
            tip: "",
            ok: "",
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
        $.post("save",$("form").serialize(),function(data){
            data = JSON.parse(data);
            if (data.success) {
                $table.bootstrapTable('refreshOptions',{pageNumber:1});
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
