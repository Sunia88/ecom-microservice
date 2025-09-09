package com.suniacode.java.ecom.controller;

import com.suniacode.java.ecom.model.User;
import com.suniacode.java.ecom.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    //    @GetMapping("/api/users")
    //    is short way from
    //    @RequestMapping(value = "/api/users", method = RequestMethod.GET)
    //    Alternative way
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.fetchAllUsers(), HttpStatus.OK);
        //Alternative way
        //return ResponseEntity.ok(userService.fetchAllUsers());
    }

    //    @GetMapping("/api/users/{id}")
    // is short way because RequestMapping in class level already has /api/users
    @GetMapping("/{id}")
    public ResponseEntity<Optional<User>> getUser(@PathVariable Long id) {
        Optional<User> user = userService.fetchUser(id);

        return user.map(value -> new ResponseEntity<>(Optional.of(value), HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    //    Alternative way
    //    @GetMapping("/api/users/{id}")
    //    public ResponseEntity<User> getUser(@PathVariable Long id) {
    //        if (userService.fetchUser(id) == null) {
    //            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    //        }
    //        return ResponseEntity.ok(userService.fetchUser(id));
    //    }

    //    @PostMapping("/api/users")
    //    is short way because RequestMapping in class level already has /api/users
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user) {
        userService.addUser(user);
        return ResponseEntity.ok("User added successfully");
    }

    //    @PutMapping("/api/users/{id}")
    //    is short way because RequestMapping in class level already has /api/users
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        boolean isUpdated = userService.updateUser(id, updatedUser);
        if (isUpdated) {
            return ResponseEntity.ok("User updated successfully");
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }
}
