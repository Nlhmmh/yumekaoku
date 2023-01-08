package com.BSCamp.RentalHouse.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.repository.EstateRepo;

@Service
public class EstateServiceImpl implements EstateService {
	@Autowired
	EstateRepo estateRepo;

	@Override
	public List<Estate> getAll() {
		return estateRepo.findAll();
	}

	@Override
	public List<Estate> getAllByIsRentOutFalse() {
		return estateRepo.findByIsRentOutFalse();
	}

	@Override
	public List<Estate> getAllByCategory(Category category) {
		return estateRepo.findByCategory(category);
	}

	@Override
	public List<Estate> getAllByCategoryAndRentOutFalse(Category category) {
		return estateRepo.findByCategoryAndIsRentOutFalse(category);
	}

	@Override
	public List<Estate> getByLocation(String location) {
		return estateRepo.findByLocationContainingIgnoreCase(location);
	}

	@Override
	public List<Estate> getByLocationAndCategory(String location, Category category) {
		return estateRepo.findByLocationContainingIgnoreCaseAndCategory(location, category);
	}

	@Override
	public List<Estate> getByLocationAndRentOutFalse(String location) {
		return estateRepo.findByLocationContainingIgnoreCaseAndIsRentOutFalse(location);
	}

	@Override
	public List<Estate> getByLocationAndCategoryAndRentOutFalse(String location, Category category) {
		return estateRepo.findByLocationContainingIgnoreCaseAndCategoryAndIsRentOutFalse(location, category);
	}

	@Override
	public Estate get(int id) {
		return estateRepo.findById(id).orElse(null);
	}

	@Override
	public Estate create(Estate estate) {
		estate.setRentOut(false);
		estate.setCreatedAt(LocalDateTime.now());
		return estateRepo.save(estate);
	}

	@Override
	public Estate update(int id, Estate estate) {
		Estate toUpdateEstate = this.get(id);
		if (toUpdateEstate == null) {
			return null;
		}
		toUpdateEstate.setId(id);
		toUpdateEstate.setTitle(estate.getTitle());
		toUpdateEstate.setDescription(estate.getDescription());
		toUpdateEstate.setRentFee(estate.getRentFee());
		toUpdateEstate.setMaintenanceFee(estate.getMaintenanceFee());
		toUpdateEstate.setSize(estate.getSize());
		toUpdateEstate.setLocation(estate.getLocation());
		toUpdateEstate.setRemarks(estate.getRemarks());
		toUpdateEstate.setRentOut(estate.isRentOut());
		toUpdateEstate.setCategory(estate.getCategory());
		toUpdateEstate.setImagePath(estate.getImagePath());
		toUpdateEstate.setVideoPath(estate.getVideoPath());
		toUpdateEstate.setUpdatedAt(LocalDateTime.now());
		estateRepo.save(toUpdateEstate);
		return toUpdateEstate;
	}

	@Override
	public boolean delete(int id) {
		Estate estate = this.get(id);
		if (estate == null) {
			return false;
		}
		estateRepo.deleteById(id);
		return true;
	}

}
