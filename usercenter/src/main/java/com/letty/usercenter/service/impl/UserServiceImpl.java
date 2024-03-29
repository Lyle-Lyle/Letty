package com.letty.usercenter.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.letty.usercenter.common.ErrorCode;
import com.letty.usercenter.common.exception.BusinessException;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.dao.mapper.UserMapper;
import com.letty.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

import static com.letty.usercenter.constant.UserConstant.ADMIN_ROLE;
import static com.letty.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "Lyle";


    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {

        System.out.println(userAccount + "注册");
        // 非空判断
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
//            return -1L;
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        // 账户是否包含特殊字符 check if contains any unexpected chars
        //TODO

        //密码和校验密码相同 check if twice inputs are the same
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入密码不匹配");
        }
        // 账户不能重复 check if userAccount is available
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户重复");
        }

        // 2.加密
        String encryptedPassword = DigestUtil.md5Hex(SALT + userPassword);
        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        int result = userMapper.insert(user);
        if (result == 0) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }

        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 非空判断
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码过短");
        }

        // 账户是否包含特殊字符 check if contains any unexpected chars
        //TODO

        // 2.加密
        String encryptedPassword = DigestUtil.md5Hex(SALT + userPassword);

        //查询用户是否存在
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_account", userAccount);
        queryWrapper.eq("user_password", encryptedPassword);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            log.info("login failed, please check your username and password");
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User enUser = getEncryptedUser(user);
        //记录用户的登录态
        // attribute是一个map
        request.getSession().setAttribute(USER_LOGIN_STATE, enUser);
        return enUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * encrypt user information
     * @param originUser
     * @return
     */
    @Override
    public User getEncryptedUser(User originUser) {
        if (originUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        User enUser = new User();
        enUser.setId(originUser.getId());
        enUser.setUsername(originUser.getUsername());
        enUser.setUserAccount(originUser.getUserAccount());
        enUser.setAvatarUrl(originUser.getAvatarUrl());
        enUser.setGender(originUser.getGender());
        enUser.setPhone(originUser.getPhone());
        enUser.setEmail(originUser.getEmail());
        enUser.setUserRole(originUser.getUserRole());
        enUser.setUserStatus(originUser.getUserStatus());
        enUser.setCreatedTime(originUser.getCreatedTime());

        return enUser;
    }


    @Override
    public List<User> searchUsersByTags(List<String> tagList) {
        if (CollectionUtils.isEmpty(tagList)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        for (String tagName : tagList) {
            queryWrapper = queryWrapper.like("tags", tagName);
        }
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList.get(0));
        return userList.stream().map(this::getEncryptedUser).collect(Collectors.toList());
    }

    @Override
    public Integer updateUser(User user, User loginUser) {
        Long userId = user.getId();
        if (userId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 如果是管理员 允许更新任意用户
        if (!isAdmin(loginUser) && userId != loginUser.getId()) {
           throw new BusinessException(ErrorCode.NO_AUTH);
        }
        User preUser = userMapper.selectById(userId);
        if (preUser == null) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        return userMapper.updateById(user);
    }

    @Override
    public User getLoginUser(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObj == null) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        return (User) userObj;
    }


    /**
     * check if is admin
     * @param request
     * @return
     */
    public boolean isAdmin(HttpServletRequest request) {
        //鉴权
        //仅限管理员查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User user = (User)userObj;
        if (user == null || user.getUserRole() != ADMIN_ROLE) {
            return false;
        }
        return true;
    }

    public boolean isAdmin(User loginUser) {
        return loginUser != null && loginUser.getUserRole() == ADMIN_ROLE;
    }
}
