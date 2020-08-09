package com.wind.demo.util;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Iterator;

/**
 * 使用poi导出Excel表
 * @author Saint
 * @createTime 2020-05-20 10:54
 */
public class ExportExcel<T> {
    public void exportExcel(String[] headers, Collection<T> dataset, String fileName, HttpServletResponse response) {
        // 声明一个工作薄
        HSSFWorkbook workbook = new HSSFWorkbook();
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet(fileName);
        //生成一个表格样式(表头样式)
        HSSFCellStyle style = workbook.createCellStyle();
        //设置单元格背景样式颜色
        //蓝色背景
        style.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //设置边框
        style.setBorderBottom(BorderStyle.MEDIUM);
        style.setBorderLeft(BorderStyle.MEDIUM);
        style.setBorderTop(BorderStyle.MEDIUM);
        style.setBorderRight(BorderStyle.MEDIUM);

        //生成一个表格样式(表格内容样式)
        HSSFCellStyle style1 = workbook.createCellStyle();
        style1.setBorderBottom(BorderStyle.THIN);
        style1.setBorderLeft(BorderStyle.THIN);
        style1.setBorderTop(BorderStyle.THIN);
        style1.setBorderRight(BorderStyle.THIN);
        //设置自动换行
        style1.setWrapText(true);

        //设置表格默认列宽度为12个字节
        sheet.setDefaultColumnWidth((short) 12);
        //设置表格第四列和第五列宽度为8000/256
        for(int i = 0; i < headers.length; i++){
            if(i >3 && i < 6){
                sheet.setColumnWidth(i, 8000);
            }
        }
        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (short i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell(i);
            //设置表头样式
            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text);
        }
        try {
            // 遍历集合数据，产生数据行
            Iterator<T> it = dataset.iterator();
            int index = 0;
            while (it.hasNext()) {
                index++;
                row = sheet.createRow(index);
                T t = (T) it.next();
                // 利用反射，根据javabean属性的先后顺序，动态调用getXxx()方法得到属性值
                Field[] fields = t.getClass().getDeclaredFields();
                for (short i = 0; i < headers.length; i++) {
                    HSSFCell cell = row.createCell(i);
                    cell.setCellStyle(style1);
                    Field field = fields[i];
                    String fieldName = field.getName();
                    String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                    Class tCls = t.getClass();
                    Method getMethod = tCls.getMethod(getMethodName, new Class[] {});
                    Object value = getMethod.invoke(t, new Object[] {});
                    // 判断值的类型后进行强制类型转换
                    String textValue = null;
                    // 其它数据类型都当作字符串简单处理
                    if(value != null && value != ""){
                        textValue = value.toString();
                    }
                    //对日期进行截取
                    if(i == headers.length - 1){
                        textValue = value.toString().substring(0, 10);
                    }
                    if (textValue != null) {
                        HSSFRichTextString richString = new HSSFRichTextString(textValue);
                        cell.setCellValue(richString);
                    }
                }
            }
            getExportedFile(workbook, fileName,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 方法说明: 指定路径下生成EXCEL文件
     * @return
     */
    public void getExportedFile(HSSFWorkbook workbook, String name,HttpServletResponse response) throws Exception {
        BufferedOutputStream fos = null;
        try {
            String fileName = name + ".xls";
            response.setContentType("application/x-msdownload");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String( fileName.getBytes("gb2312"), "ISO8859-1" ));
            fos = new BufferedOutputStream(response.getOutputStream());
            workbook.write(fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }
}
