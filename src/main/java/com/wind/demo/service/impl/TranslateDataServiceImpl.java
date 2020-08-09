package com.wind.demo.service.impl;

import com.wind.demo.dao.TranslateDataMapper;
import com.wind.demo.model.TranslateData;
import com.wind.demo.model.request.TranslateRequest;
import com.wind.demo.model.response.TranslateResult;
import com.wind.demo.model.response.base.CommonCode;
import com.wind.demo.model.response.base.ResponseResult;
import com.wind.demo.service.TranslateDataSercie;
import com.wind.demo.translate.lang.LANG;
import com.wind.demo.translate.querier.Querier;
import com.wind.demo.translate.trans.AbstractTranslator;
import com.wind.demo.translate.trans.impl.GoogleTranslator;
import com.wind.demo.translate.trans.impl.IcibaTranslator;
import com.wind.demo.translate.trans.impl.TencentTranslator;
import com.wind.demo.util.DateUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * TranslateDataService层实现类
 * @author Saint
 * @createTime 2020-05-13 9:15
 */
@Service
public class TranslateDataServiceImpl implements TranslateDataSercie {

    @Autowired
    TranslateDataMapper translateDataMapper;

    /**
     * 获取某天的全部翻译结果
     * @param date
     * @return
     */
    @Override
    public List<TranslateData> listTranslateData(Date date) {
        return translateDataMapper.findAllByDateEquals(date);
    }

    /**
     * 翻译接口
     * @param request
     * @return
     */
    @Override
    public TranslateResult translate(TranslateRequest request) {

        //1. 构建翻译器
        Querier<AbstractTranslator> querierTrans = new Querier<>();

        //2. 设置翻译所需的参数
        String originalText = request.getOriginalText();
        String originalLanguage = request.getOriginalLanguage();
        String targetLanguage = request.getTargetLanguage();
        //翻译的文本为空
        if(originalText.isEmpty()){
            return new TranslateResult(CommonCode.SUCCESS, "");
        }
        //原语言
        LANG originalLang = LANG.ZH;
        if(originalLanguage.isEmpty()){
            return new TranslateResult(CommonCode.ORIGINAL_LANGUAGE_EMPTY, "");
        }else if("english".equals(originalLanguage)){
            originalLang = LANG.EN;
        }
        //目标语言
        LANG targetLang = LANG.EN;
        if(targetLanguage.isEmpty()){
            return new TranslateResult(CommonCode.TARGET_LANGUAGE_EMPTY, "");
        }else if("chinese".equals(targetLanguage)){
            targetLang = LANG.ZH;
        }

        //设置翻译参数
        querierTrans.setParams(originalLang, targetLang, originalText);

        //3. 翻译引擎选择，默认使用腾讯翻译
        AbstractTranslator translator =new TencentTranslator();
        if("google".equals(request.getTranslateEngine())){
            //谷歌翻译翻译
            translator = new GoogleTranslator();
        }else if("jinsan".equals(request.getTranslateEngine())){
            //金山爱词霸翻译
            translator = new IcibaTranslator();
        }

        //4. 添加翻译引擎，执行翻译操作
        querierTrans.attach(translator);
        String res = querierTrans.execute();
        //将res首末的引号去掉
        res = res.substring(1, res.length() - 1);
        if("".equals(res)){
            //如果翻译不出来，则翻译结果为原文本
            res = request.getOriginalText();
        }
        return new TranslateResult(CommonCode.SUCCESS, res);
    }

    /**
     * 保存翻译结果到数据库
     * @param request
     * @return
     */
    @Override
    public ResponseResult save(TranslateRequest request) {
        TranslateData translateData = new TranslateData();
        //默认翻译引擎
        translateData.setTranslateEngine("tencent");
        BeanUtils.copyProperties(request, translateData);
        //设置翻译日期
        Date date = DateUtil.formateDate(new Date());
        translateData.setDate(date);

        //设置翻译分数
        if(request.getScore().isEmpty()) {
            return new ResponseResult(CommonCode.SCORE_ISEMPTY);
        }
        Integer score = Integer.parseInt(request.getScore());
        translateData.setScore(score);
        translateDataMapper.save(translateData);
        return new ResponseResult(CommonCode.SUCCESS);
    }


}
