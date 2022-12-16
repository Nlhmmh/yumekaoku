package com.BSCamp.RentalHouse.service;

import java.time.LocalDateTime;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.entity.UserRole;
import com.BSCamp.RentalHouse.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

//	@Override
//	public List<User> getByNameOrEmailOrPhoneNumber(String filter) {
//		// TODO Auto-generated method stub
//		return userRepo.findByNameOrEmailOrPhoneNumber(filter);
//	}

	@Override
	public User create(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(LocalDateTime.now());
		return userRepo.save(user);
	}

}
