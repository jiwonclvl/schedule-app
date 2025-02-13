package com.example.scheduleapp.global.filter;

import com.example.scheduleapp.global.dto.ErrorResponseDto;
import com.example.scheduleapp.global.exception.ErrorCode;
import com.example.scheduleapp.global.exception.custom.UnauthorizedAccessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;



@Slf4j
public class LoginCheckFilter implements Filter {

    private final ObjectMapper objectMapper = new ObjectMapper();

    // WHITELIST URI는 요청 로직에서 제외 (로그인, 로그아웃, 회원가입)
    private static final String[] WHITELIST = {
            "/",
            "/login",
            "/logout",
            "/members/signup"
    };

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain filterChain
    ) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String requestURI = httpRequest.getRequestURI();
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        // GET 요청에 대해서만 화이트리스트 적용
        if (httpRequest.getMethod().equals("GET")) {
            filterChain.doFilter(request, response);
            return;
        }

        log.info("로그인 필터 로직 실행");

        // 요청 온 uri가 WHITELIST에 포함되어 있지 않다면 필터 적용
        try {
            if(!isWhiteList(requestURI)) {
                //세션이 없다면 null 반환
                //로그인 하지 않고 다른 기능을 수행하려고 할 때
                HttpSession session = httpRequest.getSession(false);

                if(session == null || session.getAttribute((SessionConst.LOGIN_MEMBER)) == null) {
                    throw new UnauthorizedAccessException(ErrorCode.UNAUTHORIZED_ACCESS);
                }
            }

            log.info("로그인 확인 성공");
        } catch (UnauthorizedAccessException e) {

            httpResponse.setContentType("application/json;charset=utf-8");
            ErrorResponseDto errorResponseDto = new ErrorResponseDto(e.getErrorCode().getErrorCode().value(), e.getMessage());

            String jsonResponse = objectMapper.writeValueAsString(errorResponseDto);

            httpResponse.getWriter().write(jsonResponse);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private boolean isWhiteList(String requestURI) {
        return PatternMatchUtils.simpleMatch(WHITELIST, requestURI);
    }
}
