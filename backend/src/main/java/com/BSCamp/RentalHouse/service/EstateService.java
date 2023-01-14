package com.BSCamp.RentalHouse.service;

import java.util.List;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;

public interface EstateService {
	public List<Estate> getAll();
	
	public List<Estate> getAllByCategory(Category category);
	
	public List<Estate> getAllByCategoryAndRentOutFalse(Category category);
	
	public List<Estate> getByLocation(String location);
	
	public List<Estate> getByLocationAndCategory(String location, Category category);
	
	public List<Estate> getByLocationAndRentOutFalse( String location);
	
	public List<Estate> getByLocationAndCategoryAndRentOutFalse(String location, Category category);
	
	public List<Estate> getAllByIsRentOutFalse();
	
	public Estate get(int id);
	
	public Estate create(Estate estate); 
	
	public Estate update(int id, Estate estate);
	
	public Estate updateRentOutStatus(int id, boolean rentOut);
	
	public boolean delete(int id);

}
