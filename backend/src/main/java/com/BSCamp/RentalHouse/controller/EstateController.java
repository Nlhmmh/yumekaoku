package com.BSCamp.RentalHouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BSCamp.RentalHouse.entity.Estate;
import com.BSCamp.RentalHouse.service.EstateService;

@RestController
@RequestMapping("/api")
public class EstateController {

	@Autowired
	EstateService estateService;

	@GetMapping("/estates")
	public List<Estate> getEstates() {
		return estateService.getAll();
	}

	@GetMapping("/estates/{estate_id}")
	public ResponseEntity<Estate> getEstate(@PathVariable("estate_id") int estateId) {
		Estate estate = estateService.get(estateId);
		if (estate == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(estate);
	}


}
