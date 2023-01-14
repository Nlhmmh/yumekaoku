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
			
			userService.create(new User(2, "Mei", "mei@gmail.com", "01456342", UserRole.user, UserStatus.active,
					"password"));

			Category category1 = categoryService.create(new Category(1, "1LDK Apartment"));
			Category category2 = categoryService.create(new Category(2, "2LDK Apartment"));
			Category category3 = categoryService.create(new Category(3, "3LDK Apartment"));
			Category category4 = categoryService.create(new Category(4, "1R Apartment"));
			Category category5 = categoryService.create(new Category(5, "1K Apartment"));
			Category category6 = categoryService.create(new Category(6, "Retail Shop"));
			Category category7 = categoryService.create(new Category(7, "Sharedhouse"));

			estateService.create(new Estate(1, "Itabashi-ku - Tokyo", "Totally New Building (Completion: January 10 2023)\r\n"
					+ "No Guarantor, No Key Money, No Agent Fee\r\n"
					+ "2 months deposit only", 195000, 5000, 44, false,
					"Akatsuka, Itabashi-ku, Tokyo", "Please note that all listed fees are estimated amounts and may differ depending on your move in date, payment method, etc. Please inquire for exact costs.", null, null, category1));
			estateService.create(new Estate(2, "Minato-ku - Tokyo", "The apartment is a 60square meter with 2LDK type on the third floor of a five-storey building. The room has a wide span opening and balcony on the south-east side, providing good exposure to the sun", 165000, 5000, 60, false,
					"Azabu-juban Station (5 min. walk)\r\n"
					+ "Tokyo Metro Namboku Line", null, null, null, category2));
			estateService.create(new Estate(3, "SHINJUKU-KU, TOKYO", "2 months deposit only", 175000, 5000, 42, false,
					"SHINJUKU-KU, TOKYO, IN YOTSUYASANEICHO", null, null, null, category3));
			estateService.create(new Estate(4, "Nakano-ku - Tokyo", "2 months deposit only", 77000, 5000, 50, false, "Nakano-ku - Tokyo",
					null, null, null, category4));
			estateService.create(new Estate(5, "YOKOSUKA-SHI, KANAGAWA", "2 months deposit only", 145000, 5000, 44, false,
					"YOKOSUKA-SHI, KANAGAWA", null, null, null, category5));
			estateService.create(new Estate(6, "EBINA-SHI, KANAGAWA", "2 months deposit only", 105000, 5000, 44, false,
					"Ebina Station (7 min. walk)", null, null, null, category6));
			estateService.create(new Estate(7, "Mita, Minato-ku, Tokyo", "Utility fees (Electricity, Gas, Water & Wi-Fi) are included in the rent amount.", 60000, 5000, 44, false,
					"MINATO-KU, TOKYO, in ROPPONGI", null, null, null, category7));
			estateService.create(new Estate(8, "Setagaya-ku - Tokyo", "Utility fees (Electricity, Gas, Water & Wi-Fi) are included in the rent amount.", 95000, 5000, 44, false,
					"Setagaya-ku - Tokyo", null, null, null, category4));
			estateService.create(new Estate(9, "Sumida-ku ", "2 months deposit only", 195000, 5000, 50, false,
					"Sumida-ku  - Tokyo", null, null, null, category1));
			estateService.create(new Estate(10, "Kyoto-shi Higashiyama-ku", "Utility fees (Electricity, Gas, Water & Wi-Fi) are included in the rent amount.", 165000, 5000, 45, false,
					"Kyoto-shi Higashiyama-ku - Kyoto", null, null, null, category2));
			estateService.create(new Estate(11, "Shibuya-ku - Tokyo", "The apartment is a 70square meter with 3LDK type on the third floor of a five-storey building. The room has a wide span opening and balcony on the south-east side, providing good exposure to the sun", 175000, 5000, 70, false,
					"Jingumae, Shibuya-ku, Tokyo", null, null, null, category3));
			estateService.create(new Estate(12, "Chuo-ku", "South-facing sunny property.", 77000, 5000, 44, false, "Chuo-ku - Tokyo",
					null, null, null, category4));
			estateService.create(new Estate(13, "Fukuoka-shi Hakata-ku - Fukuoka", "-To Supermarket by walk only takes 2minutes.*Sunny ekiminami(140m).", 64000, 5000, 40, false,
					"Fukuoka-shi Hakata-ku - Fukuoka", null, null, null, category5));
			estateService.create(new Estate(14, "Saitama-shi Omiya-ku - Saitama", "South-facing sunny property.", 105000, 5000, 44, false,
					"Saitama-shi Omiya-ku - Saitama", null, null, null, category6));
			estateService.create(new Estate(15, "Ebina-shi - Kanagawa", "South-facing sunny property.", 60000, 5000, 44, false,
					"Ebina-shi - Kanagawa", null, null, null, category7));
			estateService.create(new Estate(16, "Yokosuka-shi - Kanagawa", "South-facing sunny property.", 95000, 5000, 44, false,
					"Yokosuka-shi - Kanagawa", null, null, null, category4));
	
		}
		
		if (deleteFiles.equals("true")) {
			storageService.clearAll();
		}
	}

}
