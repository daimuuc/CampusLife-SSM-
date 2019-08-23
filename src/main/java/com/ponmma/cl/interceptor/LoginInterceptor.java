package com.ponmma.cl.interceptor;

import com.ponmma.cl.entity.PersonInfo;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 登录验证拦截器
 * @author ponmma
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {
    /**
     * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 从session中取出用户信息来
        Object userObj = request.getSession().getAttribute("personInfo");
        if (userObj != null) {
            // 若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
            PersonInfo user = (PersonInfo) userObj;
            // 做空值判断，确保id不为空
            if (user != null && user.getId() != null && user.getId() > 0)
                // 若通过验证则返回true,拦截器返回true之后，用户接下来的操作得以正常执行
                return true;
        }
        // 若不满足登录验证，则直接跳转到帐号登录页面
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<script>");
        out.println("window.open ('" + request.getContextPath() + "/main/login','_self')");
        out.println("</script>");
        out.println("</html>");
        return false;
    }

}
