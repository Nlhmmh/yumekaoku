package com.BSCamp.RentalHouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.entity.User;
import com.BSCamp.RentalHouse.entity.UserRole;
import com.BSCamp.RentalHouse.entity.UserStatus;
import com.BSCamp.RentalHouse.service.CategoryService;
import com.BSCamp.RentalHouse.service.EstateService;
import com.BSCamp.RentalHouse.service.UserService;

@SpringBootApplication
public class RentalHouseApplication implements CommandLineRunner {
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	EstateService estateService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;

	public static void main(String[] args) {
		SpringApplication.run(RentalHouseApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		if (ddlMode.equals("create")) {
			userService.create(new User(1, "Admin", "admin@gmail.com", "0977777777", UserRole.admin, UserStatus.active,
					"password"));

			Category category1 = categoryService.create(new Category(1, "1LDK Apartment"));
			Category category2 = categoryService.create(new Category(2, "2LDK Apartment"));
			Category category3 = categoryService.create(new Category(3, "3LDK Apartment"));
			Category category4 = categoryService.create(new Category(4, "1R Apartment"));
			Category category5 = categoryService.create(new Category(6, "Retail Shop"));
			Category category6 = categoryService.create(new Category(7, "Sharedhouse"));
			Category category7 = categoryService.create(new Category(8, "Office"));

			estateService.create(new Estate(1, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category1));
			estateService.create(new Estate(2, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category2));
			estateService.create(new Estate(3, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category3));
			estateService.create(new Estate(4, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category4));
			estateService.create(new Estate(5, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category5));
			estateService.create(new Estate(6, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category6));
			estateService.create(new Estate(7, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category7));

		}

	}

}
