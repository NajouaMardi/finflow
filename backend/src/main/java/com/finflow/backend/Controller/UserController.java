package com.finflow.backend.Controller;

import com.finflow.backend.Model.User;
import com.finflow.backend.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

//    @PostMapping
//    public ResponseEntity<User> createUser(@RequestBody User user) {
//        User created = userService.createUser(user);
//        return ResponseEntity.ok(created);
//    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.findByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@PathVariable Integer id, @RequestBody User user) {
//        try {
//            User updated = userService.updateUser(id, user);
//            return ResponseEntity.ok(updated);
//        } catch (RuntimeException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
//        userService.deleteUser(id);
//        return ResponseEntity.noContent().build();
//    }
}
