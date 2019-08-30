package com.yuanian.ssm.service;

import com.yuanian.ssm.bean.Department;
import com.yuanian.ssm.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/29
 */
@Service
public class DepartmentService {
    @Autowired
    DepartmentMapper departmentMapper;
    /**
     * @MethodName
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/30
     * @param null
     * @Return List<Department>
     */
    public List<Department> getDepts() {
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }
}
