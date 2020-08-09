package com.wind.demo.model.response.base;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 返回结果类
 * @author Saint
 * @createTime 2020-05-21 15:04
 */
@Data
@ToString
@NoArgsConstructor
public class ResponseResult {

    /**
     * 操作是否成功
     */
    boolean success = true;

    /**
     * 操作代码
     */
    int code = 10000;

    /**
     * 提示信息
     */
    String message;

    /**
     * 构造函数
     * @param resultCode 响应码
     */
    public ResponseResult(ResultCode resultCode) {
        this.success = resultCode.success();
        this.code = resultCode.code();
        this.message = resultCode.message();
    }

    /**
     * 操作成功
     * @return
     */
    public static ResponseResult SUCCESS(){
        return new ResponseResult(CommonCode.SUCCESS);
    }

    /**
     * 操作失败
     */
    public static ResponseResult FAIL(){
        return new ResponseResult(CommonCode.FAIL);
    }
}
