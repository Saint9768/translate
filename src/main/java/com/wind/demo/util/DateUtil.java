package com.wind.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理类
 * @author Saint
 * @createTime 2020-05-20 11:35
 */
public class DateUtil {

    /**
     * 格式化日期为yyyy-MM-dd的形式
     * @param date
     * @return
     */
    public static Date formateDate(Date date){
        String string = dateToString(date);
        Date date1 = stringToDate(string);
        return date1;
    }

    /**
     * 日期转字符
     * @param date
     * @return
     */
    private static String dateToString(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String string = format.format(date);
        return string;
    }

    /**
     * 字符转日期
     * @param string
     * @return
     */
    private static Date stringToDate(String string){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = format.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

}
