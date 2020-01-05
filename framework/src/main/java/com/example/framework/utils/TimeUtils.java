package com.example.framework.utils;

public class TimeUtils {
    /**
     * 转换毫秒格式 HH:mm:ss
     * @param ms
     */
    public static String formatDuring(long ms){
        long hours =(ms%(1000*60*60*24))/(1000*60*60);
        long minutes =(ms%(1000*60*60))/(1000*60);
        long seconds =(ms%(1000*60))/1000;

        //如果为个位数 前面补0
        String h=hours+"";
        String m=minutes+"";
        String s=seconds+"";

        if(hours<10) h="0"+hours;
        if(minutes<10) m="0"+minutes;
        if(seconds<10) s="0"+seconds;

        return h+":"+m+":"+s;
    }
}
