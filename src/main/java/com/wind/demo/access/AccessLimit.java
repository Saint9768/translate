package com.wind.demo.access;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 运行时在方法上生效
 * @author Saint
 * @createTime 2020-05-21 19:11
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
@Documented
@Component
public @interface AccessLimit {

    /**
     * 表示多少秒
     * @return
     */
    int seconds();

    /**
     * 表示多少次
     * @return
     */
    int maxCount();

    /**
     * 是否需要登录
     * @return
     */
    boolean needLogin() default true;
}
