package dk.ecc.bowlinghall.config;

import dk.ecc.bowlinghall.admin.inventory.InventoryItemDTO;
import dk.ecc.bowlinghall.admin.inventory.InventoryItemRepository;
import dk.ecc.bowlinghall.admin.inventory.InventoryItemService;
import dk.ecc.bowlinghall.booking.Status;
import dk.ecc.bowlinghall.booking.airhockey.*;
import dk.ecc.bowlinghall.booking.bowling.*;
import dk.ecc.bowlinghall.booking.dinner.*;
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

    public InitData(DinnerBookingRepository dinnerBookingRepository, BowlingBookingRepository bowlingBookingRepository, AirHockeyBookingRepository airHockeyBookingRepository, BowlingBookingService bowlingBookingService, AirHockeyBookingService airHockeyBookingService, DinnerBookingService dinnerBookingService, BowlingLaneRepository bowlingLaneRepository, AirHockeyTableRepository airHockeyTableRepository, RestaurantRepository restaurantRepository, InventoryItemRepository inventoryItemRepository, InventoryItemService inventoryItemService) {
        this.dinnerBookingRepository = dinnerBookingRepository;
        this.bowlingBookingRepository = bowlingBookingRepository;
        this.airHockeyBookingRepository = airHockeyBookingRepository;
        this.bowlingBookingService = bowlingBookingService;
        this.airHockeyBookingService = airHockeyBookingService;
        this.dinnerBookingService = dinnerBookingService;
        this.bowlingLaneRepository = bowlingLaneRepository;
        this.airHockeyTableRepository = airHockeyTableRepository;
        this.restaurantRepository = restaurantRepository;
        this.inventoryItemRepository = inventoryItemRepository;
        this.inventoryItemService = inventoryItemService;
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
        if (inventoryItemRepository.count() == 0) {
            createInventoryItems();
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
}