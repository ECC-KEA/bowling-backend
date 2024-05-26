package dk.ecc.bowlinghall.admin.schedule;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ScheduleController {

    private final ShiftService shiftService;

    public ScheduleController(ShiftService shiftService) {
        this.shiftService = shiftService;
    }

    @GetMapping("/schedule")
    public ResponseEntity<List<ShiftDTO>> getSchedule(
            @RequestParam(required = false) Integer day,
            @RequestParam(required = false) Integer month,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer limit
    ) {
        if(day == null) day = LocalDate.now().getDayOfMonth();
        if(month == null) month = LocalDate.now().getMonthValue();
        if(year == null) year = LocalDate.now().getYear();
        if(limit == null) limit = 7;
        return ResponseEntity.ok(shiftService.getShifts(LocalDateTime.of(year, month, day, 0, 0), limit));
    }

    @GetMapping("/schedule/{id}")
    public ResponseEntity<ShiftDTO> getShift(@PathVariable Long id) {
        return ResponseEntity.ok(shiftService.getShift(id));
    }

    @PostMapping("/schedule")
    public ResponseEntity<ShiftDTO> addShift(@RequestBody ShiftDTO shiftDTO) {
        var responseDTO = shiftService.createShift(shiftDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @PatchMapping("/schedule/{id}")
    public ResponseEntity<ShiftDTO> patchShift(@PathVariable Long id, @RequestBody ShiftDTO dto) {
        ShiftDTO responseDTO = shiftService.patchShift(id, dto);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/schedule/{id}")
    public ResponseEntity<Void> deleteShift(@PathVariable Long id) {
        shiftService.deleteShift(id);
        return ResponseEntity.noContent().build();
    }
}
