package com.wind.demo.model.response;

import com.wind.demo.model.response.base.ResponseResult;
import com.wind.demo.model.response.base.ResultCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 翻译结果类
 * @author Saint
 * @createTime 2020-05-21 15:21
 */
@Data
@ToString
@NoArgsConstructor
public class TranslateResult extends ResponseResult {

    /**
     * 翻译结果
     */
    String translateResult;

    /**
     * 构造函数
     * @param resultCode 操作代码
     * @param translateResult 翻译结果
     */
    public TranslateResult(ResultCode resultCode, String translateResult){
        super(resultCode);
        this.translateResult = translateResult;
    }
}
