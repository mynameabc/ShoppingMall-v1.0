package module.noncore.member;

import com.mapper.table.MemberMapper;
import communal.Result;
import model.dto.LoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("loginImpl")
public class LoginImpl implements ILogin {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 登陆
     * @param loginDTO
     * @return
     */
    public Result login(LoginDTO loginDTO) {

        return new Result(true, "", memberMapper.selectAll());
    }
}
