package com.BSCamp.RentalHouse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.repository.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepo categoryRepo;

	@Override
	public List<Category> getAll() {
		return categoryRepo.findAll();
	}

	@Override
	public List<Category> getAllByName(String name) {
		return categoryRepo.findByName(name);
	}

	@Override
	public Category get(int id) {
		return categoryRepo.findById(id).orElse(null);
	}

	@Override
	public Category create(Category category) {
		category.setCreatedAt(LocalDateTime.now());
		return categoryRepo.save(category);
	}

	@Override
	public Category update(int id, Category category) {
		Category toUpdateCategory = this.get(id);
		if (toUpdateCategory == null) {
			return null;
		}
		toUpdateCategory.setId(id);
		toUpdateCategory.setName(category.getName());
		toUpdateCategory.setUpdatedAt(LocalDateTime.now());
		categoryRepo.save(toUpdateCategory);
		return toUpdateCategory;
	}

	@Override
	public boolean delete(int id) {
		Category category = this.get(id);
		if (category == null) {
			return false;
		}
		categoryRepo.deleteById(id);
		return true;
	}

}
