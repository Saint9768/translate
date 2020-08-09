package com.wind.demo.dao;

import com.wind.demo.model.TranslateData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

/**
 * 翻译数据交互层
 * @author Saint
 * @createTime 2020-05-12 10:03
 */
public interface TranslateDataMapper extends JpaRepository<TranslateData, Long>, JpaSpecificationExecutor<TranslateData> {

    /**
     * 根据翻译日期查询翻译的记录
     * @param date
     * @return
     */
    List<TranslateData> findAllByDateEquals(Date date);
}
