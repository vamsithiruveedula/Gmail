package com.gmail.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.gmail.model.ChangePasswordRequest;
import com.gmail.model.Email;
import com.gmail.model.User;

public interface UserService {

	User signUp(User user);

	boolean isEmailUnique(String email);

	User signIn(String email, String password);

	User getUserByEmailId(String emailId);

	List<User> getAllUsers();

	boolean deleteUserByEmailId(String emailId);

	User updateUserProfile(String emailId, User user);

	boolean changePassword(String emailId, ChangePasswordRequest changePasswordRequest);

	List<Email> getEmailsForUser(String emailId, String type);

	boolean deleteEmailById(String emailId, int id);

	User findByEmail(String emailId);

	boolean resetPassword(String emailId, ChangePasswordRequest changePasswordRequest);

	User updateProPicture(String emailId, MultipartFile file);

	boolean movetoTrash(String emailId, int id);

	Email getEmail(int id);

	List<Email> getStarredEmails(String emailId);

}
