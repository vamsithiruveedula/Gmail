package com.gmail.service;

import java.util.List;

import com.gmail.model.Folder;

public interface FolderService {

	Folder saveFolder(Folder folder);

	List<Folder> getFolders();

}
