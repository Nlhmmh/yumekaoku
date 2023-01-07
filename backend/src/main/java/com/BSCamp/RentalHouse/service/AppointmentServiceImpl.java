package com.BSCamp.RentalHouse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.repository.AppointmentRepo;

@Service
public class AppointmentServiceImpl implements AppointmentService {
	@Autowired
	AppointmentRepo repo;

	@Override
	public List<Appointment> getAll() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	@Override
	public List<Appointment> getByUser(User user) {
		return repo.findByUser(user);
	}

	@Override
	public Appointment get(int id) {
		return repo.findById(id).orElse(null);
	}

	@Override
	public Appointment create(Appointment appointment) {
		appointment.setCreatedAt(LocalDateTime.now());
		return repo.save(appointment);
	}

	public Appointment update(int id, Appointment appointment) {
		Appointment toUpdateAppointment = this.get(id);
		if (toUpdateAppointment == null) {
			return null;
		}
		toUpdateAppointment.setId(id);
		toUpdateAppointment.setMessage(appointment.getMessage());
		toUpdateAppointment.setUpdatedAt(LocalDateTime.now());
		repo.save(toUpdateAppointment);
		return toUpdateAppointment;
	}

	@Override
	public boolean delete(int id) {
		Appointment appointment = this.get(id);
		if (appointment == null) {
			return false;
		}
		repo.deleteById(id);
		return true;
	}
}
