package com.example.skypay_test2.service;

import com.example.skypay_test2.model.Booking;
import com.example.skypay_test2.model.Room;
import com.example.skypay_test2.model.User;
import com.example.skypay_test2.repository.BookingRepository;
import com.example.skypay_test2.repository.RoomRepository;
import com.example.skypay_test2.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {
    /**
     * For API
     *
    private final BookingRepository bookingRepo;
    private final UserService userService;
    private final RoomService roomService;

    @Transactional
    public Booking bookRoom(
            Long userId,
            int roomNumber,
            LocalDateTime checkIn,
            LocalDateTime checkOut
    ) {
        Booking booking = new Booking();
        User user = userService.getUserById(userId);
        Room room = roomService.getRoom(roomNumber);

        /* Validate dates *
        if (checkIn.isAfter(checkOut)) {
            throw new RuntimeException("Invalid CheckIn date !");
        }

        /* Check balance *
        int nightsNumber = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double bookingPrice = room.getPrice() * nightsNumber;
        double userBalance = user.getBalance();
        if (userBalance < bookingPrice) {
            throw new RuntimeException("Insufficient balance !");
        }

        userService.updateBalance(userId, userBalance - bookingPrice);

        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setUser(user);
        booking.setRoom(room);
        booking.setTotalPrice(bookingPrice);

        return bookingRepo.save(booking);
    }

    @Transactional
    public void printAll() {
        List<Booking> bookings = bookingRepo.findAll();

        System.out.println("ALL BOOKINGS");

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println("\nBooking ID: " + booking.getId());
                System.out.println("User: " + booking.getUser().getName() + " (ID: " + booking.getUser().getId() + ")");
                System.out.println("Room: " + booking.getRoom().getRoomType() + " (N°: " + booking.getRoom().getRoomNumber() + ")");
                System.out.println("Check-in: " + booking.getCheckIn());
                System.out.println("Check-out: " + booking.getCheckOut());
                System.out.println("Room price per night: $" + booking.getRoom().getPrice());
                System.out.println("Total price: $" + booking.getTotalPrice());
                System.out.println("-".repeat(100));
            }
        }

        System.out.println("=".repeat(100));
    }*/

    private final List<Booking> bookings = new ArrayList<>();
    private Long nextId = 1L;

    private final UserService userService;
    private final RoomService roomService;

    public BookingService(UserService userService, RoomService roomService) {
        this.userService = userService;
        this.roomService = roomService;
    }

    public Booking bookRoom(
            Long userId,
            Long roomId,
            LocalDate checkIn,
            LocalDate checkOut
    ) {
        Booking booking = new Booking();
        User user = userService.getUserById(userId);
        Room room = roomService.getRoomById(roomId);

        // Validate dates
        if (checkIn.isAfter(checkOut) || checkIn.isBefore(LocalDate.now())) {
            System.out.println("Error: Invalid dates!");
            return null;
        }

        int nightsNumber = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double bookingPrice = room.getPrice() * nightsNumber;
        double userBalance = user.getBalance();

        // Check balance
        if (userBalance < bookingPrice) {
            System.out.println("Sorry! No enough funds.");
            return null;
        }

        // Check if room is available
        if (!roomService.isRoomAvailable(roomId, checkIn, checkOut)) {
            System.out.println("Sorry! Room is not available.");
            return null;
        }

        userService.updateBalance(userId, userBalance - bookingPrice);

        booking.setId(nextId++);
        booking.setCheckIn(checkIn);
        booking.setCheckOut(checkOut);
        booking.setTotalPrice(bookingPrice);
        booking.setUser(user);
        booking.setRoom(room);

        bookings.add(booking);

        user.setBookings(bookings);
        room.setBookings(bookings);

        return booking;
    }

    public Booking getBookingById(Long id) {
        return bookings.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Booking not found"));
    }

    public void printAll() {
        System.out.println("=".repeat(100));
        System.out.println("ALL BOOKINGS");
        System.out.println("=".repeat(100));

        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            for (Booking booking : bookings) {
                System.out.println("\nBooking ID: " + booking.getId());
                System.out.println("User: " + booking.getUser().getName() + " (ID: " + booking.getUser().getId() + ")");
                System.out.println("Room: " + booking.getRoom().getRoomType() + " (N°: " + booking.getRoom().getId() + ")");
                System.out.println("Check-in: " + booking.getCheckIn());
                System.out.println("Check-out: " + booking.getCheckOut());
                System.out.println("Room price per night: $" + booking.getRoom().getPrice());
                System.out.println("Total price: $" + booking.getTotalPrice());
                System.out.println("-".repeat(100));
            }
        }
    }

    public List<Booking> getAllBookings() {
        return new ArrayList<>(bookings);
    }
}
