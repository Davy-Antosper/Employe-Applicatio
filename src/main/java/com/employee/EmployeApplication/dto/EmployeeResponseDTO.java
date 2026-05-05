package com.employee.EmployeApplication.dto;

import java.util.List;
import java.util.Set;

public record EmployeeResponseDTO(

        Integer employeeId,

        String employeeName,

        String employeeCity,

        SpouseDTO spouse,

        Set<AddressDTO> addresses,

        List<ProjectDTO> projects

) {}