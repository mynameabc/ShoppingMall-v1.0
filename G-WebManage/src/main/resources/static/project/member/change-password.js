var $topIndex;
/**
 * ---------------------------------------------------------------------------------------------------------------------
 */
function Init(index, paramMap) {
    $topIndex = index;
    $("#mobile").html(paramMap.get("mobile"));
    $("#memberID").val(paramMap.get("memberID"));
}

function doSubmit(index, paramMap) {
    $('#changePasswordForm').submit();
    return true;
}

//验证初始化
$('#changePasswordForm').validator({
    focusCleanup: true,
    stopOnError:false,
    //debug: true,
    timely: 2,
    fields: {
        "pws": {
            rule: "required;password;",
            tip: "",
            ok: "",
            msg: {
                required: "密码不能为空!",
                length: "密码最少为6位。"
            }
        },
        "confirm_pws": {
            rule: "required;password;match[pws]",
            tip: "",
            ok: "",
            msg: {
                required: "密码确认不能为空!",
                length: "密码最少为6位。",
                match:"密码确认不一致!"
            }
        }
    },
    //验证成功
    valid: function(form) {
        var target = this;
        target.holdSubmit();        //ajax提交表单之前, 先禁用submit
        $.ajax({
            url: 'updatePassword',
            type: 'POST',
            data: $(form).serialize(),
            success: function(data){
                data = JSON.parse(data);
                if (data.success) {
                    top.layer.msg(data.message, {icon: 1,time:1100});
                    top.layer.close($topIndex);
                } else {
                    top.layer.msg(data.message, {icon: 2,time:1100});
                    top.layer.close($topIndex);
                }
                target.holdSubmit(false);   //可以再次提交
            }
        });
    },
    success:function(form) {
        /*layer.alert("这是success");*/
    },
    submitHandler:function(form) {
        /*layer.alert("这是submitHandler");*/
    },
    invalid: function(form) {}//验证失败
});
