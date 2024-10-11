package com.gmail.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gmail.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

	User findByEmail(String email);

	User findByMobileNo(long mobileNo);

	boolean deleteByEmail(String emailId);

}
