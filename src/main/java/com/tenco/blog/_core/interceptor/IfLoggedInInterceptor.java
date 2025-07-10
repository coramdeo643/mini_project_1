package com.tenco.blog._core.interceptor;

import com.tenco.blog.company.Company;
import com.tenco.blog.user.User;
import com.tenco.blog.utils.Define;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class IfLoggedInInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User sessionUser = (User) session.getAttribute(Define.SESSIONUSER_USER);
        Company sessionCompany = (Company) session.getAttribute(Define.SESSIONUSER_COMPANY);

        if (sessionUser != null || sessionCompany != null) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
