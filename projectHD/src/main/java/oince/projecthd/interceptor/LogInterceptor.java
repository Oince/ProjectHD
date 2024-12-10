package oince.projecthd.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Slf4j
public class LogInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String logId = UUID.randomUUID().toString();
        String requestURI = request.getRequestURI();
        String method = request.getMethod();

        request.setAttribute("logId", logId);

        log.info("{}: REQUEST {} {}", logId, method, requestURI);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String logId = (String)request.getAttribute("logId");
        String requestURI = request.getRequestURI();

        String method = request.getMethod();

        log.info("{}: COMPLETE {} {} {}", logId, method, requestURI, response.getStatus());

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String requestURI = request.getRequestURI();
        String logId = (String)request.getAttribute("logId");
        if (ex != null) {
            log.warn("{}: {} [{}]", logId, requestURI, ex.getMessage());
        }
    }
}
