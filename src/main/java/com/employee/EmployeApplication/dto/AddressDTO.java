package com.employee.EmployeApplication.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record AddressDTO(

        Integer id,

        @NotBlank(message = "Line 1 is required")
        @Schema(description = "Rue principale", example = "mutakura,kinama")
        String line1,

        String line2,

        @Schema(description = "Code postal", example = "75001")
        String zipCode,

        @NotBlank(message = "City is required")
        @Schema(description = "Ville", example = "buja")
        String city,

        @Schema(description = "État / Province", example = "californie")
        String state,

        @NotBlank(message = "Country is required")
        @Schema(description = "Pays", example = "Burundi")
        String country

) {}