package com.BSCamp.RentalHouse.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.ColumnDefault;

@Entity
public class Estate implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 255, nullable = false)
	@NotBlank(message = "Required")
	private String title;

	@Column(length = 255, nullable = false)
	@NotBlank(message = "Required")
	private String description;

	@Column(nullable = false)
//	@NotBlank(message = "Required")
	private double rentFee;

	@ColumnDefault("0")
	private double maintenanceFee;

	private int size;

	@Column(columnDefinition = "boolean", nullable = false)
	@ColumnDefault("false")
	private boolean isRentOut;

	@Column(length = 255, nullable = false)
	@NotBlank(message = "Required")
	private String location;

	@Column(length = 255)
	private String remarks;

	@Column(length = 255)
//	@NotBlank(message = "Required")
	private String imagePath;

	@Column(length = 255)
	private String videoPath;

	@ManyToOne
	@JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
	private Category category;

	@Column(nullable = false)
	@ColumnDefault("CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	private LocalDateTime updatedAt;

	public Estate() {
	}

	public Estate(int id, @NotBlank(message = "Required") String title,
			@NotBlank(message = "Required") String description, double rentFee, double maintenanceFee, int size,
			boolean isRentOut, @NotBlank(message = "Required") String location, String remarks, String imagePath,
			String videoPath, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.description = description;
		this.rentFee = rentFee;
		this.maintenanceFee = maintenanceFee;
		this.size = size;
		this.isRentOut = isRentOut;
		this.location = location;
		this.remarks = remarks;
		this.imagePath = imagePath;
		this.videoPath = videoPath;
		this.category = category;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getRentFee() {
		return rentFee;
	}

	public void setRentFee(double rentFee) {
		this.rentFee = rentFee;
	}

	public double getMaintenanceFee() {
		return maintenanceFee;
	}

	public void setMaintenanceFee(double maintenanceFee) {
		this.maintenanceFee = maintenanceFee;
	}

	public boolean isRentOut() {
		return isRentOut;
	}

	public void setRentOut(boolean isRentOut) {
		this.isRentOut = isRentOut;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public String getVideoPath() {
		return videoPath;
	}

	public void setVideoPath(String videoPath) {
		this.videoPath = videoPath;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
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
