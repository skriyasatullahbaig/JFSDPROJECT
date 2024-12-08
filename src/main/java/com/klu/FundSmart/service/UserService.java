package com.klu.FundSmart.service;

import com.klu.FundSmart.model.User;

public interface UserService {

	public String userRegistration(User user);

	public User checkUserLogin(String usernameOrEmail, String password);

	public void updateUserProfile(Long id, User user);

	public boolean checkByEmailOrContact(String email, String phno);


}