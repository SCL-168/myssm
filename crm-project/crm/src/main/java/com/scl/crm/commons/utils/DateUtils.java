package com.scl.crm.commons.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/*
对date类型数据进行封装
 */
public class DateUtils {

    /**
     * 对指定的date对象进行格式化
     * */
    public static String formateDateTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr=dateFormat.format(date);
        return dateStr;
    }

    public static String formateDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr=dateFormat.format(date);
        return dateStr;
    }
    public static String formateTime(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String dateStr=dateFormat.format(date);
        return dateStr;
    }
}
