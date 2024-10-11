package com.gmail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.model.Email;
import com.gmail.service.EmailService;

@RestController
@RequestMapping("/emails")
public class EmailController {
	
	@Autowired
    private EmailService emailService;

	@PutMapping("/markAsRead/{id}")
	public ResponseEntity<?> markEmailAsRead(@PathVariable Integer id) {
		try {
			// Retrieve the email by its ID from the database or storage
			Email email = emailService.markAsRead(id);
			if (email != null) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle the exception (e.g., log it) and return an appropriate error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/isImportant/{id}")
	public ResponseEntity<?> markIsImportant(@PathVariable Integer id) {
		try {
			Email email = emailService.markIsImportant(id);
			if (email != null) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle the exception (e.g., log it) and return an appropriate error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/isNotImportant/{id}")
	public ResponseEntity<?> unmarkIsImportant(@PathVariable Integer id) {
		try {
			Email email = emailService.unmarkIsImportant(id);
			if (email != null) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (Exception e) {
			// Handle the exception (e.g., log it) and return an appropriate error response
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

}
