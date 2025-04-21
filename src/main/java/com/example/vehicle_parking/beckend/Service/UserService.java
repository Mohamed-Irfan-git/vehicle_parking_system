package com.example.vehicle_parking.beckend.Service;

import com.example.vehicle_parking.beckend.Model.User;
import com.example.vehicle_parking.beckend.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

     @Autowired
    private UserRepo userRepo;

     public List<User> findAll() {
         return userRepo.findAll();
     }
     public User findById(int id) {
         return userRepo.findById(id).get();
     }
     public void save(User user) {
         userRepo.save(user);

     }
     public void delete(User user) {
         userRepo.delete(user);
     }

     public void delete(int id){
         userRepo.deleteById(id);
     }

     public List<User> findByFirstname(String FirstName) {
         return userRepo.findByFirstName(FirstName);
     }

    public void updateUser(User user) {
         userRepo.save(user);
    }
}
