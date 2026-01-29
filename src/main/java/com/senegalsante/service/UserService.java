package com.senegalsante.service;

import com.senegalsante.model.Profile;
import com.senegalsante.model.User;
import com.senegalsante.repository.ProfileRepository;
import com.senegalsante.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Transactional
    public User registerUser(String firstName, String lastName, String email, String phone, String password) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Cet email est déjà utilisé");
        }

        // Création de l'utilisateur
        User user = new User();
        user.setEmail(email);
        user.setPhoneNumber(phone);
        user.setPassword(password);
        user.setRole(User.Role.USER);
        user.setStatus(User.Status.ACTIVE);

        user = userRepository.save(user);

        // Création du profil principal
        Profile profile = new Profile();
        profile.setUser(user);
        profile.setFirstName(firstName);
        profile.setLastName(lastName);
        profile.setBirthDate(LocalDate.now());
        profile.setGender("AUTRE");
        profile.setMainProfile(true);

        profileRepository.save(profile);

        return user;
    }
}
