package com.example.scheduleapp.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

@Slf4j
public class LoginCheckFilter implements Filter {

    // WHITELIST URI는 요청 로직에서 제외 (로그인, 로그아웃, 회원가입)
    private static final String[] WHITELIST = {"/","/login", "/logout", "/signup"};

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        //필터에서 수행할 로직
        //Servlet은 기능이 많이 없음, 따라서 다운 캐스팅 진행 (요청)
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        //실제 요청이 들어온 URI를 변수로 담아준다.
        String requestURI = httpRequest.getRequestURI();

        //필터에서 수행할 로직
        //Servlet은 기능이 많이 없음, 따라서 다운 캐스팅 진행 (응답)
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        log.info("로그인 필터 로직 실행");

        // 요청 온 uri가 WHITELIST에 포함되어 있지 않다면 필터 적용
        if(!isWhiteList(requestURI)) {
            //세션이 없다면 null 반환
            //로그인 하지 않고 다른 기능을 수행하려고 할 때
            HttpSession session = httpRequest.getSession(false);

            if(session == null || session.getAttribute(("id")) == null) {
                //로그인 페이지로 이동
                throw new RuntimeException("로그인 해주세요");
            }
        }

        log.info("로그인 확인 성공");
        filterChain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
    }
}
