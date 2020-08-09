package com.wind.demo.model.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;

/**
 *
 * @author Saint
 * @createTime 2020-05-21 14:59
 */
@Data
@ToString
@NoArgsConstructor
public class TranslateRequest {

    /**
     * 翻译引擎
     */
    private String translateEngine;

    /**
     * 原语言
     */
    private String originalLanguage;

    /**
     * 目标语言
     */
    private  String targetLanguage;

    /** 原文本 */
    private String originalText;

    /** 翻译后的内容 */
    private String targetText;

    /** 翻译得分 */
    private String score;

}
