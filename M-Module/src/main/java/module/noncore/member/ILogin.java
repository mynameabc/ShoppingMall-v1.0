package module.noncore.member;

import communal.Result;
import model.dto.LoginDTO;

public interface ILogin {

    /**
     * 登陆
     * @param loginDTO
     * @return
     */
    Result login(LoginDTO loginDTO);
}

