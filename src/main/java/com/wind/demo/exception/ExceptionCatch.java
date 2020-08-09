package com.wind.demo.exception;

import com.google.common.collect.ImmutableMap;
import com.wind.demo.model.response.base.CommonCode;
import com.wind.demo.model.response.base.ResponseResult;
import com.wind.demo.model.response.base.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常捕获类
 * 控制器增强
 * @author Saint
 * @createTime 2020-03-20 16:38
 */
@ControllerAdvice
public class ExceptionCatch {

    /** 打印日志 */
    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionCatch.class);

    /** 定义一个Map 线程安全，并且只读,用于配置异常类型所对应的错误代码 */
    private static ImmutableMap<Class<? extends Throwable>, ResultCode> EXCEPTIONS;

    /** 定义Map的builder对象，用与构建ImmutableMap */
    protected static ImmutableMap.Builder<Class<? extends Throwable>,ResultCode> builder = ImmutableMap.builder();

    /**
     * 捕获CustomException类异常
     * @ResponseBody 将错误信息转成JSON格式
     * @param customException
     * @return
     */
    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseResult customException(CustomException customException){
        customException.printStackTrace();
        //记录日志
        LOGGER.error("catch Exception: {" + customException.getMessage() + "}");
        ResultCode resultCode = customException.getResultCode();
        return new ResponseResult(resultCode);
    }

    /**
     * 捕获Exception类异常
     * @param exception
     * @return
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseResult exception(Exception exception){
        exception.printStackTrace();
        //记录日志
        LOGGER.error("catch Exception: {" + exception.getMessage() + "}");
        if(EXCEPTIONS == null){
            //EXCEPTIONS构建成功
            EXCEPTIONS = builder.build();
        }
        //从EXCEPTIONS中查找异常类型对应的错误代码，如果能找到就将错误代码返回给用户。
        ResultCode resultCode = EXCEPTIONS.get(exception.getClass());
        if(resultCode != null){
            return new ResponseResult(resultCode);
        }else {
            //返回通用异常信息9999
            return new ResponseResult(CommonCode.SERVER_ERROR);
        }
    }

    static{
        //定义输入参数为空对应的异常类型
        builder.put(HttpMessageNotReadableException.class,CommonCode.INVALID_PARAM);
    }
}
