package com.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * Created by Administrator on 2019/3/7 0007.
 */

public class NumberUtils {


    public static String accuracy(long num, long total, int scale){
        DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
        //可以设置精确几位小数
        df.setMaximumFractionDigits(scale);
        //模式 例如四舍五入s
        df.setRoundingMode(RoundingMode.HALF_UP);
        double accuracy_num =(num / total )* 100;
        return df.format(accuracy_num)+"%";
    }

}
