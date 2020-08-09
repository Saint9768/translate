package com.wind.demo.model.response.base;

import lombok.ToString;

/**
 * 响应代码枚举类
 * @author Saint
 * @createTime 2020-05-21 15:09
 */
@ToString
public enum CommonCode implements ResultCode{
    /**
     * 操作成功
     */
    SUCCESS(true, 10000, "操作成功！"),
    /**
     * 操作失败
     */
    FAIL(false, 11111, "操作失败！"),
    /** 翻译得分不能为空 */
    SCORE_ISEMPTY(false, 10001, "翻译效果得分不能为空！"),
    /**
     * 翻译源语言为空
     */
    ORIGINAL_LANGUAGE_EMPTY(false, 10002, "翻译源语言不能为空！"),
    /**
     * 翻译目标语言为空
     */
    TARGET_LANGUAGE_EMPTY(false, 10003, "翻译目标语言不能为空！"),
    /**
     * 请求次数过多
     */
    LIMITED_ACCESS(false, 10004, "请休息一会再来!"),
    /** 参数非法 */
    INVALID_PARAM(false,10005,"参数非法！"),
    /** 异常 */
    SERVER_ERROR(false,99999,"抱歉，系统繁忙，请稍后重试！")
    ;

    /**
     * 操作是否成功
     */
    boolean success;

    /**
     * 操作代码
     */
    int code;

    /**
     * 提示信息
     */
    String message;

    /**
     * 构造函数
     * @param success 是否操作成功
     * @param code 操作代码
     * @param message 操作提示信息
     */
    CommonCode(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean success() {
        return success;
    }

    @Override
    public int code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
