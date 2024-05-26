package dk.ecc.bowlinghall.admin.employee;

import dk.ecc.bowlinghall.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getEmpType(),
                employee.getFirstName(),
                employee.getLastName()
        );
    }

    public Employee fromDTO(EmployeeDTO employeeDTO) {
        return new Employee(
                employeeDTO.empType(),
                employeeDTO.firstName(),
                employeeDTO.lastName()
        );
    }

    public Employee getById(Long id) {
        return employeeRepository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    public List<EmployeeDTO> getEmployees() {
        return employeeRepository.findAll().stream().map(this::toDTO).toList();
    }
}
