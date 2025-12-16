package com.example.skypay_test2.repository;

import com.example.skypay_test2.model.Room;
import com.example.skypay_test2.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    public Room findByRoomNumber(int number);
}
