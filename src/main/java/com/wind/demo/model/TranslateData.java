package com.wind.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 翻译数据模型
 * @author Saint
 * @createTime 2020-05-12 10:05
 */
@Data
@ToString
@NoArgsConstructor
@Entity
@Table(name = "translate_data")
public class TranslateData{

    //private static final long serialVersionUID = 7185634919254071238L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    private String targetLanguage;

    /**
     * 原文本
     */
    private String originalText;

    /**
     * 目标文本
     */
    private String targetText;

    /**
     * 翻译效果得分
     */
    private Integer score;

    /**
     * 翻译日期
     */
    private Date date;

}
