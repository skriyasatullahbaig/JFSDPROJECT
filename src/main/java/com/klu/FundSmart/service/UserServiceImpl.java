package com.klu.FundSmart.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.klu.FundSmart.model.User;
import com.klu.FundSmart.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Override
    public String userRegistration(User user) {
        userRepository.save(user);
        return "User registered successfully...";
    }

    @Override
    public boolean checkByEmailOrContact(String email, String phno) {
        // Check if email or phone number already exists in the database
        return userRepository.findByEmail(email).isPresent() || userRepository.findByPhno(phno).isPresent();
    }

	@Override
	public void updateUserProfile(Long id, User user) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public User checkUserLogin(String usernameOrEmail, String password) {
		return userRepository.checkUserLogin(usernameOrEmail, usernameOrEmail, password);
	}

}