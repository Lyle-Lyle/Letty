package com.letty.usercenter.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.letty.usercenter.dao.entity.User;
import jakarta.servlet.http.HttpServletRequest;


public interface UserService extends IService<User> {



    /**
     *  user register
     * @param userAccount
     * @param userPassword
     * @param checkPassword check if the password same
     * @return user id
     */
    Long userRegister(String userAccount, String userPassword, String checkPassword);

    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
    int userLogout(HttpServletRequest request);
    User getEncryptedUser(User originUser);
}
