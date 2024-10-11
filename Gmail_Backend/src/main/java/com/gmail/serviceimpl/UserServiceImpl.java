package com.gmail.serviceimpl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.gmail.model.ChangePasswordRequest;
import com.gmail.model.Email;
import com.gmail.model.User;
import com.gmail.repository.EmailRepository;
import com.gmail.repository.UserRepository;
import com.gmail.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Override
	public User signUp(User user) {
		try {
			User existingUser = userRepository.findByEmail(user.getEmail());
			if (existingUser == null) {
				return userRepository.save(user);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean isEmailUnique(String email) {
		try {
			User existingUser = userRepository.findByEmail(email);
			return existingUser == null;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User signIn(String email, String password) {
		try {
			User user = userRepository.findByEmail(email);
			if (user != null && password.equals(user.getPassword()))
				return user;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User getUserByEmailId(String emailId) {
		try {
			User user = userRepository.findByEmail(emailId);
			if (user != null) {
				return user;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Email> getEmailsForUser(String emailId, String type) {
		try {
			User user = userRepository.findByEmail(emailId);
			if (type.equals("inbox")) {
				List<Email> emails = emailRepository.findByUserAndType(user, type);
				emails.forEach(email -> email.setUser(null));
				return emails;
			} else if (type.equals("sent")) {
				List<Email> emails = emailRepository.findByUserAndType(user, type);
				emails.forEach(email -> email.setUser(null));
				return emails;
			} else if (type.equals("trash")) {
				List<Email> emails = emailRepository.findByUserAndType(user, type);
				emails.forEach(email -> email.setUser(null));
				return emails;
			} else
				return Collections.emptyList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<User> getAllUsers() {
		try {
			return userRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean deleteUserByEmailId(String emailId) {
		try {
			User user = userRepository.findByEmail(emailId);
			if (user != null) {
				userRepository.deleteById(emailId);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public User updateUserProfile(String emailId, User user) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				if (user.getMobileNo() == 0L) {
					existingUser.setFirstName(user.getFirstName());
					existingUser.setLastName(user.getLastName());
					return userRepository.save(existingUser);
				}
				existingUser.setMobileNo(user.getMobileNo());
				existingUser.setFirstName(user.getFirstName());
				existingUser.setLastName(user.getLastName());
				return userRepository.save(existingUser);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User updateProPicture(String emailId, MultipartFile file) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				if (file != null && !file.isEmpty()) {
					try {
						// Convert MultipartFile to a byte array
						byte[] imageBytes = file.getBytes();
						// Set the updated profilePicture as a byte array
						existingUser.setProfilePicture(imageBytes);
						return userRepository.save(existingUser);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean changePassword(String emailId, ChangePasswordRequest changePasswordRequest) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				String currentPassword = existingUser.getPassword();
				if (!currentPassword.equals(changePasswordRequest.getOldPassword())) {
					return false;
				}
				// Update the user's password with the new password
				existingUser.setPassword(changePasswordRequest.getNewPassword());
				// Save the updated user to the database
				userRepository.save(existingUser);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteEmailById(String emailId, int id) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				emailRepository.deleteById(id);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean movetoTrash(String emailId, int id) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				Optional<Email> email = emailRepository.findById(id);
				if (email.isPresent()) {
					Email existingEmail = email.get();
					existingEmail.setDeleted(true);
					existingEmail.setType("trash");
					emailRepository.save(existingEmail);
					return true;
				} else {
					return false; // Email not found
				}
			}
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Email getEmail(int id) {
		try {
			Optional<Email> email = emailRepository.findById(id);
			if (email.isPresent()) {
				Email existingEmail = email.get();
				return existingEmail;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public User findByEmail(String emailId) {
		try {
			return userRepository.findByEmail(emailId);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Email> getStarredEmails(String emailId) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			List<Email> emails = emailRepository.findByUserAndIsImportant(existingUser, true);
			emails.forEach(email -> email.setUser(null));
			return emails;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean resetPassword(String emailId, ChangePasswordRequest changePasswordRequest) {
		try {
			User existingUser = userRepository.findByEmail(emailId);
			if (existingUser != null) {
				existingUser.setPassword(changePasswordRequest.getNewPassword());
				userRepository.save(existingUser);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
