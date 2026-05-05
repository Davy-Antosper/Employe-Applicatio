package com.employee.EmployeApplication.dto;

import jakarta.validation.constraints.NotBlank;

public record SpouseDTO(

        @Schema(description = "ID (null à la création)", example = "1")
        Integer id,

        @NotBlank(message = "Spouse name is required")
        String name,

        @Schema(description = "Numéro de téléphone", example = "+25712345678")
        String mobileNumber

) {}