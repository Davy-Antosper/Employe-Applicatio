package com.employee.EmployeApplication.service;

import com.employee.EmployeApplication.dto.EmployeeRequestDTO;
import com.employee.EmployeApplication.dto.EmployeeResponseDTO;
import com.employee.EmployeApplication.entity.Employee;
import com.employee.EmployeApplication.exception.EmployeeNotFoundException;
import com.employee.EmployeApplication.mapper.EmployeeMapper;
import com.employee.EmployeApplication.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired 
    private EmployeeRepository employeeRepository;
    @Autowired 
    private EmployeeMapper employeeMapper;


    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeMapper.toResponseDTOList(employeeRepository.findAll());
    }


    public EmployeeResponseDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
        return employeeMapper.toResponseDTO(employee);
    }


    @Transactional
    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO request) {
        Employee employee = employeeMapper.toEntity(request);
        Employee saved = employeeRepository.save(employee);
        return employeeMapper.toResponseDTO(saved);
    }

    @Transactional
    public EmployeeResponseDTO updateEmployee(Integer id, EmployeeRequestDTO request) {
        Employee existing = employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));

        existing.setEmployeeName(request.employeeName());
        existing.setEmployeeCity(request.employeeCity());

        if (request.spouse() != null) {
            existing.setSpouse(employeeMapper.toSpouseEntity(request.spouse()));
        }

        if (request.addresses() != null) {
            existing.getAddresses().clear();
            request.addresses().forEach(aDto -> existing.addAddress(employeeMapper.toAddressEntity(aDto)));
        }

        if (request.projects() != null) {
            existing.getProjects().clear();
            request.projects().forEach(pDto -> existing.addProject(employeeMapper.toProjectEntity(pDto)));
        }

        return employeeMapper.toResponseDTO(employeeRepository.save(existing));
    }


    @Transactional
    public void deleteEmployee(Integer id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
    }
}