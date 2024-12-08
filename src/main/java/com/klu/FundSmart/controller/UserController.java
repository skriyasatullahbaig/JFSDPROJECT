package com.klu.FundSmart.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.klu.FundSmart.model.User;
import com.klu.FundSmart.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
public class UserController {

    @Autowired
    UserService userService;

    // User registration - Expecting a User object in JSON format
    @PostMapping("/userRegistration")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        // Register the user through the service
        userService.userRegistration(user);
        return ResponseEntity.ok("User registered successfully.");
    }

    // Check if a user exists by email or phone number
    @GetMapping("/checkUserExists")
    public boolean checkByEmailOrContact(@RequestParam String email, @RequestParam String phno) {
        return userService.checkByEmailOrContact(email, phno);
    }

    // User login - Expecting login details in JSON format
    @PostMapping("/userlogin")
    public ResponseEntity<User> checkUserLogin(@RequestBody Map<String, String> userLoginData, HttpSession session) {
        String usernameOrEmail = userLoginData.get("usernameOrEmail");
        String password = userLoginData.get("password");

        User user = userService.checkUserLogin(usernameOrEmail, password);

        if (user != null && user.getPassword().equals(password)) {
            session.setAttribute("user", user);
            session.setAttribute("userEmail", user.getEmail());
            session.setMaxInactiveInterval(30 * 60); // Timeout after 30 minutes
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

    // Get user details from session
    @GetMapping("/getUserDetails")
    public ResponseEntity<User> getUserDetails(HttpSession session) {
        session.setMaxInactiveInterval(30 * 10); // Extend session
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        return ResponseEntity.ok(user);
    }

    // Update user profile - Expecting updated user data in JSON format
    @PutMapping("/updateUserProfile")
    public ResponseEntity<String> updateUserProfile(@RequestBody User updatedUser, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Please log in to update your profile.");
        }

        // Update the user object with the new data
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setPhno(updatedUser.getPhno());
        user.setEmail(updatedUser.getEmail());
        user.setDob(updatedUser.getDob());
        user.setGender(updatedUser.getGender());
        user.setResidentialAddress(updatedUser.getResidentialAddress());
        user.setCorrespondenceAddress(updatedUser.getCorrespondenceAddress());

        userService.updateUserProfile(user.getId(), user); // Assuming this method handles the update.

        // Update session with the modified user object
        session.setAttribute("user", user);

        return ResponseEntity.ok("Profile updated successfully.");
    }

    // Logout user
    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate(); // Invalidating the session
        return ResponseEntity.ok("Logged out successfully.");
    }
}