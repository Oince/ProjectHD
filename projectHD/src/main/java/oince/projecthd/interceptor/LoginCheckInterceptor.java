package oince.projecthd.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import oince.projecthd.controller.annotation.LoginCheck;
import oince.projecthd.exception.NotLoginException;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);
        if (loginCheck == null) {
            return true;
        }

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            log.info("{}: unauthenticated user", request.getAttribute("logId"));
            throw new NotLoginException("로그인되지 않은 사용자입니다.");
        }

        return true;
    }
}
