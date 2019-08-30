package com.yuanian.ssm.test;
import com.yuanian.ssm.bean.Department;
import com.yuanian.ssm.bean.Employee;
import com.yuanian.ssm.dao.DepartmentMapper;
import com.yuanian.ssm.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

/**
 * 测试dao层工作
 * @ProjectName: ssm_wudi
 * @Author: wudi
 * @CreateDate: 2019/8/28
 * 1 导入springTest模块
 * 2 @ContextConfiguration指定Spring配置文件的位置
 * 3 直接autowired要使用的组件
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class MapperTest {
     /**
     * 测试DepartmentMapper
     */
    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    SqlSession sqlSession;

    @Autowired
    EmployeeMapper employeeMapper;

    @Test
    public void testCRUD(){
//        //1,创建springIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext();
//        //2，从容器中获取mapper
//        DepartmentMapper bean = ioc.getBean(DepartmentMapper.class);
//         departmentMapper.insertSelective(new Department(null,"研发部"));
//         EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
//        for (int i = 0; i <10 ; i++) {
//            String uid= UUID.randomUUID().toString().substring(0,5)+ i;
//            mapper.insertSelective(new Employee(null,uid,"M",uid+"@outlook.com",(int)(1+Math.random()*(6-1+1))));
//        }
//         System.out.println("批量完成");
        List<Employee> emps =employeeMapper.selectByExampleWithDept(null);
        System.out.println(emps);
    }
}
