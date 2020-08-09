package com.wind.demo.service;

import com.wind.demo.model.TranslateData;
import com.wind.demo.model.request.TranslateRequest;
import com.wind.demo.model.response.TranslateResult;
import com.wind.demo.model.response.base.ResponseResult;

import java.util.Date;
import java.util.List;

/**
 * TranslateDataService接口
 * @author Saint
 * @createTime 2020-05-13 9:15
 */
public interface TranslateDataSercie {

    /**
     * 获取某天的全部翻译结果
     * @param date
     * @return
     */
    List<TranslateData> listTranslateData(Date date);

    /**
     * 翻译接口
     * @param request
     * @return
     */
    TranslateResult translate(TranslateRequest request);

    /**
     * 保存翻译结果到数据库
     * @param request
     * @return
     */
    ResponseResult save(TranslateRequest request);
}
