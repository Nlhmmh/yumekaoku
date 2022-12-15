package com.BSCamp.RentalHouse.service;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;
	
//	@Override
//	public List<User> getByNameOrEmailOrPhoneNumber(String filter) {
//		// TODO Auto-generated method stub
//		return userRepo.findByNameOrEmailOrPhoneNumber(filter);
//	}

}
