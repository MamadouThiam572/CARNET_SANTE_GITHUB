
package com.senegalsante.controller;

import com.senegalsante.model.User;
import com.senegalsante.model.Profile;
import com.senegalsante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest request) {
        // Validation Java du numéro de téléphone
        if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {
            return ResponseEntity.badRequest().body("Ce numéro est déjà utilisé.");
        }

        User user = new User();
        user.setPhoneNumber(request.getPhoneNumber());
        user.setPasswordHash(request.getPassword()); // En production, utiliser BCrypt
        user.setEmail(request.getEmail());
        
        Profile profile = new Profile();
        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setBirthDate(request.getBirthDate());
        profile.setGender(request.getGender());
        profile.setMainProfile(true);
        profile.setUser(user);

        user.getProfiles().add(profile);
        userRepository.save(user);

        return ResponseEntity.ok("Inscription réussie !");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return userRepository.findByPhoneNumber(request.getPhoneNumber())
            .filter(u -> u.getPasswordHash().equals(request.getPassword()))
            .map(u -> ResponseEntity.ok("Bienvenue"))
            .orElse(ResponseEntity.status(401).body("Identifiants incorrects"));
    }
}
