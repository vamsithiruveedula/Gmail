package com.gmail.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gmail.model.Folder;
import com.gmail.service.FolderService;

@RestController
@RequestMapping("/folders")
public class FolderController {

	@Autowired
    private FolderService folderService;
	
	@PostMapping("/save")
	public ResponseEntity<?> saveFolder(@RequestBody Folder folder) {
		try {
			Folder savedFolder=folderService.saveFolder(folder);
			if (savedFolder != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(savedFolder);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Folder not created");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}
	
	@GetMapping("/getFolders")
	public ResponseEntity<?> getFolders() {
		try {
			List<Folder> folders = folderService.getFolders();
			if (folders != null) {
				return ResponseEntity.ok(folders);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}
	
}
