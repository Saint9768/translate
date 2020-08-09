package com.wind.demo;

import com.wind.demo.controller.TranslateDataController;
import com.wind.demo.dao.TranslateDataMapper;
import com.wind.demo.model.TranslateData;
import com.wind.demo.service.TranslateDataSercie;
import com.wind.demo.translate.lang.LANG;
import com.wind.demo.translate.querier.Querier;
import com.wind.demo.translate.trans.AbstractTranslator;
import com.wind.demo.translate.trans.impl.GoogleTranslator;
import com.wind.demo.translate.trans.impl.IcibaTranslator;
import com.wind.demo.translate.trans.impl.TencentTranslator;
import com.wind.demo.util.DateUtil;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

@SpringBootTest(classes = {TranslateApplication.class, TranslateApplicationTests.class})
@RunWith(SpringRunner.class)
class TranslateApplicationTests {

    @Autowired
    private TranslateDataController translateDataController;

    @Autowired
    private TranslateDataMapper translateDataMapper;

    @Autowired
    private TranslateDataSercie translateDataSercie;

    @Test
    void translate() throws ParseException {

        LANG originalLang = null;
        String originalLanguage = "english";


        // translator
        Querier<AbstractTranslator> querierTrans = new Querier<>();
        querierTrans.setParams(LANG.EN, LANG.ZH,"Happiness is a way station between too much and too little.");

        /** 腾讯翻译（测试完成） */
        //querierTrans.attach(new TencentTranslator());
        /** 谷歌翻译（测试完成） */
        querierTrans.attach(new GoogleTranslator());
        /** 金山爱词霸（测试完成） */
        //querierTrans.attach(new IcibaTranslator());
        String resultTrans = querierTrans.execute();
        System.out.println(resultTrans);
    }

    @Test
    void listData() throws ParseException {
        String s2 = "2020-01-12 12:12:12";
        System.out.println(s2.substring(0,10));
        Date date = DateUtil.formateDate(new Date());
        List<TranslateData> list = translateDataSercie.listTranslateData(date);
        for(TranslateData s : list){
            System.out.println(s);
        }
    }

    @Test
    void contextLoads() {

        //匿名内部类,自定义查询条件
        /* 1. 实现Specifications接口(提供感性，查询的对象类型)
           2. 实现toPredicate方法(构造查询条件)
           3. 需要借助方法参数中的两个参数（
               root：获取需要查询的对象属性
               CriteriaBuilder：用来构造查询条件的，内部封装了很多的查询条件(模糊匹配，精确匹配)
                     CriteriaBuilder中的equal方法可以直接对path对象进行比较
                     gt.lt,ge,le,like：得到path对象，根据path指定比较的参数类型，然后再去进行比较
                         指定参数类型：path.as(类型的字节码对象）*/
        Specification<TranslateData> spec = new Specification<TranslateData>() {
            @Override
            public Predicate toPredicate(Root<TranslateData> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                //1. 获取比较的属性
                Path<Object> userName = root.get("userName");
                Path<Object> company = root.get("company");
                //2. 构造查询条件， (比较的属性,比较的属性的取值)
                Predicate predicate = criteriaBuilder.equal(userName, "周鑫");// 进行精确的匹配。
                Predicate predicate1 = criteriaBuilder.like(company.as(String.class), "中国%");//进行精确查询
                Predicate and = criteriaBuilder.and(predicate, predicate1);//以与的方式拼接多个查询条件
                criteriaBuilder.or(predicate, predicate1);//以或的方式拼接多个查询条件

                return and;
            }
        };

        //排序,创建排序对象，需要调用构造方法实例化对象
        // 第一个参数是排序的顺序    第二个参数是排序的属性名
        // 注： 这里由于SpringBoot的版本太新， 不能使用new Sort()方式进行实例化
        Sort sort = Sort.by(Sort.Direction.DESC, "userName");
        List<TranslateData> translateData = translateDataMapper.findAll(spec, sort);

        /**
         * 分页查询
         * Specification：查询条件
         * Pageable：分页参数（查询的页码， 每页查询的条数）
         */
        //在新版的SpringBoot中都使用静态方法进行实例化
        Pageable pageable = PageRequest.of(0, 5,sort);
        Page<TranslateData> page = translateDataMapper.findAll(spec,pageable);
    }

}
