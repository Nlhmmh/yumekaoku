package com.BSCamp.RentalHouse.service;

import java.util.List;

import com.BSCamp.RentalHouse.entity.Category;
	
public interface CategoryService {
	public List<Category> getAll();
	
	public List<Category> getAllByName(String name);
	
	public Category get(int id);
	
	public Category create(Category category);
	
	public Category update(int id,Category category);
	
	public boolean delete(int id);

}
