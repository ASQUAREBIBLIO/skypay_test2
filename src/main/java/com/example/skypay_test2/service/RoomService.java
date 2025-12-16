package com.example.skypay_test2.service;

import com.example.skypay_test2.model.Room;
import com.example.skypay_test2.model.User;
import com.example.skypay_test2.repository.RoomRepository;
import com.example.skypay_test2.repository.UserRepository;
import com.example.skypay_test2.types.RoomType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RoomService {
    private final RoomRepository roomRepo;

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
    }
}
