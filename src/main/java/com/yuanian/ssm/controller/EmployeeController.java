package com.yuanian.ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yuanian.ssm.bean.Employee;
import com.yuanian.ssm.bean.Msg;
import com.yuanian.ssm.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/28
 */
@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     * @param ids
     * @return
     */
    @ResponseBody
    @DeleteMapping("/emp/{ids}")
    public Msg deleteEmpById(@PathVariable("ids") String ids){
        if (ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
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
     * 如果直接ajax=PUT形式的请求
     * 封装的数据
     * Employee
     * [empId=1014,empName=null,gender=null,email=null,dId=null]
     *
     * 问题：
     * 请求体中有数据
     * 但是对象封装不上
     *
     * 原因：
     * Tomcat会将请求体中的数据，封装一个map
     * request.getParameter()就会从这个map中取值
     * springMVC封装pojo对象的时候会将pojo中每个属性的值，request.getParamter("email");
     *
     * Put请求，请求体中的数据，request.getParameter("empName")拿不到
     * Tomcat看到put不会封装请求体中的数据为map只有post形式请求才封装请求体为map
     *
     *
     * 解决方案
     * 要能支持直接发送PUT之类的请求，还要封装请求体中的数据
     * 1、配置上HttpPutFormContentFilter；
     * 2、作用：将请求体中的数据解析包装成一个map。
     * 3、request被重新包装，request.getParameter()被重写，就会从自己封装的map中取数据
     * 员工更新方法
     * @param employee
     * @return
     */
    @PutMapping("/emp/{empId}")
    @ResponseBody
    public Msg updateEmp(Employee employee){
        System.out.println(employee);
        employeeService.updateEmp(employee);
        return Msg.success();
    }

    /**
     * 根据id查询员工
     * @param id
     * @return
     */
    @GetMapping("/emp/{id}")
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }


    /**
     * 检验用户名是否可用
     * @param empName
     * @return
     */
    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkUser(@RequestParam("empName") String empName){
        //先判断用户名是否是合法的表达式
        String regx = "(^[a-zA-Z0-9_-]{4,16}$)|(^[\\u2E80-\\u9FFF]{2,5})";
        if (!empName.matches(regx)){
            return Msg.fail().add("va_msg","用户名必须是4-16位英文数字或2-5位中文");
        }
        //数据库用户名重复校验
        boolean b = employeeService.checkUser(empName);
        if (b){
            return Msg.success();
        }else {
            return Msg.fail().add("va_msg","用户名不可用");
        }
    }


    /**
     * 员工保存
     * 1、支持jsr303校验
     * 2、导入Hibernate-Validator
     */
    @PostMapping("/emp")
    @ResponseBody
    public Msg saveEmp( Employee employee, BindingResult result){
        if (result.hasErrors()){
            Map<String,Object> map = new HashMap<>();
            //校验失败，应该返回失败，在模态框中显示校验失败的错误信息
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

//    /**
//     * 员工保存
//     * @return
//     */
//    @PostMapping("/emp")
//    @ResponseBody
//    public Msg saveEmp(Employee employee){
//        employeeService.saveEmp(employee);
//        return Msg.success();
//    }

    @RequestMapping("/emps")
    @ResponseBody
    public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue = "1") Integer pn, Model model){
        PageHelper.startPage(pn,5);
        //startPage后面紧跟的这个查询就是一个分页查询
        List<Employee> emps = employeeService.getAll();
        //使用pageInfo包装后的结果,只需要将pageInfo交给页面
        //封装了详细的分页信息，包括我们查询出来的数据,传入连续显示的页面
        PageInfo page = new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }
    //@RequestMapping("/emps")
//    public String getEmps(@RequestParam(value="pn",defaultValue = "1") Integer pn, Model model){
//        PageHelper.startPage(pn,5);
//        //startPage后面紧跟的这个查询就是一个分页查询
//        List<Employee> emps = employeeService.getAll();
//        //使用pageInfo包装后的结果,只需要将pageInfo交给页面
//        //封装了详细的分页信息，包括我们查询出来的数据,传入连续显示的页面
//        PageInfo page = new PageInfo(emps,5);
//        model.addAttribute("pageInfo",page);
//        return "list";
//    }


}
