package com.yuanian.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sun.xml.internal.bind.v2.TODO;
import com.yuanian.ssm.bean.Employee;
import com.yuanian.ssm.bean.Msg;
import com.yuanian.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 员工控制类
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/28
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    /**
     * @MethodName deleteEmpById 员工单个及批量删除
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/29
     * @param ids
     * @Return com.yuanian.ssm.bean.Msg
     */
    @ResponseBody
    @DeleteMapping("/emp/{ids}")
    public Msg deleteEmpById(@PathVariable("ids") String ids){
        if (ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            for (String string : str_ids){
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }

    /**
     * @MethodName updateEmp 员工更新方法
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/29
     * @param employee
     * @Return com.yuanian.ssm.bean.Msg
     */
    @PutMapping("/emp/{empId}")
    @ResponseBody
    public Msg updateEmp(Employee employee){
        System.out.println(employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * @MethodName getEmp 根据id查询员工
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/29
     * @param id
     * @Return com.yuanian.ssm.bean.Msg
     */
    @GetMapping("/emp/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }


    /**
     * @MethodName checkUser 检验用户名是否可用
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param empName
     * @Return com.yuanian.ssm.bean.Msg
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkUser(@RequestParam("empName") String empName){
        String regx = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是4-16位英文数字或2-5位中文");
        }
        boolean b = employeeService.checkUser(empName);
        if (b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }


    /**
     * @MethodName saveEmp 员工保存
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/28
     * @param employee 员工信息
     * @param result
     * @Return com.yuanian.ssm.bean.Msg
     */
    @PostMapping("/emp")
    @ResponseBody
    public Msg saveEmp(@Valid Employee employee, BindingResult result){
        if (result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            List<FieldError> errors = result.getFieldErrors();
            for (FieldError fieldError:errors){
                System.out.println("错误的字段名："+fieldError.getField());
                System.out.println("错误信息："+fieldError.getDefaultMessage());
                map.put(fieldError.getField(),fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorFields",map);
        }else {
            employeeService.saveEmp(employee);
            return Msg.success();
        }
    }

    /**
     * @MethodName getEmpsWithJson 获取所有员工信息
     * @Author wudi
     * @Description TODO
     * @Date 2019/8/29
     * @param pn 页码
     * @Return com.yuanian.ssm.bean.Msg
     */
    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue = "1") Integer pn){
        PageHelper.startPage(pn,5);
        List<Employee> emps = employeeService.getAll();
        PageInfo page = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }
}
