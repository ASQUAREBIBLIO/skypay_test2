package com.example.skypay_test2.service;

import com.example.skypay_test2.model.Booking;
import com.example.skypay_test2.model.Room;
import com.example.skypay_test2.model.User;
import com.example.skypay_test2.repository.RoomRepository;
import com.example.skypay_test2.repository.UserRepository;
import com.example.skypay_test2.types.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {
    /**
     * For API
     * private final RoomRepository roomRepo;

    @Transactional
    public Room setRoom(int number, RoomType type, double price) {
        Room room = new Room();
        room.setRoomNumber(number);
        room.setRoomType(type);
        room.setPrice(price);
        return  roomRepo.save(room);
    }

    @Transactional
    public Room getRoom(int number) {
        return roomRepo.findByRoomNumber(number);
    }*/

    private final List<Room> rooms = new ArrayList<>();
    private Long nextId = 1L;

    public Room setRoom(RoomType type, Double price) {
        Room room = new Room();
        room.setId(nextId++);
        room.setRoomType(type);
        room.setPrice(price);
        rooms.add(room);
        return room;
    }

    public Room getRoomById(Long id) {
        return rooms.stream()
                .filter(r -> r.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Room not found"));
    }

    public boolean isRoomAvailable(Long id, LocalDate checkIn, LocalDate checkOut) {
        Room room = getRoomById(id);

        for (Booking booking : room.getBookings()) {
            boolean isTaken = !checkOut.isBefore(booking.getCheckIn()) &&
                    !checkIn.isAfter(booking.getCheckOut());
            if (isTaken) {
                return false;
            }
        }

        return true;
    }

    public List<Room> getAllRooms() {
        return new ArrayList<>(rooms);
    }
}
