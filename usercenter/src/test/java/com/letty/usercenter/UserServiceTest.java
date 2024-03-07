package com.letty.usercenter;

import com.letty.usercenter.service.UserService;
import com.letty.usercenter.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class UserServiceTest {


    @Resource
    private UserService userService;

    @Test
    public void userRegister() {
        String userAccount = "Lyle";
        String userPassword = "123456";
        String checkPassword = "123456";
        Long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1L, result);

    }

//    @Test
//    void contextLoads() {
//        System.out.println("11");
//    }
}
