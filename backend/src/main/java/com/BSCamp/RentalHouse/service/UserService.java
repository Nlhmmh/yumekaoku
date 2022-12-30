package com.BSCamp.RentalHouse.service;

import com.BSCamp.RentalHouse.entity.User;

import java.util.List;

public interface UserService {
	public User checkLoginUser(String email, String password);
	
	public List<User> getAll();

	public List<User> getByNameOrEmailOrPhoneNumber(String name, String email, String phoneNumber);

	public User get(int id);
	
	public User create(User user);

	public User update(int id, User user);
	
	public boolean delete(int id);
}
