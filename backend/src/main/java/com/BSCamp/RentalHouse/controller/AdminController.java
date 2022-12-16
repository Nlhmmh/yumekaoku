package com.BSCamp.RentalHouse.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.service.EstateService;

@RestController
@RequestMapping("/admin")
public class AdminController {
	@Autowired
	EstateService estateService;

	@PostMapping("/estates/create")
	public ResponseEntity<?> createEstate(@Valid @RequestBody Estate estate) {
		if (estate.getCategory() == null) {
			return ResponseEntity.badRequest().body("Category Not Found.");
		}
		if (estate.getCategory().getId() == 0) {
			return ResponseEntity.badRequest().body("Invalid Category.");
		}
		return ResponseEntity.ok(estateService.create(estate));
	}

	@PutMapping("/estates/update/{estate_id}")
	public ResponseEntity<?> updateEstate(@PathVariable("estate_id") int estateId, @Valid @RequestBody Estate estate) {
		Estate updatedEstate = estateService.update(estateId, estate);
		if (updatedEstate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(updatedEstate);

	}

	@DeleteMapping("/estates/delete/{estate_id}")
	public ResponseEntity<?> deleteEstate(@PathVariable("estate_id") int estateId) {
		boolean isDeleted = estateService.delete(estateId);
		if (!isDeleted) {
			return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
		return ResponseEntity.ok().build();

	}

}
