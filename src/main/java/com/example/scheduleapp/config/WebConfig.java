package com.example.scheduleapp.config;

import com.example.scheduleapp.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean loginFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();

        //필터 적용
        filterRegistrationBean.setFilter(new LoginFilter());

        //필터 순서 적용
        filterRegistrationBean.setOrder(1);

        //전체 URL에 필터 적용 --> 이후 핉터 부분에서 처리할 URL 구별
        filterRegistrationBean.addUrlPatterns("/*");

        return filterRegistrationBean;
    }
}
