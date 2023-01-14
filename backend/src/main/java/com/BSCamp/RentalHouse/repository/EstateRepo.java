package com.BSCamp.RentalHouse.repository;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;

@EnableJpaRepositories
public interface EstateRepo extends JpaRepository<Estate, Integer> {
	public List<Estate> findByCategory(Category category);

	public List<Estate> findByCategoryAndIsRentOutFalse(Category category);

	public List<Estate> findByLocationContainingIgnoreCase(String location);

	public List<Estate> findByLocationContainingIgnoreCaseAndCategory(String location, Category category);

	public List<Estate> findByLocationContainingIgnoreCaseAndIsRentOutFalse(String location);

	public List<Estate> findByLocationContainingIgnoreCaseAndCategoryAndIsRentOutFalse(String location,
			Category category);

//	@Query("SELECT * FROM estate WHERE is_rent_out = false")
	public List<Estate> findByIsRentOutFalse();

}
