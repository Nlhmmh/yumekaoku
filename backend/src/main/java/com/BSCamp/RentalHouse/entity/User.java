package com.BSCamp.RentalHouse.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class User implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length = 20 , nullable = false)
	@NotBlank(message = "Required")
	private String name;
	
	@Column(length = 20 , nullable = false, unique = true)
	@NotBlank(message = "Required")
	private String email;
	
	@Column(name = "phone_number" ,length = 20 , nullable = false)
	@NotBlank(message = "Required")
	private String phoneNumber;
	
	@Column(columnDefinition = "ENUM('admin','user') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserRole role;

	@Column(columnDefinition = "ENUM('active','deactivated') NOT NULL")
	@Enumerated(EnumType.STRING)
	private UserStatus status;
	
	@Column(length = 100 ,nullable = false)
	@NotBlank(message = "Required")
	private String password;
	
	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	public User() {
		
	}
	

	public User(@NotBlank(message = "Required") String name, @NotBlank(message = "Required") String email,
			@NotBlank(message = "Required") String phoneNumber, UserRole role, UserStatus status,
			@NotBlank(message = "Required") String password) {
		this.name = name;
		this.email = email;
		this.phoneNumber = phoneNumber;
		this.role = role;
		this.status = status;
		this.password = password;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
	
}
