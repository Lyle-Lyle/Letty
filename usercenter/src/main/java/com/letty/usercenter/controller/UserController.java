package com.letty.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.dto.req.UserLoginReq;
import com.letty.usercenter.dto.req.UserRegisterReq;
import com.letty.usercenter.service.UserService;
import com.letty.usercenter.service.impl.UserServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.letty.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.letty.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public Long userRegister(@RequestBody UserRegisterReq params) {
        if (params == null) {
            return null;
        }
        String userAccount = params.getUserAccount();
        String userPassword = params.getUserPassword();
        String checkPassword = params.getCheckPassword();
        return userService.userRegister(userAccount, userPassword, checkPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginReq params, HttpServletRequest request) {
        if (params == null) {
            return null;
        }
        String userAccount = params.getUserAccount();
        String userPassword = params.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        return userService.userLogin(userAccount, userPassword, request);
    }

    @GetMapping("/search")
    public List<User> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return new ArrayList<>();
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        判断字符串是否空
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
//        为什么不用循环用 stream
        // 这段逻辑如果再复杂一点就应该放到service中了
        return userList.stream().map(user -> {
            return userService.getEncryptedUser(user);
        }).collect(Collectors.toList());
    }

//    如果用DeleteMapping这里地址是空，但是不是很直观啊
    @PostMapping("/delete")
    public boolean deleteUser(@RequestBody long id,HttpServletRequest request) {
        if (!isAdmin(request)) {
            return false;
        }
        if (id <= 0) {
            return false;
        }
//        mp会自动把这个作为逻辑删除
        return userService.removeById(id);
    }

    /**
     * check if is admin
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //鉴权
        //仅限管理员查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }
}
