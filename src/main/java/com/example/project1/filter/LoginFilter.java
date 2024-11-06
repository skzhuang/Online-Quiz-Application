package com.example.project1.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * One way to perform the security check. Not very convenient or secure.
 * We will learn more about how to do such thing in Spring on the next week
 */
@WebFilter("/*")
@Component
public class LoginFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // System.out.println("In LoginFilter"); // for debug
        HttpSession session = request.getSession(false);

        if (session != null && session.getAttribute("user") != null) {
            filterChain.doFilter(request, response);
        } else {
            // redirect back to the login page if user is not logged in
            response.sendRedirect("/login");
        }
    }

    /**
     * Some requests don't require the user to log in.
     * For example, login/register page
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();
        System.out.println("path = " + path);

        // Let me explain this.
        // When a user calls /login, a jsp file name is returned.
        // The user will then send another request for the actual jsp file.
        // Obviously, we shouldn't filter the jsp file request either.
        // The example here is using hard-coded file path, but there are smarter ways to implement this filtering.
        return "/login".equals(path)
                || "/register".equals(path)
                || "/contact".equals(path)
                || "/WEB-INF/jsp/login.jsp".equals(path)
                || "/WEB-INF/jsp/register.jsp".equals(path)
                || "/WEB-INF/jsp/contact.jsp".equals(path)
                || path.endsWith(".css");
    }
}

