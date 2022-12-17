package com.BSCamp.RentalHouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.entity.UserRole;
import com.BSCamp.RentalHouse.entity.UserStatus;
import com.BSCamp.RentalHouse.service.CategoryService;
import com.BSCamp.RentalHouse.service.UserService;

@SpringBootApplication
public class RentalHouseApplication implements CommandLineRunner{
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;
	
	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;
	
	public static void main(String[] args) {
		SpringApplication.run(RentalHouseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if(ddlMode.equals("create")) {
			userService.create(new User(1, "Admin", "admin@gmail.com", "0977777777", UserRole.admin	, UserStatus.active, "password"));
			
			categoryService.create(new Category(1, "1LDK Apartment"));	
			categoryService.create(new Category(2, "2LDK Apartment"));	
			categoryService.create(new Category(3, "3LDK Apartment"));	
			categoryService.create(new Category(4, "1K Apartment"));	
			categoryService.create(new Category(5, "1R Apartment"));	
			categoryService.create(new Category(6, "Retail Shop"));	
			categoryService.create(new Category(7, "Sharedhouse"));	
			categoryService.create(new Category(8, "Office"));	
	
		}
			
	}

}
