package com.example.myhome.security.handler;

import com.example.myhome.security.UserDetailsHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class PcgAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            System.out.println("Authentication session is null");
//            response.setHeader("Set-Cookie", String.format("JSESSIONID=%s;SameSite=None;Secure;HttpOnly;", session.getId()));
            redirectStrategy.sendRedirect(request, response, "/account/login");
            return;
        }
        System.out.println("Authentication Success : "+session.getId());
        session.setAttribute("user", UserDetailsHelper.getAuthenticatedUser());

//        response.setHeader("Set-Cookie", String.format("JSESSIONID=%s;SameSite=None;Secure;HttpOnly;", session.getId()));
        redirectStrategy.sendRedirect(request, response, "/");
    }

}
