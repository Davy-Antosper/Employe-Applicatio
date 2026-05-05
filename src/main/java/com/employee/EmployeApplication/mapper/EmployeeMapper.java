package com.employee.EmployeApplication.mapper;

import com.employee.EmployeApplication.dto.*;
import com.employee.EmployeApplication.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class EmployeeMapper {


    public EmployeeResponseDTO toResponseDTO(Employee e) {
        return new EmployeeResponseDTO(e.getEmployeeId(),e.getEmployeeName(),e.getEmployeeCity(),
                e.getSpouse() != null ? toSpouseDTO(e.getSpouse()) : null,
                e.getAddresses().stream().map(this::toAddressDTO).collect(Collectors.toSet()),
                e.getProjects().stream().map(this::toProjectDTO).collect(Collectors.toList())
        );
    }

    public Employee toEntity(EmployeeRequestDTO dto) {
        Employee employee = Employee.builder()
                .employeeName(dto.employeeName())
                .employeeCity(dto.employeeCity())
                .build();

        if (dto.spouse() != null) {
            employee.setSpouse(toSpouseEntity(dto.spouse()));
        }

        if (dto.addresses() != null) {
            dto.addresses().forEach(aDto -> employee.addAddress(toAddressEntity(aDto)));
        }

        if (dto.projects() != null) {
            dto.projects().forEach(pDto -> employee.addProject(toProjectEntity(pDto)));
        }

        return employee;
    }


    public AddressDTO toAddressDTO(Address a) {
        return new AddressDTO(a.getId(), a.getLine1(), a.getLine2(),
                a.getZipCode(), a.getCity(), a.getState(), a.getCountry());
    }

    public Address toAddressEntity(AddressDTO dto) {
        return Address.builder()
                .id(dto.id())
                .line1(dto.line1())
                .line2(dto.line2())
                .zipCode(dto.zipCode())
                .city(dto.city())
                .state(dto.state())
                .country(dto.country())
                .build();
    }


    public SpouseDTO toSpouseDTO(Spouse s) {
        return new SpouseDTO(s.getId(), s.getName(), s.getMobileNumber());
    }

    public Spouse toSpouseEntity(SpouseDTO dto) {
        return Spouse.builder()
                .id(dto.id())
                .name(dto.name())
                .mobileNumber(dto.mobileNumber())
                .build();
    }


    public ProjectDTO toProjectDTO(Project p) {
        return new ProjectDTO(p.getId(), p.getName(), p.getClientName());
    }

    public Project toProjectEntity(ProjectDTO dto) {
        return Project.builder()
                .id(dto.id())
                .name(dto.name())
                .clientName(dto.clientName())
                .build();
    }


    public List<EmployeeResponseDTO> toResponseDTOList(List<Employee> employees) {
        return employees.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }
}