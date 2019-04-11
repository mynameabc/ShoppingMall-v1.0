package module.noncore.member;

import communal.Result;
import model.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component("loginDecoration")
public class LoginDecoration implements ILogin {

    @Autowired
    @Qualifier("loginImpl")
    private ILogin loginImpl;

    public Result login(LoginDTO loginDTO) {

        Result result = loginImpl.login(loginDTO);
        if (result.isSuccess()) {
            //发送短信
        }
        return result;
    }
}
