package com.example.scheduleapp.global.interceptor;

import com.example.scheduleapp.global.annotation.LoginRequired;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.UnauthorizedAccessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
/*todo: 이 부분 다른 곳들도 수정하기 */
import static com.example.scheduleapp.global.interceptor.SessionConst.LOGIN_MEMBER;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ) throws Exception {

        boolean hasAnnotation  = checkAnnotation(handler, LoginRequired.class);

        if (hasAnnotation) {
            log.info("Login required 어노테이션 확인");
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(LOGIN_MEMBER) == null) {
                throw new UnauthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
            }
            return true;
        }

        return true;

    }

    /*예외 처리 부분*/
    private boolean checkAnnotation(Object handler, Class<LoginRequired> loginRequiredClass) {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        //LoginRequired anntotation이 있는 경우
        if (null != handlerMethod.getMethodAnnotation(loginRequiredClass) || null != handlerMethod.getBeanType().getAnnotation(loginRequiredClass)) {
            return true;
        }

        //annotation이 없는 경우
        return false;
    }
}
