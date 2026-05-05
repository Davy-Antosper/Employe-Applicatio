package com.employee.EmployeApplication.service;

import com.employee.EmployeApplication.dto.EmployeeRequestDTO;
import com.employee.EmployeApplication.dto.EmployeeResponseDTO;
import com.employee.EmployeApplication.entity.Employee;
import com.employee.EmployeApplication.exception.EmployeeNotFoundException;
import com.employee.EmployeApplication.mapper.EmployeeMapper;
import com.employee.EmployeApplication.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeResponseDTO responseDTO;
    private EmployeeRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        employee = Employee.builder()
                .employeeId(1)
                .employeeName("Irumva Alain")
                .employeeCity("Bujumbura")
                .build();

        responseDTO = new EmployeeResponseDTO(1, "Irumva Alain", "Bujumbura", null, null, null);
        requestDTO  = new EmployeeRequestDTO("Irumva Alain", "Bujumbura", null, null, null);
    }


    @Test
    @DisplayName("getAllEmployees : retourne la liste complete")
    void getAllEmployees_shouldReturnList() {
        when(employeeRepository.findAll()).thenReturn(List.of(employee));
        when(employeeMapper.toResponseDTOList(any())).thenReturn(List.of(responseDTO));

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).employeeName()).isEqualTo("Irumva Alain");
        verify(employeeRepository).findAll();
    }

    @Test
    @DisplayName("getAllEmployees : retourne liste vide si aucun employe")
    void getAllEmployees_whenEmpty_shouldReturnEmptyList() {
        when(employeeRepository.findAll()).thenReturn(List.of());
        when(employeeMapper.toResponseDTOList(any())).thenReturn(List.of());

        List<EmployeeResponseDTO> result = employeeService.getAllEmployees();

        assertThat(result).isEmpty();
    }


    @Test
    @DisplayName("getEmployeeById : retourne l'employé si trouvé")
    void getEmployeeById_whenExists_shouldReturnDTO() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponseDTO(employee)).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.getEmployeeById(1);

        assertThat(result.employeeId()).isEqualTo(1);
        assertThat(result.employeeName()).isEqualTo("Irumva Alain");
    }

    @Test
    void getEmployeeById_whenNotFound_shouldThrow() {
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.getEmployeeById(99))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("99");
    }


    @Test
    @DisplayName("createEmployee : sauvegarde et retourne le DTO")
    void createEmployee_shouldSaveAndReturnDTO() {
        when(employeeMapper.toEntity(requestDTO)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDTO(employee)).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.createEmployee(requestDTO);

        assertThat(result.employeeName()).isEqualTo("Irumva Alain");
        verify(employeeRepository).save(employee);
    }


    @Test
    @DisplayName("updateEmployee : met à jour et retourne le DTO")
    void updateEmployee_whenExists_shouldUpdate() {
        when(employeeRepository.findById(1)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(any())).thenReturn(employee);
        when(employeeMapper.toResponseDTO(any())).thenReturn(responseDTO);

        EmployeeResponseDTO result = employeeService.updateEmployee(1, requestDTO);

        assertThat(result).isNotNull();
        verify(employeeRepository).save(employee);
    }

    @Test
    @DisplayName("updateEmployee :  EmployeeNotFoundException si introuvable")
    void updateEmployee_whenNotFound_shouldThrow() {
        when(employeeRepository.findById(99)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> employeeService.updateEmployee(99, requestDTO))
                .isInstanceOf(EmployeeNotFoundException.class);
    }


    @Test
    @DisplayName("deleteEmployee : supprime si l'employé existe")
    void deleteEmployee_whenExists_shouldDelete() {
        when(employeeRepository.existsById(1)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(1);

        assertThatCode(() -> employeeService.deleteEmployee(1)).doesNotThrowAnyException();
        verify(employeeRepository).deleteById(1);
    }

    @Test
    @DisplayName("deleteEmployee:   EmployeeNotFoundException si introuvable")
    void deleteEmployee_whenNotFound_shouldThrow() {
        when(employeeRepository.existsById(99)).thenReturn(false);

        assertThatThrownBy(() -> employeeService.deleteEmployee(99))
                .isInstanceOf(EmployeeNotFoundException.class)
                .hasMessageContaining("99");
    }
}