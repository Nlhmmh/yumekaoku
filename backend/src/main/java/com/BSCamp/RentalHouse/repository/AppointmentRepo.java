package com.BSCamp.RentalHouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.entity.AppointmentCreateRequest;
import com.BSCamp.RentalHouse.entity.User;

@EnableJpaRepositories
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {

	List<Appointment> findByUser(User user);

//	public List<Appointment> findByAppointment(Appointment appointment);
}