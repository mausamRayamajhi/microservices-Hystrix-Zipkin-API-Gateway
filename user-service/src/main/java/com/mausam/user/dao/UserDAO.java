package com.mausam.user.dao;


import com.mausam.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserDAO extends CrudRepository<User, Long> {

    User findByEmail(String email);

    User findByEmailAndStatus(String email, int status);

    List<User> findByRoleAndStatus(String role, int status);

    @Query("SELECT User FROM User User")
    Page<User> findAllUser(Pageable pageable);

    Page<User> findByStatus(int status, Pageable pageable);

    Page<User> findByRole(String role, Pageable pageable);


    //Below Method Is For Forget Password Use Only
    User findByEmailAndVerificationAndUserId(String email, String verificationCode, long id);
}
