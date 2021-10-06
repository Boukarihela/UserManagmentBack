package com.example.usermanagmentback.services.interfaces.user;

import com.example.usermanagmentback.dao.entities.User;
import com.example.usermanagmentback.exceptions.RecordNotFoundException;

import java.util.List;

public interface UserService {

    long addUser(User user);
    User createOrUpdate(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    User findUserById(Long userId) throws RecordNotFoundException;

    void deleteUser(Long userId) throws RecordNotFoundException;

    boolean changePassword(String oldPassword, String newPassword, Long id);

    boolean ForgotPasswordByMail(String email);

    boolean changeForgotPassword(String token, String newPwd, String email);

    User findUserByEmail(String email) throws RecordNotFoundException;
}
