package com.psib.auth;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFailedHandler implements AuthenticationFailureHandler {

    private static final Logger LOG = Logger.getLogger(LoginFailedHandler.class);

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException e) throws IOException, ServletException {
        LOG.info("[onAuthenticationFailure] Start");
        LOG.info("[onAuthenticationFailure] End");
        response.sendRedirect("login");
    }
}
