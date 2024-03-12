package com.letty.usercenter.controller;


import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/management")
public class ManageController {

    @Resource
    private UserService userService;


    @GetMapping("/userList")
    public List<User> getUserList() {
//        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
//        User currenUser = (User)userObj;
//        if (currenUser == null) {
//            return null;
//        }
//        // todo 这里还需要优化 如果封号 删除之类的 要筛选一下
//        long UserId = currenUser.getId();
        return userService.list();
    }
}
