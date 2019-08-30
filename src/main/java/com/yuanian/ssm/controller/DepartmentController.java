package com.yuanian.ssm.controller;

import com.yuanian.ssm.bean.Department;
import com.yuanian.ssm.bean.Msg;
import com.yuanian.ssm.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/29
 */
@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    /**
     * 返回所有部门信息
     */
    @RequestMapping("/depts")
    @ResponseBody
    public Msg getDepts(){
        //查出所有部门的信息
        List<Department> list = departmentService.getDepts();
        return Msg.success().add("depts",list);
    }

}
