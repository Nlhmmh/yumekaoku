package com.BSCamp.RentalHouse.service;

import java.util.List;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.entity.AppointmentCreateRequest;
import com.BSCamp.RentalHouse.entity.User;

public interface AppointmentService {
	public List<Appointment> getAll();

	public List<Appointment> getByUser(User user);

	public Appointment get(int id);

	public Appointment create(Appointment appointment);

//	public Appointment updateStatus(int id, String status);

//	public Appointment update(int id, Appointment appointment);

//	public boolean delete(int id);

}
