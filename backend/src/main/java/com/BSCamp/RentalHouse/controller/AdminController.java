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

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.service.CategoryService;
import com.BSCamp.RentalHouse.service.EstateService;
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

//	Estate Routes
	@GetMapping("/estates")
	public List<Estate> getEstates() {
		return estateService.getAll();
	}
	
	@PostMapping("/estates/create")
	public ResponseEntity<?> createEstate(@Valid @RequestBody Estate estate) {
		if (estate.getCategory() == null) {
			return ResponseEntity.badRequest().body("Category Not Found.");
		}
		if (estate.getCategory().getId() == 0) {
			return ResponseEntity.badRequest().body("Invalid Category.");
		}
		return ResponseEntity.ok(estateService.create(estate));
	}

	@PutMapping("/estates/update/{estate_id}")
	public ResponseEntity<?> updateEstate(@PathVariable("estate_id") int estateId, @Valid @RequestBody Estate estate) {
		Estate updatedEstate = estateService.update(estateId, estate);
		if (updatedEstate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedEstate);

	}

	@DeleteMapping("/estates/delete/{estate_id}")
	public ResponseEntity<?> deleteEstate(@PathVariable("estate_id") int estateId) {
		boolean isDeleted = estateService.delete(estateId);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
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
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}

}
