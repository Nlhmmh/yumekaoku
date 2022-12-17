package com.BSCamp.RentalHouse.service;

import java.util.List;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;

public interface EstateService {
	public List<Estate> getAll();
	
	public List<Estate> getByCategory(Category category);
	
	public Estate get(int id);
	
	public Estate create(Estate estate); 
	
	public Estate update(int id, Estate estate);
	
	public boolean delete(int id);

}
