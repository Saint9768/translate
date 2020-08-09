package com.wind.demo.model.response.base;

/**
 * 响应代码接口类
 * @author Saint
 * @createTime 2020-05-21 15:07
 */
public interface ResultCode {

    /**
     * 操作是否成功，true表示成功，false表示失败
     * @return
     */
    boolean success();

    /**
     * 操作代码
     * @return
     */
    int code();

    /**
     * 操作信息
     * @return
     */
    String message();
}
