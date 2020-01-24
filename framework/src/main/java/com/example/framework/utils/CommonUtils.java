package com.example.framework.utils;

import java.util.List;

/**
 * FileName:CommonUtils
 * Create Date:2020/1/23 21:04
 * Profile:
 */
public class CommonUtils {

    /**
     * 检查List是否可用
     * @param list
     * @return
     */
    public static boolean isEmpty(List list){
      return list!=null&& list.size ()>0;
    }
}
