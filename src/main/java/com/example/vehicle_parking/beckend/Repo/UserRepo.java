package com.example.vehicle_parking.beckend.Repo;

import com.example.vehicle_parking.beckend.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {

    List<User> findByFirstName(String firstName);
}
