package com.lyle.lettyeducontentapi.api;

import com.lyle.lettyedubase.base.model.PageParams;
import com.lyle.lettyedubase.base.model.PageResult;
import com.lyle.lettyeducontentmodel.model.dto.QueryCourseParamDto;
import com.lyle.lettyeducontentmodel.model.po.CourseBase;
import com.lyle.lettyeducontentservice.service.CourseBaseInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Arrays;

@RestController
@Api(value = "课程信息编辑接口", tags = "课程信息编辑接口")
public class CourseBaseInfoController {
    @Resource
    CourseBaseInfoService courseBaseInfoService;
    @ApiOperation("课程查询接口")
    @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams, @RequestBody QueryCourseParamDto queryCourseParams) {
        PageResult<CourseBase> result = courseBaseInfoService.queryCourseBaseList(pageParams, queryCourseParams);
        return result;
    }
}
