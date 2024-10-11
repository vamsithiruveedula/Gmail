package com.gmail.model;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "folders")
public class Folder {

	@Id
	private String folderName;
	private List<Integer> emailIds;
	
	public String getFolderName() {
		return folderName;
	}
	public void setFolderName(String folderName) {
		this.folderName = folderName;
	}
	public List<Integer> getEmailIds() {
		return emailIds;
	}
	public void setEmailIds(List<Integer> emailIds) {
		this.emailIds = emailIds;
	}
	public Folder() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Folder(String folderName, List<Integer> emailIds) {
		super();
		this.folderName = folderName;
		this.emailIds = emailIds;
	}
	
}
