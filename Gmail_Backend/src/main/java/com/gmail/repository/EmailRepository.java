package com.gmail.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.model.Email;
import com.gmail.model.User;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {

	List<Email> findByUserAndType(User user, String type);

	List<Email> findByUserAndIsImportant(User existingUser, boolean b);

}
