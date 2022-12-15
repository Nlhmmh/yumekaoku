package com.BSCamp.RentalHouse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.BSCamp.RentalHouse.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{
	public List<User> findByNameOrEmailOrPhoneNumber(String filter);

}
