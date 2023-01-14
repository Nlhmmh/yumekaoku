package com.BSCamp.RentalHouse.service;

import java.time.LocalDateTime;
import java.util.List;

//import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.entity.UserStatus;
import com.BSCamp.RentalHouse.repository.UserRepo;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Override
	public User checkLoginUser(String email, String password) {
		User user = userRepo.findByEmail(email);
		if (user == null) {
			return null;
		}
		if (!passwordEncoder.matches(password, user.getPassword())) {
			return null;
		}
		return userRepo.save(user);
	}

	@Override
	public List<User> getAll() {
		return userRepo.findAll();
	}

	@Override
	public List<User> getByNameOrEmailOrPhoneNumber(String name, String email, String phoneNumber) {
		return userRepo.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCaseOrPhoneNumberContainingIgnoreCase(name,
				email, phoneNumber);
	}

	@Override
	public User get(int id) {
		return userRepo.findById(id).orElse(null);
	}

	@Override
	public User create(User user) {
		User createdUser = userRepo.findByEmail(user.getEmail());
		if (createdUser != null) {
			return null;
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setCreatedAt(LocalDateTime.now());
		user.setStatus(UserStatus.active);
		user.setRole(user.getRole());
		return userRepo.save(user);
	}

	@Override
	public User update(int id, User user) {
		User toUpdateUser = this.get(id);
		if (toUpdateUser == null) {
			return null;
		}
		toUpdateUser.setName(user.getName());
		toUpdateUser.setEmail(user.getEmail());
		toUpdateUser.setPhoneNumber(user.getPhoneNumber());
		toUpdateUser.setUpdatedAt(LocalDateTime.now());
		userRepo.save(toUpdateUser);
		return toUpdateUser;
	}

	@Override
	public boolean delete(int id) {
		User user = this.get(id);
		if (user == null) {
			return false;
		}
		userRepo.deleteById(id);
		return true;
	}

	@Override
	public void updatePassword(int id, String newPassword) {
		User toUpdateUser = this.get(id);
		if (toUpdateUser != null) {
			toUpdateUser.setPassword(passwordEncoder.encode(newPassword));
			userRepo.save(toUpdateUser);
			System.out.println("User password updated");
		}
	}

}
