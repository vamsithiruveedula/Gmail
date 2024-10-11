package com.gmail.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.model.ChangePasswordRequest;
import com.gmail.model.Email;
import com.gmail.model.ForgotPasswordRequest;
import com.gmail.model.User;
import com.gmail.service.EmailService;
import com.gmail.service.UserService;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "http://localhost:5173")
public class UserController {

	@Autowired
    private UserService userService;

	@Autowired
    private EmailService emailService;

	@PostMapping("/emailCheck")
	public ResponseEntity<?> emailCheck(@RequestBody Map<String, String> request) {
		try {
			String emailToCheck = request.get("email");
			boolean emailExists = userService.isEmailUnique(emailToCheck);
			if (emailExists) {
				return ResponseEntity.status(HttpStatus.OK).body("Email is available");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Email already exists");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PostMapping("/signup")
	public ResponseEntity<?> signUp(@RequestBody User user) {
		try {
			if (user.getEmail() == null || user.getEmail().isEmpty() || user.getFirstName() == null
					|| user.getFirstName().isEmpty() || user.getLastName() == null || user.getLastName().isEmpty()
					|| user.getPassword() == null || user.getPassword().isEmpty() || user.getMobileNo() == 0L) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
			}
			User savedUser = userService.signUp(user);
			if (savedUser != null) {
				return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email already exists");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PostMapping("/signin")
	public ResponseEntity<?> signIn(@RequestBody User user) {
		try {
			// Validate user input
			if (user.getEmail() == null || user.getEmail().isEmpty() || user.getPassword() == null
					|| user.getPassword().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
			}
			// Call the service layer to authenticate the user
			User existingUser = userService.signIn(user.getEmail(), user.getPassword());

			if (existingUser != null) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("User login success");
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PostMapping("/{emailId}/send")
	public ResponseEntity<?> sendEmail(@PathVariable String emailId, @RequestBody Email email) {
		try {
			boolean sent = emailService.sendEmail(emailId, email);
			if (sent) {
				return ResponseEntity.ok("Email send successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to send email.");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/get/{emailId}")
	public ResponseEntity<?> getUser(@PathVariable String emailId) {
		try {
			User user = userService.getUserByEmailId(emailId);
			if (user != null) {
				return ResponseEntity.ok(user);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/get/{emailId}/{type}")
	public ResponseEntity<?> getUserEmails(@PathVariable String emailId, @PathVariable String type) {
		try {
			List<Email> emails = userService.getEmailsForUser(emailId, type);
			if (!emails.isEmpty()) {
				return ResponseEntity.ok(emails);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No emails found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getAll")
	public ResponseEntity<?> getAllUsers() {
		try {
			List<User> users = userService.getAllUsers();
			if (users.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Users are empty");
			} else {
				return ResponseEntity.ok(users);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@DeleteMapping("/delete/{emailId}")
	public ResponseEntity<?> deleteUserByEmailId(@PathVariable String emailId) {
		try {
			boolean user = userService.deleteUserByEmailId(emailId);
			if (user) {
				return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/update/{emailId}")
	public ResponseEntity<?> updateUser(@PathVariable String emailId, @RequestBody User user) {
		try {
			User updatedUser = userService.updateUserProfile(emailId, user);
			if (updatedUser != null) {
				return ResponseEntity.ok(updatedUser);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/changePicture/{emailId}")
	public ResponseEntity<?> updateProPicture(@PathVariable String emailId, @RequestParam("file") MultipartFile file) {
		try {
			User updatedUser = userService.updateProPicture(emailId, file);
			if (updatedUser != null) {
				return ResponseEntity.ok(updatedUser);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/changePassword/{emailId}")
	public ResponseEntity<?> changePassword(@PathVariable String emailId,
			@RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			if (emailId == null || changePasswordRequest.getOldPassword() == null
					|| changePasswordRequest.getNewPassword() == null
					|| changePasswordRequest.getOldPassword().isEmpty()
					|| changePasswordRequest.getNewPassword().isEmpty()
					|| changePasswordRequest.getOldPassword().equals(changePasswordRequest.getNewPassword())) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
			}
			boolean passwordChanged = userService.changePassword(emailId, changePasswordRequest);
			if (passwordChanged) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password changed successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Incorrect password");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@DeleteMapping("/delete/{emailId}/{id}")
	public ResponseEntity<?> deleteEmail(@PathVariable String emailId, @PathVariable int id) {
		try {
			boolean deleted = userService.deleteEmailById(emailId, id);
			if (deleted) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/moveToTrash/{emailId}/{id}")
	public ResponseEntity<?> trashEmail(@PathVariable String emailId, @PathVariable int id) {
		try {
			boolean trashed = userService.movetoTrash(emailId, id);
			if (trashed) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Email moved to trash");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getEmail/{id}")
	public ResponseEntity<?> getEmail(@PathVariable int id) {
		try {
			Email email = userService.getEmail(id);
			if (email != null) {
				return ResponseEntity.ok(email);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@GetMapping("/getStarred/{emailId}")
	public ResponseEntity<?> getStarred(@PathVariable String emailId) {
		try {
			List<Email> emails = userService.getStarredEmails(emailId);
			if (emails != null) {
				return ResponseEntity.ok(emails);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Email not found");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PostMapping("/verifyPassword/{emailId}")
	public ResponseEntity<?> verifyPassword(@PathVariable String emailId, @RequestBody Map<String, String> request) {
		try {
			String oldPassword = request.get("oldPassword");
			User existingUser = userService.findByEmail(emailId);

			String currentPassword = existingUser.getPassword();
			if (currentPassword.equals(oldPassword)) {
				return ResponseEntity.ok("PasswordVerified");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Incorrect old password");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}

	}

	@PostMapping("/forgotPassword")
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
		try {
			if (forgotPasswordRequest.getEmail() == null || forgotPasswordRequest.getEmail().isEmpty()
					|| forgotPasswordRequest.getMobileNo() == null) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
			}
			User existingUser = userService.findByEmail(forgotPasswordRequest.getEmail());
			if (existingUser != null) {
				if (forgotPasswordRequest.getMobileNo().equals(existingUser.getMobileNo())) {
					return ResponseEntity.status(HttpStatus.ACCEPTED).body("User Found");
				} else {
					return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter valid details");
				}
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Enter valid details");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

	@PutMapping("/resetPassword/{emailId}")
	public ResponseEntity<?> resetPassword(@PathVariable String emailId,
			@RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			if (emailId == null || changePasswordRequest.getNewPassword() == null
					|| changePasswordRequest.getNewPassword().isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid input");
			}
			boolean passwordChanged = userService.resetPassword(emailId, changePasswordRequest);
			if (passwordChanged) {
				return ResponseEntity.status(HttpStatus.ACCEPTED).body("Password reset successfully");
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to reset password");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
		}
	}

}
