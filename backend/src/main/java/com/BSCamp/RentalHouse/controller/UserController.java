package com.BSCamp.RentalHouse.controller;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.LoginRequest;
import com.BSCamp.RentalHouse.entity.LoginResponse;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.security.JwtTokenUtil;
import com.BSCamp.RentalHouse.entity.ChangePassword;
import com.BSCamp.RentalHouse.service.UserService;

@RestController
@RequestMapping("/api")
//@RequestMapping("/api/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authManager;

	@Autowired
	JwtTokenUtil jwtUtil;

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginReq) {
		User getUser = userService.checkLoginUser(loginReq.getEmail(), loginReq.getPassword());
		if (getUser == null) {
			return ResponseEntity.badRequest().build();
		} else {
			try {

				Authentication authentication = authManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginReq.getEmail(), loginReq.getPassword()));

				User user = (User) authentication.getPrincipal();
				String accessToken = jwtUtil.generateAccessToken(user);
				LoginResponse response = new LoginResponse(user.getEmail(), accessToken);

				return ResponseEntity.ok().body(response);

			} catch (BadCredentialsException ex) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		}
//	return ResponseEntity.ok().body(user);

	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		User createdUser = userService.create(user);
		if (createdUser == null) {
			return ResponseEntity.badRequest().body("User with same email already existed.");
		}
		return ResponseEntity.ok().body(createdUser);
	}

//	@PreAuthorize("hasAnyRole('admin','user')")
	@GetMapping("/profile")
	public ResponseEntity<User> getProfile(@RequestParam int id) {
		User user = userService.get(id);
		if (user == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(user);
	}

//	@PreAuthorize("hasAnyRole('admin','user')")
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

//	@PreAuthorize("hasAnyRole('admin','user')")
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
