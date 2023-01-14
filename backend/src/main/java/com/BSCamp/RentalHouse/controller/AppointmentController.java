package com.BSCamp.RentalHouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.service.AppointmentService;


@RestController
@RequestMapping("/api/user")
public class AppointmentController {
	@Autowired
	AppointmentService service;
	
	@GetMapping("/appointments")
	public List<Appointment> getAll() {
		return service.getAll();
	}
	
	@PostMapping("/appointments/add")
	public Appointment create(@RequestBody Appointment appointment) {
	
		return service.create(appointment);
	}
}

