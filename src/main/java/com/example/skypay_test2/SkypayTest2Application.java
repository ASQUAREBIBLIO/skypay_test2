package com.example.skypay_test2;

import com.example.skypay_test2.model.Booking;
import com.example.skypay_test2.model.Room;
import com.example.skypay_test2.model.User;
import com.example.skypay_test2.service.BookingService;
import com.example.skypay_test2.service.RoomService;
import com.example.skypay_test2.service.UserService;
import com.example.skypay_test2.types.RoomType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class SkypayTest2Application {

	public static void main(String[] args) {
		SpringApplication.run(SkypayTest2Application.class, args);
	}

	@Bean
	public CommandLineRunner run(UserService userService, RoomService roomService, BookingService bookingService) {
		return args -> {
			System.out.println("\n*** SKYPAY TEST 2 - HOTEL BOOKING SYSTEM ***\n");

			// Create Rooms
			System.out.println("Creating rooms...");
			Room room1 = roomService.setRoom(RoomType.STANDARD, 1000.0);
			Room room2 = roomService.setRoom(RoomType.JUNIOR, 2000.0);
			Room room3 = roomService.setRoom(RoomType.MASTER, 3000.0);

			// Create Users
			System.out.println("Creating users...");
			User user1 = userService.setUser("John Doe", 5000.0);
			User user2 = userService.setUser("Jane Smith", 10000.0);

			// Make Bookings
			System.out.println("Making bookings...\n");

			// Booking 1: User 1 tries booking Room 2 from 30/06/2026 to 07/07/2026 (7 nights)
			// return: No enough funds
			System.out.println("Booking 1:");
			System.out.println("User: " + user1.getName() + " | Balance: $" + user1.getBalance());
			Booking booking1 = bookingService.bookRoom(
					user1.getId(),
					room2.getId(),
					LocalDate.of(2026, 6, 30),
					LocalDate.of(2026, 7, 7)
			);

			// Booking 2: User 1 tries booking Room 2 from 07/07/2026 to 30/06/2026
			// return: Invalid check-in/check-out dates
			System.out.println("Booking 2:");
			System.out.println("User: " + user1.getName() + " | Balance: $" + user1.getBalance());
			Booking booking2 = bookingService.bookRoom(
					user1.getId(),
					room2.getId(),
					LocalDate.of(2026, 7, 7),
					LocalDate.of(2026, 6, 30)
			);

			// Booking 3: User 1 tries booking Room 1 from 07/07/2026 to 08/07/2026 (1 night)
			// return: Successfully booked
			System.out.println("Booking 3:");
			System.out.println("User: " + user1.getName() + " | Balance: $" + user1.getBalance());
			Booking booking3 = bookingService.bookRoom(
					user1.getId(),
					room1.getId(),
					LocalDate.of(2026, 7, 7),
					LocalDate.of(2026, 7, 8)
			);

			// Booking 4: User 2 tries booking Room 1 from 07/07/2026 to 09/07/2026 (2 nights)
			// return: Room not available
			System.out.println("Booking 4:");
			System.out.println("User: " + user2.getName() + " | Balance: $" + user2.getBalance());
			Booking booking4 = bookingService.bookRoom(
					user2.getId(),
					room1.getId(),
					LocalDate.of(2026, 7, 7),
					LocalDate.of(2026, 7, 9)
			);

			// Booking 5: User 2 tries booking Room 3 from 07/07/2026 to 08/07/2026 (1 night)
			// return: Successfully booked
			System.out.println("Booking 5:");
			System.out.println("User: " + user2.getName() + " | Balance: $" + user2.getBalance());
			Booking booking5 = bookingService.bookRoom(
					user2.getId(),
					room3.getId(),
					LocalDate.of(2026, 7, 7),
					LocalDate.of(2026, 7, 8)
			);

			bookingService.printAll();
		};
	}

}
