package com.rmp.backend.auth;



import com.rmp.backend.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    SecurityUtils securityUtils;
    Map<String,String> usersTempMap = new HashMap<>();

    @PostMapping("/api/register")
    public ResponseEntity<String> registerUser(@RequestBody User newUser) {
        // Check if the email is already registered
        String existingPassword = usersTempMap.get(newUser.getEmail());
        if (existingPassword != null) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        // Register the new user
        usersTempMap.put(newUser.getEmail(), newUser.getPassword());

        // Optional: Encrypt password, generate user ID, and store in Firestore
        // newUser.setUserId(UUID.randomUUID().toString());
        // newUser.setPassword(securityUtils.encryptPassword(newUser.getPassword()));
        // firestoreService.addUser(newUser);

        return ResponseEntity.ok("User registered successfully");
    }


    @PostMapping("/api/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        // Check if the email exists in the hashmap
        if (!usersTempMap.containsKey(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }

        // Retrieve the stored password for the email
        String storedPassword = usersTempMap.get(user.getEmail());

        // Validate the provided password with the stored password
        if (storedPassword.equals(user.getPassword())) {
            return ResponseEntity.ok("User logged in successfully");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

}
