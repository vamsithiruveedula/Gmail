package com.gmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.gmail.model.Folder;

@Repository
public interface FolderRepository extends JpaRepository<Folder, String>{

}
