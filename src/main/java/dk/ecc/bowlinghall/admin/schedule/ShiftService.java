package dk.ecc.bowlinghall.admin.schedule;

import dk.ecc.bowlinghall.admin.employee.Employee;
import dk.ecc.bowlinghall.admin.employee.EmployeeService;
import dk.ecc.bowlinghall.error.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ShiftService {

    private final ShiftRepository shiftRepository;
    private final EmployeeService employeeService;

    public ShiftService(ShiftRepository shiftRepository, EmployeeService employeeService) {
        this.shiftRepository = shiftRepository;
        this.employeeService = employeeService;
    }

    public ShiftDTO toDTO(Shift shift) {
        Long employeeId = null;
        if (shift.getEmployee() != null) {
            employeeId = shift.getEmployee().getId();
        }
        return new ShiftDTO(
                shift.getId(),
                employeeId,
                shift.getStart(),
                shift.getEnd()
        );
    }

    public Shift fromDTO(ShiftDTO shiftDTO) {
        Employee employee = null;
        if (shiftDTO.employeeId() != null) {
            employee = employeeService.getById(shiftDTO.employeeId());
        }
        return new Shift(
                shiftDTO.id(),
                employee,
                shiftDTO.start(),
                shiftDTO.end()
        );
    }

    public List<ShiftDTO> getShifts(LocalDateTime fromDate, int limit){
        List<Shift> shifts = shiftRepository.findAllByStartBetween(fromDate, fromDate.plusDays(limit));
        return shifts.stream().map(this::toDTO).toList();
    }

    public ShiftDTO getShift(Long id) {
        return shiftRepository.findById(id).map(this::toDTO).orElseThrow(() -> new NotFoundException("Shift not found"));
    }

    public ShiftDTO createShift(ShiftDTO shiftDTO) {
        var shift = fromDTO(shiftDTO);
        return toDTO(shiftRepository.save(shift));
    }

    public ShiftDTO patchShift(Long id, ShiftDTO shiftDTO) {
        var shift = fromDTO(getShift(id));
        var start = shiftDTO.start();
        var end = shiftDTO.end();

        if (start != null && end != null) {
            shift.setStartAndEnd(start, end);
        } else if (start != null) {
            shift.setStart(start);
        } else if (end != null) {
            shift.setEnd(end);
        }

        return toDTO(shiftRepository.save(shift));
    }

    public void deleteShift(Long id) {
        shiftRepository.deleteById(id);
    }
}
