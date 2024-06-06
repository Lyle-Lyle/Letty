package com.lyle.lettyeducontentservice.service;

import com.lyle.lettyedubase.base.model.PageParams;
import com.lyle.lettyedubase.base.model.PageResult;
import com.lyle.lettyeducontentmodel.model.dto.QueryCourseParamDto;
import com.lyle.lettyeducontentmodel.model.po.CourseBase;

public interface CourseBaseInfoService {
    /**
     * 课程查询接口
     * @param pageParams 分页参数
     * @param queryCourseParams 查询条件
     * @return
     */
    PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamDto queryCourseParams);
}