package com.example.mongodemo.controller;

import com.example.mongodemo.model.User;
import com.example.mongodemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ApiController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping(value = "/")
    public String getPage(){
        return "Welcome";
    }

    @GetMapping(value = "/users")
    public List<User> getUsers(){
        return userRepo.findAll();
    }

    @PostMapping(value = "/save")
    public String saveUser(@RequestBody User user){
        userRepo.save(user);
        return "Saved...";
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User updatedUser){
        Optional<User> userData = userRepo.findById(id);

        if (userData.isPresent()) {
            User user = userData.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setEmail(updatedUser.getEmail());
            return new ResponseEntity<>(userRepo.save(user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/delete/{id}")
    public String deleteUser(@PathVariable String id){
        Optional<User> userData = userRepo.findById(id);

        if (userData.isPresent()){
            User deleteUser = userRepo.findById(id).get();
            userRepo.delete(deleteUser);
            return "Deleted user with id: " + id;
        } else {
            return "User does not exist";
        }
    }

}
