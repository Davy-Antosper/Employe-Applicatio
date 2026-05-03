package com.employee.EmployeApplication.controller;

import com.employee.EmployeApplication.entity.Employee;
import com.employee.EmployeApplication.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@ResponseBody
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @RequestMapping("/employees")
    public List<Employee> findAllEmployee(){
       return employeeService.getAllEmployees();
    }
    @RequestMapping("/employees/{id}")
    public Employee findAllEmployee(@PathVariable int id){
        return employeeService.getAnEmployee(id);
    }
    @RequestMapping(value ="/employees",method = RequestMethod.POST)
    public void createEmployee(@RequestBody Employee employee){
        employeeService.createEmployee(employee);
    }

    @RequestMapping(value ="/employees",method = RequestMethod.PUT)
    public void updateEmployee(@RequestBody Employee employee){
        employeeService.updateEmployee(employee);
    }

    @RequestMapping(value ="/employees/{id}",method = RequestMethod.DELETE)
    public void deleteEmployee(@PathVariable int id){
        employeeService.deleteEmployee(id);
    }
}
