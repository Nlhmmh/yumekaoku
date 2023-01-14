package com.BSCamp.RentalHouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.entity.AppointmentCreateRequest;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.service.AppointmentService;
import com.BSCamp.RentalHouse.service.EstateService;
import com.BSCamp.RentalHouse.service.UserService;

@RestController
@RequestMapping("/api/user")
public class AppointmentController {
	@Autowired
	AppointmentService service;

	@Autowired
	UserService userService;

	@Autowired
	EstateService estateService;

	@GetMapping("/{userId}/appointments")
	public ResponseEntity<?> getAllByUser(@PathVariable("userId") int userId) {
		User user = userService.get(userId);
		if (user == null) {
			return ResponseEntity.badRequest().body("User ID invalid");
		}

		List<Appointment> appointments = service.getByUser(user);
		return ResponseEntity.ok().body(appointments);
	}

	@PostMapping("/appointments/add")
	public ResponseEntity<?> createAppointment(@RequestBody AppointmentCreateRequest request) {
		Appointment appointment = new Appointment();
		User user = userService.get(request.getUserId());
		if (user == null) {
			return ResponseEntity.badRequest().body("User ID invalid");
		}
		Estate estate = estateService.get(request.getEstateId());
		if (estate == null) {
			return ResponseEntity.badRequest().body("Estate ID invalid");
		}
		appointment.setAppointmentDate(request.getAppointmentDate());
		appointment.setMessage(request.getMessage());
		appointment.setUser(user);
		appointment.setEstate(estate);
		return ResponseEntity.ok().body(service.create(appointment));

	}
}
