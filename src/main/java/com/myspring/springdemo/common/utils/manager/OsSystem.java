package com.myspring.springdemo.common.utils.manager;

import org.springframework.stereotype.Component;

/**
 * @author huanghao
 */
public class OsSystem {
    public static boolean isWindows(){
        return System.getProperty("os.name").toLowerCase().contains("windows");
    }

    public static boolean isLinux(){
        return System.getProperty("os.name").toLowerCase().contains("linux");
    }
}
