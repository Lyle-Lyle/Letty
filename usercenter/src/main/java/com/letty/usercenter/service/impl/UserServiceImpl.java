package com.letty.usercenter.service.impl;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.dao.mapper.UserMapper;
import com.letty.usercenter.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import static com.letty.usercenter.constant.UserConstant.USER_LOGIN_STATE;

@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;

    private static final String SALT = "Lyle";


    @Override
    public Long userRegister(String userAccount, String userPassword, String checkPassword) {

        // 非空判断
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1L;
        }
        if (userAccount.length() < 4) {
            return -1L;
        }
        if (userPassword.length() < 8) {
            return -1L;
        }

        // 账户是否包含特殊字符 check if contains any unexpected chars
        //TODO

        //密码和校验密码相同 check if twice inputs are the same
        if (userPassword.equals(checkPassword)) {
            return -1L;
        }
        // 账户不能重复 check if userAccount is available
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_account", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1L;
        }

        // 2.加密
        String encryptedPassword = DigestUtil.md5Hex(SALT + userPassword);

        //3.插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptedPassword);
        int result = userMapper.insert(user);
        if (result == 0) {
            return -1L;
        }
        return user.getId();
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 非空判断
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8) {
            return null;
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
            return null;
        }
        User enUser = getEncryptedUser(user);
        //记录用户的登录态
        // attribute是一个map
        request.getSession().setAttribute(USER_LOGIN_STATE, enUser);
        return enUser;
    }

    /**
     * encrypt user information
     * @param originUser
     * @return
     */
    @Override
    public User getEncryptedUser(User originUser) {
        User enUser = new User();
        enUser.setId(originUser.getId());
        enUser.setUsername(originUser.getUsername());
        enUser.setUserAccount(originUser.getUserAccount());
        enUser.setUserPassword(originUser.getUserPassword());
        enUser.setAvatarUrl(originUser.getAvatarUrl());
        enUser.setGender(originUser.getGender());
        enUser.setPhone(originUser.getPhone());
        enUser.setEmail(originUser.getEmail());
        enUser.setUserStatus(originUser.getUserStatus());
        enUser.setCreatedTime(originUser.getCreatedTime());
        return enUser;
    }
}
