package com.BSCamp.RentalHouse.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.LoginRequest;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.entity.ChangePassword;
import com.BSCamp.RentalHouse.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<User> login(@Valid @RequestBody LoginRequest loginReq) {
		User user = userService.checkLoginUser(loginReq.getEmail(), loginReq.getPassword());
		if (user == null) {
			return ResponseEntity.badRequest().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		User createdUser = userService.create(user);
		if (createdUser == null) {
			return ResponseEntity.badRequest().body("User with same email already existed.");
		}
		return ResponseEntity.ok().body(createdUser);
	}

	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam int id) {
		User user = userService.get(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

	@PutMapping("/profile/update")
	public ResponseEntity<User> updateProfile(@Valid @RequestBody User user) {
		if (user.getId() <= 0) {
			return ResponseEntity.badRequest().build();
		}
		User updatedUser = userService.update(user.getId(), user);
		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedUser);
	}

	@PutMapping("/password/update")
	public ResponseEntity<Object> changePassword(@Valid @RequestBody ChangePassword changePwd) throws IOException {
		User user = userService.get(changePwd.getOri_id());
		if (user == null) {
			return new ResponseEntity<Object>(new Exception("Something wrong"), HttpStatus.CONFLICT);
		}
		if (!changePwd.getConfirmPassword().equals(changePwd.getNewPassword())) {
			return new ResponseEntity<Object>(new Exception("Confirm Password does not match"), HttpStatus.CONFLICT);
		}
		if (!passwordEncoder.matches(changePwd.getCurrentPassword(), user.getPassword())) {
			return new ResponseEntity<Object>(new Exception("Current Password does not match"), HttpStatus.CONFLICT);
		}
		userService.updatePassword(user.getId(), changePwd.getNewPassword());
		return ResponseEntity.ok().body(user);
	}

}
