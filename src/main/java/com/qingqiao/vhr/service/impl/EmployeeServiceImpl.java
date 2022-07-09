package com.qingqiao.vhr.service.impl;

import com.qingqiao.vhr.bean.*;
import com.qingqiao.vhr.mapper.EmployeeMapper;
import com.qingqiao.vhr.service.EmployeeService;
import com.qingqiao.vhr.utils.ResponseBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public ResponsePageBean getAllEmp(Integer page, Integer size, String name) {
        if (page !=null && page!=1){
            page=(page-1)*size;
        }
        Integer total = employeeMapper.getTotal(name);
        List<Employee> allEmp = employeeMapper.getAllEmp(page-1, size,name);
        ResponsePageBean bean = new ResponsePageBean();
        bean.setData(allEmp);
        bean.setTotal(total);
        return bean;
    }

    @Override
    public int insertEmp(Employee employee) {
        List<Employee> queryall = employeeMapper.queryall();
        String workID1="0";
        int i1 = Integer.parseInt(workID1);
        for (Employee employee1 : queryall) {
            String workID = employee1.getWorkID();
            String trim = workID.trim();
            int i = Integer.parseInt(trim);
            if (i1<i){
                i1=i;
            }
        }
        i1=i1+1;
        String s = String.valueOf(i1);
        System.out.println("s:"+s);
        employee.setWorkID(s);
        //计算合同日期

        Date endContract = employee.getEndContract();
        Date beginContract = employee.getBeginContract();
        long time = endContract.getTime();
        long time1 = beginContract.getTime();
        long l=time-time1;
        double l1 =(double) l / 1000 / 60 / 60 / 24 / 30 / 12;
        String s1 = String.format("%.2f", l1);
        Double of = Double.valueOf(s1);
        employee.setContractTerm(of);
        //计算入职日期
        java.sql.Date date = new java.sql.Date(System.currentTimeMillis());
        employee.setBeginDate(date);
        //在职
        employee.setWorkState("在职");
        return employeeMapper.insertSelective(employee);
    }

    @Override
    public int deleteEmp(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int deleteEmpMany(Integer[] ids) {
        return employeeMapper.deleteEmpMany(ids);
    }

    @Override
    public int updateEmp(Employee employee) {
        return employeeMapper.updateByPrimaryKeySelective(employee);
    }

    @Override
    public List<Nation> getNation(ResponseBean responseBean) {
        return employeeMapper.getNation(responseBean);
    }

    @Override
    public List<Politicsstatus> getPol(ResponseBean responseBean) {
        return employeeMapper.getPol(responseBean);
    }

}
