package com.employee.EmployeApplication.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.Set;

public record EmployeeRequestDTO(

        @NotBlank(message = "Employee name is required")
        String employeeName,

        @Schema(description = "Ville de résidence", example = "Bujumbura")
        String employeeCity,
        SpouseDTO spouse,
        Set<AddressDTO> addresses,
        List<ProjectDTO> projects

) {}