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
import com.BSCamp.RentalHouse.service.CategoryService;

@RestController
@RequestMapping("/api/admin")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories")
	public List<Category> getCategories(@RequestParam String name) {
		if (name == null) {
			return categoryService.getAll();
		}
		return categoryService.getAllByName(name);
	}

	@PostMapping("/categories")
	public ResponseEntity<?> createCategory(@Valid @RequestBody Category category) {
		return ResponseEntity.ok(categoryService.create(category));
	}

	@PutMapping("/categories/{category_id}")
	public ResponseEntity<?> updateCategory(@PathVariable("category_id") int categoryId,
			@Valid @RequestBody Category category) {
		Category updatedCategory = categoryService.update(categoryId, category);
		if (updatedCategory == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedCategory);
	}

	@DeleteMapping("/categories/{category_id")
	public ResponseEntity<?> deleteCategory(@PathVariable("category_id") int categoryId) {
		boolean isDeleted = categoryService.delete(categoryId);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();
	}

}
