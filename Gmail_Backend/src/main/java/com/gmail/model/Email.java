package com.gmail.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "emails")
public class Email {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String senderId;
    private String subject;
    private String body;
    private LocalDateTime sentDate;
    private boolean isRead;
    private boolean isImportant;
    private boolean isDeleted;
    private List<String> recipientIds;
    private String type;
    @ManyToOne
    @JoinColumn(name = "email_id", referencedColumnName = "email")
    private User user;

    public Email() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Email(Integer id, String senderId, String subject, String body, LocalDateTime sentDate, boolean isRead,
			boolean isImportant, boolean isDeleted, List<String> recipientIds, String type, User user) {
		super();
		this.id = id;
		this.senderId = senderId;
		this.subject = subject;
		this.body = body;
		this.sentDate = sentDate;
		this.isRead = isRead;
		this.isImportant = isImportant;
		this.isDeleted = isDeleted;
		this.recipientIds = recipientIds;
		this.type = type;
		this.user = user;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSenderId() {
		return senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public LocalDateTime getSentDate() {
		return sentDate;
	}

	public void setSentDate(LocalDateTime sentDate) {
		this.sentDate = sentDate;
	}

	public boolean isRead() {
		return isRead;
	}

	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	public boolean isImportant() {
		return isImportant;
	}

	public void setImportant(boolean isImportant) {
		this.isImportant = isImportant;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<String> getRecipientIds() {
		return recipientIds;
	}

	public void setRecipientIds(List<String> recipientIds) {
		this.recipientIds = recipientIds;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
