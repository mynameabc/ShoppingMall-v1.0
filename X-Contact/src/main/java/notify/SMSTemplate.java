package notify;

/**
 * 短信模板常量类
 */
public class SMSTemplate {

    /**
     * 注册短信模板
     */
    public static final String Register_SMS_Template = "SMS_109445471";

    /**
     * 注册通过审核短信
     */
    public static final String RegisterSUCCESS_SMS_Template = "SMS_113095041";

    /**
     * 子账户注册短信模板
     */
    public static final String AddChild_SMS_Template = "SMS_109390493";

    /**
     * 忘记密码
     */
    public static final String ResetPassword_SMS_Template = "SMS_109485422";

    /**
     * 带赠送充值成功短信(${time}-${money1}-${money2}-${money3})
     */
    public static final String Recharge_SUCCESS_Give_Template = "SMS_116562122";

    /**
     * 不带赠送充值成功短信(${time}-${money1}-${money2})
     */
    public static final String Recharge_SUCCESS_NOGive_Template = "SMS_116592288";
}

