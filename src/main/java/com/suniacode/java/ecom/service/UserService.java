package com.suniacode.java.ecom.service;

import com.suniacode.java.ecom.repository.UserRepository;
import com.suniacode.java.ecom.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor //Alternative way to create constructor for final fields
public class UserService {
    private final UserRepository userRepository;
    //    private List<User> userList = new ArrayList<>();
    //    Using database instead of in-memory list
    private Long nextId = 1L;

    //    public UserService(UserRepository userRepository) {
    //        this.userRepository = userRepository;
    //    }
    //Alternative way add annotation @RequiredArgsConstructor from lombok

    public List<User> fetchAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        //  user.setId(nextId++);
        // using jpa to create id automatically
        //  userList.add(user);
        // using repository to save user
        userRepository.save(user);
    }

    public Optional<User> fetchUser(Long id) {
        //        return Optional.ofNullable(userList.stream().filter(user -> user.getId().equals(id))
        //                .findFirst()
        //                .orElse(null));
        // instead of list, using repository to fetch user by id
        return userRepository.findById(id);
    }

    //Alternative way
    //    public User fetchUser(Long id) {
    //        for (User user : userList) {
    //            if (user.getId().equals(id)) {
    //                return user;
    //            }
    //        }
    //        return null;
    //    }

    public boolean updateUser(Long id, User updatedUser) {
    //        return userList.stream().filter(user -> user.getId().equals(id))
    //                .findFirst()
    //                .map(existingUser -> {
    //                    existingUser.setFirstName(updatedUser.getFirstName());
    //                    existingUser.setLastName(updatedUser.getLastName());
    //                    return true;
    //                }).orElse(false);
        // Instead of list, using repository to update user
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setFirstName(updatedUser.getFirstName());
                    existingUser.setLastName(updatedUser.getLastName());
                    userRepository.save(existingUser);
                    return true;
                }).orElse(false);
    }
    // Alternative way to update user
    //    public boolean userUpdate(Long id, User updatedUser) {
    //        Optional<User> existingUserOpt = fetchUser(id);
    //        if (existingUserOpt.isPresent()) {
    //            User existingUser = existingUserOpt.get();
    //            existingUser.setFirstName(updatedUser.getFirstName());
    //            existingUser.setLastName(updatedUser.getLastName());
    //            return true;
    //        }
    //        return false;
    //    }
}
