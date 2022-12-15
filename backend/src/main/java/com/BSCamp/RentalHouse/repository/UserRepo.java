package com.BSCamp.RentalHouse.repository;

//import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.BSCamp.RentalHouse.entity.User;

@EnableJpaRepositories
public interface UserRepo extends JpaRepository<User, Integer>{
//	public List<User> findByNameOrEmailOrPhoneNumber(String filter);

}
