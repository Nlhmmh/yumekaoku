package com.BSCamp.RentalHouse.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.BSCamp.RentalHouse.entity.Appointment;
import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.service.AppointmentService;
import com.BSCamp.RentalHouse.service.CategoryService;
import com.BSCamp.RentalHouse.service.EstateService;
import com.BSCamp.RentalHouse.service.StorageService;
import com.BSCamp.RentalHouse.service.UserService;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	UserService userService;

	@Autowired
	EstateService estateService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	StorageService storageService;

	@Autowired
	AppointmentService appointmentService;

	// Admin User Routes
	@GetMapping("/users")
	public List<User> getUsers(@RequestParam(required = false) String filter) {
		if (filter == null) {
			return userService.getAll();
		}
		return userService.getByNameOrEmailOrPhoneNumber(filter, filter, filter);
	}

	@PostMapping("/users/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody User user) {
		return ResponseEntity.ok(userService.create(user));
	}

	@PutMapping("/users/update/{user_id}")
	public ResponseEntity<?> updateUser(@PathVariable("user_id") int userId, @Valid @RequestBody User user) {
		User updatedUser = userService.update(userId, user);
		if (updatedUser == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedUser);
	}

	@DeleteMapping("/users/delete/{user_id}")
	public ResponseEntity<?> deleteUser(@PathVariable("user_id") int userId) {
		boolean isDeleted = userService.delete(userId);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}

	// File Routes
	@PostMapping("/file/create")
	public ResponseEntity<String> createFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType) {
		String filePath = storageService.create(file, fileType);
		if (filePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(filePath);
	}

	@PutMapping("/file/update")
	public ResponseEntity<String> updateFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileType") String fileType, @RequestParam("filePath") String filePath) {
		String newFilePath = storageService.update(file, fileType, filePath);
		if (newFilePath == null) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok(newFilePath);
	}

//	Estate Routes
	@GetMapping("/estates")
	public List<Estate> getEstates(@RequestParam(required = false) String search) {
		if (search == null) {
			return estateService.getAll();
		}
		return estateService.getByLocation(search);
	}

	@GetMapping("/estates/{estate_id}")
	public ResponseEntity<?> getEstate(@PathVariable("estate_id") int estateId) {
		Estate estate = estateService.get(estateId);
		if (estate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(estate);
	}

	@PostMapping("/estates/create")
	public ResponseEntity<?> createEstate(@Valid @RequestBody Estate estate) {
		if (estate.getCategory() == null) {
			return ResponseEntity.badRequest().body("Category Not Found.");
		}

		if (estate.getCategory() != null) {
			Category category = categoryService.get(estate.getCategory().getId());
			if (category == null) {
				return ResponseEntity.badRequest().body("Invalid Category.");
			}
		}

		if (!storageService.check(estate.getImagePath())) {
			return ResponseEntity.badRequest().body("Invaild Image");
		}
		if (!storageService.check(estate.getVideoPath())) {
			return ResponseEntity.badRequest().body("Invaild Video");
		}
		return ResponseEntity.ok(estateService.create(estate));
	}

	@PutMapping("/estates/update/{estate_id}")
	public ResponseEntity<?> updateEstate(@PathVariable("estate_id") int estateId, @Valid @RequestBody Estate estate) {
		if (estate.getCategory() != null) {
			Category category = categoryService.get(estate.getCategory().getId());
			if (category == null) {
				return ResponseEntity.badRequest().body("Invalid Category.");
			}
		}

		Estate updatedEstate = estateService.update(estateId, estate);
		if (updatedEstate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedEstate);

	}

	@PutMapping("/estates/{estate_id}/rentout/update")
	public ResponseEntity<?> updateRentOutStatus(@PathVariable("estate_id") int estateId,
			@RequestParam(value = "rentout") boolean rentOut) {
		Estate updatedEstate = estateService.updateRentOutStatus(estateId, rentOut);
		if (updatedEstate == null) {
			return ResponseEntity.badRequest().body("Invalid Estate");
		}
		return ResponseEntity.ok().body(updatedEstate);
	}

	@DeleteMapping("/estates/delete/{estate_id}")
	public ResponseEntity<?> deleteEstate(@PathVariable("estate_id") int estateId) {
		Estate estate = estateService.get(estateId);

		if (estate == null) {
			return ResponseEntity.notFound().build();
		}

		boolean isDeleted = estateService.delete(estateId);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}

		// Delete Estate Image
		storageService.delete(estate.getImagePath());

		// Delete Estate Video
		storageService.delete(estate.getVideoPath());
		return ResponseEntity.ok().build();

	}

	// Category Routes
	@GetMapping("/categories")
	public List<Category> getCategories(@RequestParam(required = false) String name) {
		if (name == null) {
			return categoryService.getAll();
		}
		return categoryService.getAllByName(name);
	}

	@PostMapping("/categories/create")
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
		return ResponseEntity.ok(categoryService.create(category));
	}

	@PutMapping("/categories/update/{category_id}")
	public ResponseEntity<?> updateCategory(@PathVariable("category_id") int categoryId,
			@Valid @RequestBody Category category) {
		Category updatedCategory = categoryService.update(categoryId, category);
		if (updatedCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedCategory);
	}

	@DeleteMapping("/categories/delete/{category_id}")
	public ResponseEntity<?> deleteCategory(@PathVariable("category_id") int categoryId) {
		boolean isDeleted = categoryService.delete(categoryId);
		if (!isDeleted) {
//			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			return ResponseEntity.badRequest().body("Category Not found");
		}
		return ResponseEntity.ok().build();
	}

	// Admin appointment Routes
	@GetMapping("/appointments")
	public List<Appointment> getAll() {
		return appointmentService.getAll();
	}

//	@PutMapping("/appointments/update/{id}/status")
//	public ResponseEntity<?> updateAppointmentStatus(@PathVariable("id") int id, @RequestParam String status) {
//		Appointment updatedAppointment = appointmentService.updateStatus(id, status);
//		if (updatedAppointment == null) {
//			return ResponseEntity.badRequest().body("Appointment is invalid, Status is invalid");
//		}
//		return ResponseEntity.ok(updatedAppointment);
//	}

}
