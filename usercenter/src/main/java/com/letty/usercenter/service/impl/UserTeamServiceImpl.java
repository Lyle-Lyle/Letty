package com.letty.usercenter.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.dao.entity.UserTeam;
import com.letty.usercenter.dao.mapper.UserMapper;
import com.letty.usercenter.dao.mapper.UserTeamMapper;
import com.letty.usercenter.service.UserService;
import com.letty.usercenter.service.UserTeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户队伍服务实现类

 */
@Service
public class UserTeamServiceImpl extends ServiceImpl<UserTeamMapper, UserTeam>
        implements UserTeamService {

}
