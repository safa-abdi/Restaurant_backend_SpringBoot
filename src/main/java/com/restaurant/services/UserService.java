package com.restaurant.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.restaurant.Repositories.UserRepository;
import com.restaurant.entities.User;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;
 
	
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById1(Long userId) {
        return userRepository.findById(userId);
    }

    public User addUser(User user) {
    	 String encodedPassword = passwordEncoder.encode(user.getPassword());
         user.setPassword(encodedPassword);
         return userRepository.save(user);
    }
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public User modifUser(User user) {
        User existingUser = userRepository.findById(user.getId()).orElse(null);

        if (existingUser != null) {
            existingUser.setEmail(user.getEmail());
            existingUser.setFirstname(user.getFirstname());
            existingUser.setLastname(user.getLastname());
            existingUser.setRole(user.getRole());
            existingUser.setPhone(user.getPhone());

            // Si un mot de passe est fourni, on le hache avant de le sauvegarder
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                existingUser.setPassword(hashedPassword);
            }

            return userRepository.save(existingUser);
        }

        return user;
    }

	
}
