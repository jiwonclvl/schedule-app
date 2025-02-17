package com.example.scheduleapp.global.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) /*실행되는 중에 계속 수행되어야 한다. */
@Target(ElementType.METHOD) /*메서드 선언시에 어노테이션 사용*/
public @interface LoginRequired {

}
