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
import com.BSCamp.RentalHouse.service.StorageService;
import com.BSCamp.RentalHouse.service.UserService;

@SpringBootApplication
public class RentalHouseApplication implements CommandLineRunner {
	@Autowired
	UserService userService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	EstateService estateService;

	@Autowired
	StorageService storageService;

	@Value("${spring.jpa.hibernate.ddl-auto}")
	private String ddlMode;

	@Value("${custom.delete.files}")
	private String deleteFiles;

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
			Category category5 = categoryService.create(new Category(5, "1K Apartment"));
			Category category6 = categoryService.create(new Category(6, "Retail Shop"));
			Category category7 = categoryService.create(new Category(7, "Sharedhouse"));

			estateService.create(new Estate(1, "Itabashi-ku - Tokyo", "test", 195000, 5000, 44, false,
					"Itabashi-ku - Tokyo", null, null, null, category1));
			estateService.create(new Estate(2, "Minato-ku - Tokyo", "test", 165000, 5000, 44, false,
					"Minato-ku - Tokyo", null, null, null, category2));
			estateService.create(new Estate(3, "SHINJUKU-KU, TOKYO", "test", 175000, 5000, 44, false,
					"SHINJUKU-KU, TOKYO, IN YOTSUYASANEICHO", null, null, null, category3));
			estateService.create(new Estate(4, "Nakano-ku - Tokyo", "test", 77000, 5000, 44, false, "Nakano-ku - Tokyo",
					null, null, null, category4));
			estateService.create(new Estate(5, "YOKOSUKA-SHI, KANAGAWA", "test", 145000, 5000, 44, false,
					"YOKOSUKA-SHI, KANAGAWA", null, null, null, category5));
			estateService.create(new Estate(6, "EBINA-SHI, KANAGAWA", "test", 105000, 5000, 44, false,
					"Ebina Station (7 min. walk)", null, null, null, category6));
			estateService.create(new Estate(7, "MINATO-KU, TOKYO", "test", 60000, 5000, 44, false,
					"MINATO-KU, TOKYO, in ROPPONGI", null, null, null, category7));
			estateService.create(new Estate(8, "Setagaya-ku - Tokyo", "test", 95000, 5000, 44, false,
					"Setagaya-ku - Tokyo", null, null, null, category4));
	
		}
		
		if (deleteFiles.equals("true")) {
			storageService.clearAll();
		}
	}

}
