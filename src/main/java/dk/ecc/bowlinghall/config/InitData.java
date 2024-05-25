package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.admin.employee.EmpType;
import dk.ecc.bowlinghall.admin.employee.Employee;
import dk.ecc.bowlinghall.admin.employee.EmployeeRepository;
import dk.ecc.bowlinghall.admin.schedule.Shift;
import dk.ecc.bowlinghall.admin.schedule.ShiftDTO;
import dk.ecc.bowlinghall.admin.schedule.ShiftRepository;
import dk.ecc.bowlinghall.admin.schedule.ShiftService;
import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.*;
import dk.ecc.bowlinghall.booking.bowling.*;
import dk.ecc.bowlinghall.booking.dinner.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.IntStream;


@Component
@Profile("!test")
public class InitData implements CommandLineRunner {

    private final DinnerBookingRepository dinnerBookingRepository;
    private final BowlingBookingRepository bowlingBookingRepository;
    private final AirHockeyBookingRepository airHockeyBookingRepository;
    private final BowlingBookingService bowlingBookingService;
    private final AirHockeyBookingService airHockeyBookingService;
    private final DinnerBookingService dinnerBookingService;
    private final BowlingLaneRepository bowlingLaneRepository;
    private final AirHockeyTableRepository airHockeyTableRepository;
    private final RestaurantRepository restaurantRepository;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingBookingService bowlingBookingService, AirHockeyBookingService airHockeyBookingService, DinnerBookingService dinnerBookingService, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository, RestaurantRepository restaurantRepository, EmployeeRepository employeeRepository, ShiftRepository shiftRepository, ShiftService shiftService) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.bowlingBookingService = bowlingBookingService;
        this.airHockeyBookingService = airHockeyBookingService;
        this.dinnerBookingService = dinnerBookingService;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airHockeyTableRepository = airHockeyTableRepository;
        this.restaurantRepository = restaurantRepository;
        this.employeeRepository = employeeRepository;
        this.shiftRepository = shiftRepository;
        this.shiftService = shiftService;
    }

    @Override
    public void run(String... args) throws Exception {
        if (bowlingLaneRepository.count() == 0) {
            IntStream.range(0, 24).mapToObj(i -> new BowlingLane(200, i < 4)).forEach(bowlingLaneRepository::save);
        }
        if (airHockeyTableRepository.count() == 0) {
            IntStream.range(0, 6).mapToObj(i -> new AirHockeyTable(50)).forEach(airHockeyTableRepository::save);
        }
        if (restaurantRepository.count() == 0) {
            restaurantRepository.save(new Restaurant(100));
        }
        if (bowlingBookingRepository.count() == 0) {
            createBowlingBookings();
        }
        if (airHockeyBookingRepository.count() == 0) {
            createAirHockeyBookings();
        }
        if (dinnerBookingRepository.count() == 0) {
            createDinnerBookings();
        }
        if (employeeRepository.count() == 0) {
            createEmployees();
        }
        if (shiftRepository.count() == 0) {
            createShifts();
        }

    }

    private void createEmployees() {
        var manager = new Employee(EmpType.MANAGER, "Jack", "Sparrow");
        var operator = new Employee(EmpType.OPERATOR, "Will", "Turner");

        var cleaning1 = new Employee(EmpType.CLEANING, "James", "Bond");
        var cleaning2 = new Employee(EmpType.CLEANING, "Money", "Penny");

        var employee0 = new Employee(EmpType.REGULAR, "Alice", "Johnson");
        var employee1 = new Employee(EmpType.REGULAR, "Bob", "Smith");
        var employee2 = new Employee(EmpType.REGULAR, "Carol", "Jones");
        var employee3 = new Employee(EmpType.REGULAR, "Dave", "Brown");
        var employee4 = new Employee(EmpType.REGULAR, "Eve", "White");
        var employee5 = new Employee(EmpType.REGULAR, "Frank", "Green");
        var employee6 = new Employee(EmpType.REGULAR, "Grace", "Black");
        var employee7 = new Employee(EmpType.REGULAR, "Henry", "Blue");

        employeeRepository.saveAll(List.of(manager, operator, cleaning1, cleaning2, employee0, employee1, employee2, employee3, employee4, employee5, employee6, employee7));
    }

    private void createShifts() {
        var manager = employeeRepository.findByFirstName("Jack").orElseThrow();
        var operator = employeeRepository.findByFirstName("Will").orElseThrow();

        var cleaning1 = employeeRepository.findByFirstName("James").orElseThrow();
        var cleaning2 = employeeRepository.findByFirstName("Money").orElseThrow();

        var employee1 = employeeRepository.findByFirstName("Alice").orElseThrow();
        var employee2 = employeeRepository.findByFirstName("Bob").orElseThrow();
        var employee3 = employeeRepository.findByFirstName("Carol").orElseThrow();
        var employee4 = employeeRepository.findByFirstName("Dave").orElseThrow();
        var employee5 = employeeRepository.findByFirstName("Eve").orElseThrow();
        var employee6 = employeeRepository.findByFirstName("Frank").orElseThrow();
        var employee7 = employeeRepository.findByFirstName("Grace").orElseThrow();
        var employee8 = employeeRepository.findByFirstName("Henry").orElseThrow();

        var dayShiftEmployees = List.of(manager, operator, cleaning1, employee1, employee3, employee5, employee7);
        var nightShiftEmployees = List.of(cleaning2, employee2, employee4, employee6, employee8);

        for(var emp : dayShiftEmployees) {
            for(int i = 1; i < 14; i++) {
                var start = LocalDateTime.now().plusDays(i).withHour(8).withMinute(0);
                var end = LocalDateTime.now().plusDays(i).withHour(16).withMinute(0);
                shiftService.createShift(new ShiftDTO(null, emp.getId(), start, end));
            }
        }

        for(var emp : nightShiftEmployees) {
            for(int i = 0; i < 14; i++) {
                var start = LocalDateTime.now().plusDays(i).withHour(14).withMinute(0);
                var end = LocalDateTime.now().plusDays(i).withHour(22).withMinute(0);
                shiftService.createShift(new ShiftDTO(null, emp.getId(), start, end));
            }
        }
    }

    private void createBowlingBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<BowlingBookingDTO> bookings = List.of(
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED, false),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED, true),
                new BowlingBookingDTO(null, null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED, false)
        );
        bookings.forEach(bowlingBookingService::addBowlingBooking);
    }

    private void createAirHockeyBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<AirHockeyBookingDTO> bookings = List.of(
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED),
                new AirHockeyBookingDTO(null, null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED)
        );
        bookings.forEach(airHockeyBookingService::addAirHockeyBooking);
    }

    private void createDinnerBookings() {
        var tomorrow = LocalDateTime.now().plusDays(1);
        var nextWeek = LocalDateTime.now().plusWeeks(1);

        List<DinnerBookingDTO> bookings = List.of(
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(10), tomorrow.withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(11), tomorrow.withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(12), tomorrow.withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(13), tomorrow.withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(14), tomorrow.withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(15), tomorrow.withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(16), tomorrow.withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(17), tomorrow.withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(18), tomorrow.withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", tomorrow.withHour(19), tomorrow.withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(10), nextWeek.withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(11), nextWeek.withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(12), nextWeek.withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(13), nextWeek.withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(14), nextWeek.withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(15), nextWeek.withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(16), nextWeek.withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(17), nextWeek.withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(18), nextWeek.withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.withHour(19), nextWeek.withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(10), nextWeek.plusDays(1).withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(11), nextWeek.plusDays(1).withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(12), nextWeek.plusDays(1).withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(13), nextWeek.plusDays(1).withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(14), nextWeek.plusDays(1).withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(15), nextWeek.plusDays(1).withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(16), nextWeek.plusDays(1).withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(17), nextWeek.plusDays(1).withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(18), nextWeek.plusDays(1).withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(1).withHour(19), nextWeek.plusDays(1).withHour(20), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(10), nextWeek.plusDays(2).withHour(11), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(11), nextWeek.plusDays(2).withHour(12), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(12), nextWeek.plusDays(2).withHour(13), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(13), nextWeek.plusDays(2).withHour(14), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(14), nextWeek.plusDays(2).withHour(15), Status.BOOKED, 10),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(15), nextWeek.plusDays(2).withHour(16), Status.BOOKED, 2),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(16), nextWeek.plusDays(2).withHour(17), Status.BOOKED, 4),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(17), nextWeek.plusDays(2).withHour(18), Status.BOOKED, 6),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(18), nextWeek.plusDays(2).withHour(19), Status.BOOKED, 8),
                new DinnerBookingDTO(null, "email@test.t", nextWeek.plusDays(2).withHour(19), nextWeek.plusDays(2).withHour(20), Status.BOOKED, 10)
        );
        bookings.forEach(dinnerBookingService::create);
    }
}