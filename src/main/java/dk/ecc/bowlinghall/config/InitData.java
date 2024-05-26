package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.admin.inventory.InventoryItemDTO;
import dk.ecc.bowlinghall.admin.inventory.InventoryItemRepository;
import dk.ecc.bowlinghall.admin.inventory.InventoryItemService;
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
import dk.ecc.bowlinghall.pos.product.SaleProduct;
import dk.ecc.bowlinghall.pos.product.SaleProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
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
    private final InventoryItemRepository inventoryItemRepository;
    private final InventoryItemService inventoryItemService;
    private final EmployeeRepository employeeRepository;
    private final ShiftRepository shiftRepository;
    private final ShiftService shiftService;
    private final SaleProductRepository saleProductRepository;

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingBookingService bowlingBookingService, AirHockeyBookingService airHockeyBookingService, DinnerBookingService dinnerBookingService, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository, RestaurantRepository restaurantRepository, InventoryItemRepository inventoryItemRepository, InventoryItemService inventoryItemService, EmployeeRepository employeeRepository, ShiftRepository shiftRepository, ShiftService shiftService, SaleProductRepository saleProductRepository) {
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
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemService = inventoryItemService;
        this.saleProductRepository = saleProductRepository;
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
        if (saleProductRepository.count() == 0) {
            createSaleProducts();
        }
        if (inventoryItemRepository.count() == 0) {
            createInventoryItems();
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
        LocalDateTime baseDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        for (int day = 0; day < 30; day++) {
            LocalDateTime date = baseDate.plusDays(day + 1);

            for (int hour = 0; hour < 10; hour++) {
                var startHour = 10 + hour;
                var endHour = startHour + 1;
                var childFriendly = hour < 4;

                var booking = new BowlingBookingDTO(null, null, "email@test.t", date.withHour(startHour), date.withHour(endHour), Status.BOOKED, childFriendly);
                bowlingBookingService.addBowlingBooking(booking);
            }
        }
    }

    private void createAirHockeyBookings() {
        LocalDateTime baseDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        for (int day = 0; day < 30; day++) {
            LocalDateTime date = baseDate.plusDays(day + 1);

            for (int hour = 0; hour < 10; hour++) {
                var startHour = 10 + hour;
                var endHour = startHour + 1;

                var booking = new AirHockeyBookingDTO(null, null, "email@test.t", date.withHour(startHour), date.withHour(endHour), Status.BOOKED);
                airHockeyBookingService.addAirHockeyBooking(booking);
            }
        }
    }

    private void createDinnerBookings() {
        LocalDateTime baseDate = LocalDateTime.now().truncatedTo(ChronoUnit.HOURS);

        for (int day = 0; day < 30; day++) {
            LocalDateTime date = baseDate.plusDays(day + 1);

            for (int hour = 0; hour < 10; hour++) {
                var startHour = 10 + hour;
                var endHour = startHour + 1;

                var booking = new DinnerBookingDTO(null, "email@test.t", date.withHour(startHour), date.withHour(endHour), Status.BOOKED, 4);
                dinnerBookingService.addDinnerBooking(booking);
            }
        }
    }

    private void createInventoryItems() {
        List<InventoryItemDTO> inventoryItems = new ArrayList<>() {{
            add(new InventoryItemDTO(null, "Air hockey puck", 50));
            add(new InventoryItemDTO(null, "Air hockey mallet", 50));
            add(new InventoryItemDTO(null, "Bowling pin", 400));
            add(new InventoryItemDTO(null, "Broom", 4));
            add(new InventoryItemDTO(null, "Vacuum", 2));
            add(new InventoryItemDTO(null, "Mop", 2));
            add(new InventoryItemDTO(null, "Bucket", 2));
            add(new InventoryItemDTO(null, "Universal cleaner", 15));
            add(new InventoryItemDTO(null, "Floor wax", 5));
        }};

        IntStream.rangeClosed(20, 50).forEach(size -> inventoryItems.add(new InventoryItemDTO(null, "Bowling shoes size " + size, 50)));
        IntStream.rangeClosed(6, 16).forEach(size -> inventoryItems.add(new InventoryItemDTO(null, "Bowling ball size " + size, 60)));

        inventoryItems.forEach(inventoryItemService::addInventoryItem);
    }

    private void createSaleProducts() {
        saleProductRepository.save(new SaleProduct("Coca Cola", 20));
        saleProductRepository.save(new SaleProduct("Fanta", 20));
        saleProductRepository.save(new SaleProduct("Sprite", 20));
        saleProductRepository.save(new SaleProduct("Pepsi", 20));
        saleProductRepository.save(new SaleProduct("7up", 20));
        saleProductRepository.save(new SaleProduct("Mountain Dew", 20));
        saleProductRepository.save(new SaleProduct("Red Bull", 30));
        saleProductRepository.save(new SaleProduct("Monster", 30));
        saleProductRepository.save(new SaleProduct("Rockstar", 30));
        saleProductRepository.save(new SaleProduct("Burn", 30));
        saleProductRepository.save(new SaleProduct("Battery", 30));
        saleProductRepository.save(new SaleProduct("Cult", 30));
        saleProductRepository.save(new SaleProduct("Carlsberg", 25));
        saleProductRepository.save(new SaleProduct("Tuborg", 25));
        saleProductRepository.save(new SaleProduct("Heineken", 25));
        saleProductRepository.save(new SaleProduct("Corona", 25));
        saleProductRepository.save(new SaleProduct("Guinness", 25));
        saleProductRepository.save(new SaleProduct("Budweiser", 25));
        saleProductRepository.save(new SaleProduct("Jack Daniels", 40));
        saleProductRepository.save(new SaleProduct("Jim Beam", 40));
        saleProductRepository.save(new SaleProduct("Johnnie Walker", 40));
        saleProductRepository.save(new SaleProduct("Jameson", 40));
        saleProductRepository.save(new SaleProduct("Glenfiddich", 40));
        saleProductRepository.save(new SaleProduct("Chivas Regal", 40));
    }
}