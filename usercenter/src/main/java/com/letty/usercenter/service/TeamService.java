package com.letty.usercenter.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.letty.usercenter.dao.entity.Team;
import com.letty.usercenter.dao.entity.User;
import com.letty.usercenter.dto.TeamQuery;
import com.letty.usercenter.dto.req.TeamJoinReq;
import com.letty.usercenter.dto.req.TeamQuitReq;
import com.letty.usercenter.dto.req.TeamUpdateReq;
import com.letty.usercenter.vo.TeamUserVO;

import java.util.List;

/**
 * 队伍服务
 *
 */
public interface TeamService extends IService<Team> {

    /**
     * 创建队伍
     *
     * @param team
     * @param loginUser
     * @return
     */
    long addTeam(Team team, User loginUser);

    /**
     * 搜索队伍
     *
     * @param teamQuery
     * @param isAdmin
     * @return
     */
    List<TeamUserVO> listTeams(TeamQuery teamQuery, boolean isAdmin);

    /**
     * 更新队伍
     *
     * @param teamUpdateReq
     * @param loginUser
     * @return
     */
    boolean updateTeam(TeamUpdateReq teamUpdateReq, User loginUser);

    /**
     * 加入队伍
     *
     * @param teamJoinReq
     * @return
     */
    boolean joinTeam(TeamJoinReq teamJoinReq, User loginUser);

    /**
     * 退出队伍
     *
     * @param teamQuitReq
     * @param loginUser
     * @return
     */
    boolean quitTeam(TeamQuitReq teamQuitReq, User loginUser);

    /**
     * 删除（解散）队伍
     *
     * @param id
     * @param loginUser
     * @return
     */
    boolean deleteTeam(long id, User loginUser);
}

