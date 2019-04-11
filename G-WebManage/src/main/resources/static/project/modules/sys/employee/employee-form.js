var $table;          //父页面table表格对象
var $topIndex;      //openDialog的dialog index
var $selectRowMap;

var manPhoto = "../../static/images/man.jpg";
var womanPhoto = "../../static/images/woman.jpg";

function Init(index, paramMap) {
    var handlerType = paramMap.get("handlerType");
    $("#handlerType").val(handlerType);

    switch(handlerType)
    {
        case "add":
            $("#man").iCheck("check");
            $("#photo").attr("src", manPhoto);
            $("#photoValue").val(manPhoto);
            break;
        case "edit":
            //查询
            $.getJSON("get?id=" + paramMap.get("id"),function(data) {
                if (data.success) {

                    //查询员工机构名
                    if((typeof data.data.officeID != "undefined") && (typeof data.data.officeID.valueOf() == "number") && (data.data.officeID.toString().length > 0)){
                        $.getJSON("../office/get?id=" + data.data.officeID,function(result){
                            if (result.success) {
                                $("#officeName").val(result.data.name);
                            }
                        });
                    }

                    $("#id").val(data.data.employeeID);
                    $("#name").val(data.data.name);
                    $("#email").val(data.data.email);
                    $("#mobile").val(data.data.mobile);
                    $("#jobNumber").val(data.data.jobNumber);
                    $("#pws").val(data.data.pws);
                    $("#realName").val(data.data.realName);
                    $("#officeID").val(data.data.officeID);
                    if (data.data.sex) {$("#man").iCheck("check");} else {$("#woman").iCheck("check");}
                    $("#photo").attr("src", data.data.photo);
                    $("#photoValue").val(data.data.photo);
                    $("#loginLock").bootstrapSwitch('state', data.data.loginLock);
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
    $selectRowMap = paramMap.get("selectRowMap");
    $('#inputForm').submit();
}

//验证初始化
$('#inputForm').validator({
    stopOnError:true,
    focusCleanup: true,
    msgMaker: false, //不要自动生成消息
    timely: 2,
    fields: {
        "realName": {
            rule: "required;",
            msg:{
                required: "真实姓名不能为空!",
            }
        },
        "officeID": {
            rule: "required;",
            msg: {
                required: "请选择该员工所属机构!"
            }
        },
        "jobNumber": {
            rule: "required;",
            msg: {
                required: "工号不能为空!"
            }
        },
        "name": {
            rule: "required;",
            msg: {
                required: "登陆名不能为空!"
            }
        },
        "pws": {
            rule: "required;",
            msg: {
                required: "密码不能为空!"
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
                if (null != $selectRowMap) {
                    $selectRowMap.clear();
                }
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

//打开机构对话框
function openSelectOfficeDialog() {
    var paramMap = new Map();
    paramMap.put("type", "0");
    paramMap.put("iframeIndex", 1);
    paramMap.put("officeID", $("#officeID").val());
    wd.openDialog('选择所属机构',width,height,'common/select-office',paramMap);
}

$(function () {

    /*----------------------------------------------------------------------------------------------------------------*/

    //删除所属机构
    $("#officeDeleteButton").click(function(){
        $("#officeID").val("");
        $("#officeName").val("");
    });

    //打开所属机构对话框
    $("#officeSelectButton").click(function(){
        openSelectOfficeDialog();
    });

    //打开所属机构对话框
    $("#officeName").click(function(){
        openSelectOfficeDialog();
    });

    /*----------------------------------------------------------------------------------------------------------------*/

    //删除头像
    $("#areaDeleteButton").click(function(){
        $("#areaID").val("");
        $("#areaName").val("");
    });

    //打开头像对话框
    $("#areaSelectButton").click(function(){
        wd.openDialog('选择头像',width,height,'../../common/select-area',$("#areaID").val());
    });

    //打开头像对话框
    $("#areaName").click(function(){
        wd.openDialog('选择头像',width,height,'../../common/select-area',$("#areaID").val());
    });

    /*----------------------------------------------------------------------------------------------------------------*/

    $("#man").on('ifChecked', function(event){
        $("#photoValue").val(manPhoto);
        $("#photo").attr("src", manPhoto);
    });

    $("#woman").on('ifChecked', function(event){
        $("#photoValue").val(womanPhoto);
        $("#photo").attr("src", womanPhoto);
    });

    //登陆锁
    $("#loginLock").bootstrapSwitch({
        onText:"未锁",
        offText:"已锁",
        onColor:"success",
        offColor:"danger",
        size:"small"
    });

    //性别
    $("input[name='sex']").iCheck({
        radioClass: 'iradio_square-blue'
    });

    $("#1").iCheck({
        checkboxClass: 'icheckbox_square-blue'
    });

    $("#2").iCheck({
        checkboxClass: 'icheckbox_square-blue'
    });

    $("#3").iCheck({
        checkboxClass: 'icheckbox_square-blue'
    });
});
