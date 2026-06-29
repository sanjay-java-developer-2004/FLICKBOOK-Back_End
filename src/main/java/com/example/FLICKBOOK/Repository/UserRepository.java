package com.example.FLICKBOOK.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.FLICKBOOK.Model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);



}
