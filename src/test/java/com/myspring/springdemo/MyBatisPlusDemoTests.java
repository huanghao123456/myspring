//package com.myspring.springdemo;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import com.myspring.springdemo.dao.entity.SysUser;
//import org.junit.jupiter.api.Disabled;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @author: hhuang
// */
//@Disabled
//@SpringBootTest
//public class MyBatisPlusDemoTests {
//
//    @Autowired
//    private ISysUserService userService;
//
//    @Test
//    void queryAllUser() {
//        System.out.println(userService.list());
//    }
//
//    @Test
//    void queryConditionalUser() {
//        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
//        wrapper.eq("sex", '1')
//                .isNotNull("phonenumber")
//                .le("id", 100)
//                .like("name", "黄%");
//        System.out.println(userService.list(wrapper));
//    }
//
//    @Test
//    void queryAllUserPage() {
//        Page<SysUser> page = new Page<>(2, 2);
//        Page<SysUser> userPage = userService.page(page);
//        userPage.getRecords().forEach(System.out::println);
//    }
//}
