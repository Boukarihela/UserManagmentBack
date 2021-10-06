package com.example.usermanagmentback.dao.repositories;

import com.example.usermanagmentback.dao.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User,Long> {
    @Query(value ="select u from User u where u.email = :email")
    User findByEmail(@Param("email") String email);
}
