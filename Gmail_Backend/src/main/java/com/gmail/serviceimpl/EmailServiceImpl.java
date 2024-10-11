package com.gmail.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.model.Email;
import com.gmail.model.User;
import com.gmail.repository.EmailRepository;
import com.gmail.repository.UserRepository;
import com.gmail.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Override
	public boolean sendEmail(String emailId, Email email) {
		try {
			User sendUser = userRepository.findByEmail(emailId);
			email.setSenderId(emailId);
			email.setImportant(false);
			email.setRead(false);
			email.setType("sent");
			email.setSentDate(LocalDateTime.now());
			email.setUser(sendUser);
			int count = 0;
			for (String emailId1 : email.getRecipientIds()) {
				User user = userRepository.findByEmail(emailId1);
				if (user != null) {
					count++;
				} else {
					System.out.println("Email address not found in the database: " + emailId1);
				}
			}
			emailRepository.save(email);
			boolean received = receiveEmail(email);
			if (received) {
				return true; // Email sent and received successfully
			} else {
				return false; // Email sent, but failed to receive
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public boolean receiveEmail(Email email) {
		try {
			int count = 0;
			for (String recipientEmail : email.getRecipientIds()) {
				User user = userRepository.findByEmail(recipientEmail);
				Email receivedEmail = new Email();
				receivedEmail.setSenderId(email.getSenderId());
				receivedEmail.setSubject(email.getSubject());
				receivedEmail.setBody(email.getBody());
				receivedEmail.setImportant(false);
				receivedEmail.setRead(false);
				receivedEmail.setDeleted(false);
				receivedEmail.setSentDate(LocalDateTime.now());
				receivedEmail.setType("inbox");
				receivedEmail.setRecipientIds(email.getRecipientIds());
				receivedEmail.setUser(user);
				emailRepository.save(receivedEmail);
				count++;
			}
			if (count == email.getRecipientIds().size()) {
				return true;
			} else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Email markAsRead(Integer id) {
		try {
			Optional<Email> emailOptional = emailRepository.findById(id);
			if (emailOptional.isPresent()) {
				Email email = emailOptional.get();
				email.setRead(true);
				emailRepository.save(email);
				return email;
			} else {
				return null; // Email not found
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Email markIsImportant(Integer id) {
		try {
			Optional<Email> emailOptional = emailRepository.findById(id);
			if (emailOptional.isPresent()) {
				Email email = emailOptional.get();
				email.setImportant(true);
				emailRepository.save(email);
				return email;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Email unmarkIsImportant(Integer id) {
		try {
			Optional<Email> emailOptional = emailRepository.findById(id);
			if (emailOptional.isPresent()) {
				Email email = emailOptional.get();
				email.setImportant(false);
				emailRepository.save(email);
				return email;
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
