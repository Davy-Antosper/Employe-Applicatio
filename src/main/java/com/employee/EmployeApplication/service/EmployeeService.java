package com.employee.EmployeApplication.service;

import com.employee.EmployeApplication.entity.Employee;
import com.employee.EmployeApplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    private List<Employee> employeeList = new ArrayList<>(Arrays.asList(
            new Employee(1,"First Employee","Washington"),
            new Employee(2,"second Employee","Gitega")
    ));


    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();

    }

    public Employee getAnEmployee(int id){
//        return employeeList.stream()
//                .filter(e -> e.getEmployeeId()==id)
//                .findFirst()
//                .orElseThrow(() -> new RuntimeException("no Employee with that id found..!"));
       return  employeeRepository.findById(id).orElseThrow(() -> new RuntimeException("not found"));
    }

    public void createEmployee(Employee employee){
        if (employee.getAddresses() != null) {
            employee.getAddresses().forEach(address -> address.setEmployee(employee));
        }

        if (employee.getProjects() != null) {
            employee.getProjects().forEach(project -> {
                project.getEmployeeList().add(employee);
            });
        }       

        employeeRepository.save(employee);    }

    public void updateEmployee(Employee employee) {
//        employeeList.stream()
//                .filter(e->e.getEmployeeId()==employee.getEmployeeId())
//                .findFirst()
//                .ifPresent(e->{
//                    e.setEmployeeId(employee.getEmployeeId());
//                    e.setEmployeeName(employee.getEmployeeName());
//                    e.setEmployeeCity(employee.getEmployeeCity());
//                });
        employeeRepository.save(employee);
    }

    public void deleteEmployee(int id) {
        employeeRepository.delete(employeeRepository.getById(id));
      //  employeeList.removeIf(e -> e.getEmployeeId() == id);
    }
}
