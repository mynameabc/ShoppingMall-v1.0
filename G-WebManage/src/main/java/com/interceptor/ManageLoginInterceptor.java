package com.interceptor;

import auxiliary.loginObject.GuaranteeAuthorization;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import sys.GlobalConstants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 管理后端登陆拦截器
 */
public class ManageLoginInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ManageLoginInterceptor.class);

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object var3, ModelAndView var4) throws Exception{}

    @Override
    public void afterCompletion(HttpServletRequest var1, HttpServletResponse var2, Object var3, Exception var4) throws Exception{}

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object var3) throws Exception {

        logger.info("---");
        GuaranteeAuthorization guaranteeAuthorization =
                (GuaranteeAuthorization) request.getSession().getAttribute(GlobalConstants.Manage_SessionObject);
        if (null != guaranteeAuthorization) {
            return true;
        }

        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/login";

        PrintWriter out = response.getWriter();

        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");
        response.setDateHeader("Expires", 0);
        response.setHeader("Pragma", "no-cache");

        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + url + "','_parent')"); //作为父窗口打开
        out.println("</script>");
        out.println("</html>");

/*        String requestWith = request.getHeader("x-requested-with" );
        if (requestWith != null && requestWith.equalsIgnoreCase("XMLHttpRequest" )){
            return (null == guaranteeAuthorization) ? (false) : (true);
        } else {
            response.sendRedirect(request.getContextPath() + "/login.html");
        }*/

        return false;
    }
}
