package com.myspring.springdemo.common.utils.client;

/**
 * 参数验证相关正则表达式
 * @author huanghao
 */
public abstract class RegexPatternsUtils {
    /**
     * 字符串中不能包含“.”或“|”
     */
    public static final String INVALID_TASK_NAME_SIGN = "^((?!\\.|\\|).)*$";

    /**
     * 字符串中包含“.”或“:”
     */
    public static final String INVALID_PDB_NAME_SIGN = "(\\.|:)";

}
