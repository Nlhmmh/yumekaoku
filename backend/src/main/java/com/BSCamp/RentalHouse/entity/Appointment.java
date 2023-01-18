package com.BSCamp.RentalHouse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;


@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 225)
	private String message;
	
	@Column(nullable = false)
	private LocalDateTime appointmentDate;
	
//	@Column(columnDefinition = "ENUM('upcoming','progress','done','cancelled') NOT NULL")
//	@Enumerated(EnumType.STRING)
//	private AppointmentStatus status;

	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "estate_id", referencedColumnName = "id", nullable = false)
	private Estate estate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getUser() {
		return user;
	}

	public LocalDateTime getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(LocalDateTime appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

//	public AppointmentStatus getStatus() {
//		return status;
//	}
//
//	public void setStatus(AppointmentStatus status) {
//		this.status = status;
//	}

	public void setUser(User user) {
		this.user = user;
	}

	public Estate getEstate() {
		return estate;
	}

	public void setEstate(Estate estate) {
		this.estate = estate;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

}
