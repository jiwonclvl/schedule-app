package com.example.scheduleapp.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("v1.0") //버전
                .title("일정 관리 앱 Develop") //이름
                .description("일정 관리 앱을 개발하는 프로젝트입니다."); //설명
        return new OpenAPI()
                .info(info);
    }
}
