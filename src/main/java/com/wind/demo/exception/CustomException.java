package com.wind.demo.exception;


import com.wind.demo.model.response.base.ResultCode;

/**
 * 自定义异常类型
 * @author Saint
 */
public class CustomException extends RuntimeException {

    /** 错误代码 */
    ResultCode resultCode;

    public CustomException(ResultCode resultCode){
        this.resultCode = resultCode;

    }

    /**
     * 取出错误代码
     * @return
     */
    public ResultCode getResultCode(){
        return resultCode;
    }
}
