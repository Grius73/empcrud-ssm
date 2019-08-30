package com.yuanian.ssm.service;

import com.yuanian.ssm.bean.Employee;
import com.yuanian.ssm.bean.EmployeeExample;
import com.yuanian.ssm.dao.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/28
 */
@Service
public class EmployeeService {
    @Autowired
    EmployeeMapper employeeMapper;

    /**
     * @MethodName 获取所有员工信息
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/30
     * @param
     * @Return List<Employee>
     */
    public List<Employee> getAll(){
        return employeeMapper.selectByExampleWithDept(null);
    }


    /**
     * @MethodName saveEmp 员工信息保存
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param employee
     * @Return
     */
    public void saveEmp(Employee employee) {
        employeeMapper.insertSelective(employee);
    }


    /**
     * @MethodName deleteBatch 批量删除
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param ids
     * @Return
     */
    public void deleteBatch(List<Integer> ids) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        //delete from xxx where emp_id in(1,2,3)
        criteria.andEmpIdIn(ids);
        employeeMapper.deleteByExample(example);
    }

    /**
     * @MethodName deleteEmp 员工单个删除
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param id
     * @Return
     */
    public void deleteEmp(Integer id) {
        employeeMapper.deleteByPrimaryKey(id);
    }


    /**
     * @MethodName updateEmp 员工信息更新
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param employee
     * @Return
     */
    public void updateEmp(Employee employee) {
        employeeMapper.updateByPrimaryKeySelective(employee);
    }

   /**
    * @MethodName  getEmp 根据id获取用户
    * @Author wudi
    * @Description TODO
    * @Date 2019/8/29
    * @param id 用户id
    * @Return Employee
    */

    public Employee getEmp(Integer id) {
        Employee employee = employeeMapper.selectByPrimaryKey(id);
        return employee;
    }

  /**
   * @MethodName checkUser
   * @Author wudi
   * @Description TODO
   * @Date 2019/8/29
   * @param empName 用户名
   * @Return boolean
   */

    public boolean checkUser(String empName) {
        EmployeeExample example = new EmployeeExample();
        EmployeeExample.Criteria criteria = example.createCriteria();
        criteria.andEmpNameEqualTo(empName);
        long count = employeeMapper.countByExample(example);
        return count == 0;
    }
}
