package com.wind.demo.exception;

import com.wind.demo.model.response.base.ResultCode;

/**
 * 异常抛出类
 * @author Saint
 * @createTime 2020-03-20 16:35
 */
public class ExceptionCast {

    public static void cast(ResultCode resultCode){
        throw new CustomException(resultCode);
    }
}
