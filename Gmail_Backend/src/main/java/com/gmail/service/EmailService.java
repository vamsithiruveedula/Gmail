package com.gmail.service;

import com.gmail.model.Email;

public interface EmailService {

	boolean sendEmail(String emailId, Email email);

	boolean receiveEmail(Email email);

	Email markAsRead(Integer id);

	Email markIsImportant(Integer id);

	Email unmarkIsImportant(Integer id);

}
