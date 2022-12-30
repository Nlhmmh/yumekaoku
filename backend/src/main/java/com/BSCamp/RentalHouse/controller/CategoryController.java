package com.BSCamp.RentalHouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.service.CategoryService;

@RestController
@RequestMapping("/api")
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@GetMapping("/categories")
	public List<Category> getCategories(@RequestParam(required = false) String name) {
		if (name == null) {
			return categoryService.getAll();
		}
		return categoryService.getAllByName(name);
	}

}
