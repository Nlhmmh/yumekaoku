package com.BSCamp.RentalHouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BSCamp.RentalHouse.entity.Category;

@EnableJpaRepositories
public interface CategoryRepo extends JpaRepository<Category, Integer> {
	public List<Category> findByName(String name);

}
