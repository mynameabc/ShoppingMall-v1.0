package auxiliary.shiro;

import java.util.HashSet;
import java.util.Set;
 
import javax.servlet.http.HttpServletRequest;
 
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.web.subject.WebSubject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
/**
* @ClassName:
* @Description: Realm的配置
* @author fuweilian
* @date 2018-5-12 上午11:36:41
 */
public class MyShiroRealm extends AuthorizingRealm {
	//slf4j记录日志，可以不使用
	private Logger logger = LoggerFactory.getLogger(MyShiroRealm.class);
 
	/**
	 * 设置授权信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		logger.info("开始授权(doGetAuthorizationInfo)");
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		HttpServletRequest request = (HttpServletRequest) ((WebSubject) SecurityUtils.getSubject()).getServletRequest();//这个可以用来获取在登录的时候提交的其他额外的参数信息
		String username = (String) principals.getPrimaryPrincipal();//这里是写的demo，后面在实际项目中药通过这个登录的账号去获取用户的角色和权限，这里直接是写死的
		//受理权限
		//角色
		Set<String> roles = new HashSet<String>();
		roles.add("role1");
		authorizationInfo.setRoles(roles);
		//权限
		Set<String> permissions = new HashSet<String>();
		permissions.add("user:list");
		//permissions.add("user:add");
		authorizationInfo.setStringPermissions(permissions);
		return authorizationInfo;
	}
 
	/**
	 * 设置认证信息
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authenticationToken) throws AuthenticationException {
		logger.info("开始认证(doGetAuthenticationInfo)");
		//UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
		HttpServletRequest request = (HttpServletRequest) ((WebSubject) SecurityUtils
				.getSubject()).getServletRequest();
		UsernamePasswordToken token = new UsernamePasswordToken (request.getParameter("userName"),request.getParameter("password"));
		//获取用户输入的账号
		String userName = (String)token.getPrincipal();
		//通过userName去数据库中匹配用户信息，通过查询用户的情况做下面的处理
		//这里暂时就直接写死,根据登录用户账号的情况做处理
		logger.info("账号："+userName);
		if("passwordError".equals(userName)){//密码错误
			throw new IncorrectCredentialsException(); 
		}else if("lockAccount".equals(userName)){// 用户锁定
			throw new LockedAccountException(); 
		}else{
			SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
		            userName, //用户名
		            "123456", //密码，写死
		            ByteSource.Util.bytes(userName+"salt"),//salt=username+salt
		            getName()  //realm name
		    );
		    return authenticationInfo;
		}
	}
	
}
