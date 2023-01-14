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
//		appointment.setStatus(AppointmentStatus.upcoming);
		return repo.save(appointment);
	}

//	@Override
//	public Appointment updateStatus(int id, String status) {
//		Appointment toUpdateAppointment = this.get(id);
//		if (toUpdateAppointment == null) {
//			return null;
//		}
//		try {
//			toUpdateAppointment.setStatus(AppointmentStatus.valueOf(status));
//		} catch (IllegalArgumentException e) {
//			e.printStackTrace();
//			return null;
//		}
//		toUpdateAppointment.setUpdatedAt(LocalDateTime.now());
//		repo.save(toUpdateAppointment);
//		return toUpdateAppointment;
//	}

//	public Appointment update(int id, Appointment appointment) {
//		Appointment toUpdateAppointment = this.get(id);
//		if (toUpdateAppointment == null) {
//			return null;
//		}
//		toUpdateAppointment.setId(id);
//		toUpdateAppointment.setAppointmentDate((appointment.getAppointmentDate()));
//		toUpdateAppointment.setStatus(appointment.getStatus());
//		toUpdateAppointment.setMessage(appointment.getMessage());
//		toUpdateAppointment.setUpdatedAt(LocalDateTime.now());
//		repo.save(toUpdateAppointment);
//		return toUpdateAppointment;
//	}

//	@Override
//	public boolean delete(int id) {
//		Appointment appointment = this.get(id);
//		if (appointment == null) {
//			return false;
//		}
//		repo.deleteById(id);
//		return true;
//	}
}
