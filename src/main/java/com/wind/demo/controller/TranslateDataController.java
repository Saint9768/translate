package com.wind.demo.controller;

import com.wind.demo.access.AccessLimit;
import com.wind.demo.model.TranslateData;
import com.wind.demo.model.request.TranslateRequest;
import com.wind.demo.model.response.TranslateResult;
import com.wind.demo.model.response.base.ResponseResult;
import com.wind.demo.service.TranslateDataSercie;
import com.wind.demo.util.DateUtil;
import com.wind.demo.util.ExportExcel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author Saint
 * @createTime 2020-05-20 10:46
 */
@RestController
@CrossOrigin
@Api(value = "翻译接口", description = "翻译接口")
public class TranslateDataController {

    @Autowired
    TranslateDataSercie translateDataSercie;

    /**
     * 导出某天全部翻译的数据库数据为Excel文件
     * @param response
     */
    //@ApiOperation("导出某天提交的所有翻译结果")
    @GetMapping("/export")
    public void export(HttpServletResponse response){
        TranslateData translateData = new TranslateData();
        Date date = DateUtil.formateDate(new Date());
        //获取全部数据
        List<TranslateData> list = translateDataSercie.listTranslateData(date);
        ExportExcel<TranslateData> exportExcel = new ExportExcel<>();
        //Excel表头
        String[] headers = {"编号","翻译引擎","原语言","目标语言", "原文", "译文", "得分","翻译日期"};
        //Excel文件名
        String fileName = UUID.randomUUID().toString();
        exportExcel.exportExcel(headers, list, fileName, response);
    }

    /**
     * 翻译
     * @RequestBode注解表明用来接收前端json字符串
     * 压测结果，每秒请求数不能超过200
     * @param request
     * @return
     */
    @AccessLimit(seconds = 1, maxCount = 199)
    @ApiOperation("执行翻译操作")
    @PostMapping("/translate")
    public TranslateResult translate(@RequestBody TranslateRequest request){

        return translateDataSercie.translate(request);
    }

    /**
     * 保存翻译结果
     * @param request
     * @return
     */
    @ApiOperation("保存翻译结果")
    @PostMapping("/save")
    public ResponseResult saveTranslateData(@RequestBody TranslateRequest request){
        ResponseResult responseResult = translateDataSercie.save(request);
        return  responseResult;
    }

}
