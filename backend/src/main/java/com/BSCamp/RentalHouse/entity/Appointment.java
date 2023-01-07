package com.BSCamp.RentalHouse.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
@Entity
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 225)
	private String message;
	
	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	

	private LocalDateTime updatedAt;
	
	@Column(length = 20)
	private String appointment_date;
	
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

//	public void setUpdatedAt(LocalDateTime updatedAt) {
//		this.updatedAt = updatedAt;
//	}

	public String getAppointment_date() {
		return appointment_date;
	}

	public void setAppointment_date(String appointment_date) {
		this.appointment_date = appointment_date;
	}

	public User getUser() {
		return user;
	}

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

	@Override
	public String toString() {
		return "Appointment [id=" + id + ", message=" + message + ", createdAt=" + createdAt + ", updatedAt="
				+ updatedAt + ", appointment_date=" + appointment_date + ", user=" + user + ", estate=" + estate + "]";
	}

	public static void setCreatedAt(LocalDateTime now) {
		// TODO Auto-generated method stub
		
	}

	public void setUpdatedAt(LocalDateTime now) {
		// TODO Auto-generated method stub
		
	}


	
}
