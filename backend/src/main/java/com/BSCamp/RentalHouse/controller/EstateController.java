package com.BSCamp.RentalHouse.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Category;
import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.service.CategoryService;
import com.BSCamp.RentalHouse.service.EstateService;
import com.BSCamp.RentalHouse.service.StorageService;

@RestController
@RequestMapping("/api")
public class EstateController {

	@Autowired
	EstateService estateService;

	@Autowired
	CategoryService categoryService;
	
	@Autowired
	StorageService storageService;

	@GetMapping("/estates")
	public List<Estate> getEstates(@RequestParam(required = false) String search) {
		if (search == null) {
			return estateService.getAllByIsRentOutFalse();
		}
		return estateService.getByTitleOrLocation(search, search);
	}

	@GetMapping("/categories/{category_id}/estates")
	public ResponseEntity<?> getEstatesByCategory(@PathVariable("category_id") int categoryId) {
		Category category = categoryService.get(categoryId);
		if (category == null) {
			return ResponseEntity.badRequest().body("Category ID invalid");
		}
		List<Estate> estates = estateService.getAllByCategory(category);
		return ResponseEntity.ok().body(estates);
	}

	@GetMapping("/estates/{estate_id}")
	public ResponseEntity<Estate> getEstate(@PathVariable("estate_id") int estateId) {
		Estate estate = estateService.get(estateId);
		if (estate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(estate);
	}

	@GetMapping("/media/{fileType}/{fileName}")
	public ResponseEntity<?> getEstateImage(
			@PathVariable("fileType") String fileType,
			@PathVariable("fileName") String fileName
	) throws IOException {
		MediaType contentType = MediaType.IMAGE_PNG;
		switch (fileType) {
			case "mp4" :
				contentType = MediaType.APPLICATION_OCTET_STREAM;
				break;
			case "jpg" :
				contentType = MediaType.IMAGE_JPEG;
				break;
			case "png" :
				contentType = MediaType.IMAGE_PNG;
				break;
			default :
				return ResponseEntity.badRequest()
						.body("Unsupported File Type");
		}
		byte[] fileBytes = storageService.load(fileName);
		if (fileBytes == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().contentType(contentType).body(fileBytes);
	}
}
