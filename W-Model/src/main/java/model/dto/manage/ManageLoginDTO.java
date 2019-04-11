package model.dto.manage;

import communal.Result;
import communal.util.RegexUtil;
import org.apache.commons.lang3.StringUtils;

public class ManageLoginDTO implements java.io.Serializable {

    private String account;
    private String pws;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPws() {
        return pws;
    }

    public void setPws(String pws) {
        this.pws = pws;
    }

    public Result checkDTO() {

        if (StringUtils.isEmpty(this.account) || StringUtils.isEmpty(this.account.trim())) {return new Result(false, "用户名不可为空!");}
        if (!RegexUtil.username(this.account)) {return new Result(false, "用户名必须是:4-16位字母, 数字, 下划线或中文!");}

        /*----------------*/

        if (StringUtils.isEmpty(this.pws) || StringUtils.isEmpty(this.pws.trim())) {return new Result(false, "密码不可为空!");}

        return new Result(true, "");
    }
}
