package com.finflow.backend.Service;

import com.finflow.backend.Model.User;
import com.finflow.backend.Repository.UserRepository;
import jakarta.persistence.Access;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Integer id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

//    public User updateUser(Integer id, User updatedUser) {
//        return userRepository.findById(id)
//                .map(user -> {
//                    user.setFirstname(updatedUser.getFirstname());
//                    user.setLastname(updatedUser.getLastname());
//                    user.setDateOfBirth(updatedUser.getDateOfBirth());
//                    user.setEmail(updatedUser.getEmail());
//                    user.setPassword(updatedUser.getPassword()); // Ideally encode password
//                    user.setAccountLocked(updatedUser.isAccountLocked());
//                    user.setEnabled(updatedUser.isEnabled());
//                    user.setRoles(updatedUser.getRoles());
//                    return userRepository.save(user);
//                })
//                .orElseThrow(() -> new RuntimeException("User not found with id " + id));
//    }

//    public void deleteUser(Integer id) {
//        userRepository.deleteById(id);
//    }
}

