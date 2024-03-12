package com.letty.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.letty.usercenter.common.ErrorCode;
import com.letty.usercenter.common.ResultUtils;
import com.letty.usercenter.common.exception.BusinessException;
import com.letty.usercenter.common.resp.BaseResponse;
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
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterReq params) {
        System.out.println("注册参数" + params);
        if (params == null) {
//            return ResultUtils.error(ErrorCode.PARAMS_ERROR);
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = params.getUserAccount();
        String userPassword = params.getUserPassword();
        String checkPassword = params.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
//        return new BaseResponse<>(0, result, "ok");
        //这样每次都new是不是很麻烦啊 而且代码容易被改
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginReq params, HttpServletRequest request) {
        if (params == null) {
            return null;
        }
        System.out.println(params.getUserPassword());
        String userAccount = params.getUserAccount();
        String userPassword = params.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }

        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }



    @PostMapping("/logout")
    public BaseResponse<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        int result = userService.userLogout(request);
        return ResultUtils.success(result);
    }




    @GetMapping("/search")
    public BaseResponse<List<User>> searchUsers(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
//            return new ArrayList<>();
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
//        判断字符串是否空
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);
        }
        List<User> userList = userService.list(queryWrapper);
//        为什么不用循环用 stream
        // 这段逻辑如果再复杂一点就应该放到service中了
        List<User> list = userList.stream().map(user -> {
            return userService.getEncryptedUser(user);
        }).collect(Collectors.toList());
        return ResultUtils.success(list);
    }


    /**
     * 获取当前用户的登录态
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currenUser = (User)userObj;
        if (currenUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        // todo 这里还需要优化 如果封号 删除之类的 要筛选一下
        long UserId = currenUser.getId();
        User user = userService.getById(UserId);
        User encryptedUser = userService.getEncryptedUser(user);
        return ResultUtils.success(encryptedUser);
    }



//    如果用DeleteMapping这里地址是空，但是不是很直观啊
    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id,HttpServletRequest request) {
        if (!isAdmin(request)) {
//            return ResultUtils.success(false);
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
//        mp会自动把这个作为逻辑删除
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
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
