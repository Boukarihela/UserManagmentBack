package com.example.usermanagmentback.services.interfaces.user;

import com.example.usermanagmentback.dao.entities.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

    long addUser(User user);

    User updateUser(User user);

    List<User> getAllUsers();

    Optional<User> findUserById(Long userId);

    void deleteUser(Long userId);

    boolean changePassword(String oldPassword, String newPassword, Long id);

    boolean ForgotPasswordByMail(String email);

    boolean changeForgotPassword(String token, String newPwd, String email);

    Optional<User>  findUserByEmail(String email);
}
