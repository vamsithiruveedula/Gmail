package com.gmail.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmail.model.Folder;
import com.gmail.repository.FolderRepository;
import com.gmail.service.FolderService;
@Service
public class FolderServiceImpl implements FolderService {

	@Autowired
	private FolderRepository folderRepository;
	
	@Override
	public Folder saveFolder(Folder folder) {
		try {
			if (folder != null) {
				return folderRepository.save(folder);
			} else {
				return null;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public List<Folder> getFolders() {
		try {
			return folderRepository.findAll();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	
}
