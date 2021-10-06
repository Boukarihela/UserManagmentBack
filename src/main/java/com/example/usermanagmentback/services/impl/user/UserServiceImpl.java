package com.example.usermanagmentback.services.impl.user;

import com.example.usermanagmentback.dao.entities.User;
import com.example.usermanagmentback.dao.repositories.UserDao;
import com.example.usermanagmentback.exceptions.RecordNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.usermanagmentback.services.interfaces.user.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;
    private static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public long addUser(User user) {

        Optional<User> userDaoByEmail = userDao.findByEmail(user.getEmail());
        if (userDaoByEmail == null) {
            long id = userDao.saveAndFlush(user).getId();
            return id;
        } else {
            return 0L;
        }
    }

    @Override
    public User createOrUpdate(User user) {
        Optional<User> optionalUser = userDao.findById(user.getId());

        if (optionalUser.isPresent()) {
            User newUser = optionalUser.get();
            newUser.setUsername(user.getUsername());
            newUser.setEmail(user.getEmail());
            newUser.setRole(user.getRole());
            newUser.setPassword(user.getPassword());
            newUser = userDao.save(newUser);
            return newUser;
        } else {
            user = userDao.save(user);
            return user;
        }
    }

    @Override
    public User updateUser(User user) {
//        Optional<User> userToUpdate = userDao.findById(user.getId());
//        if (userToUpdate.isPresent()) {
//            logger.info(" ID of User To Update : {}", userToUpdate.get().getId() + " ");
//            userDao.
//        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = userDao.findAll();
        if (userList.size() > 0) {
            return userList;
        } else {
            return new ArrayList<User>();
        }

    }

    @Override
    public User findUserById(Long userId) throws RecordNotFoundException {
        Optional<User> user = userDao.findById(userId);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No User  exist for given id");
        }
    }

    @Override
    public void deleteUser(Long userId) throws RecordNotFoundException {
        Optional<User> user = userDao.findById(userId);

        if (user.isPresent()) {
            userDao.deleteById(userId);
        } else {
            throw new RecordNotFoundException("No user  exist for given id");
        }
    }

    @Override
    public boolean changePassword(String oldPassword, String newPassword, Long id) {
        return false;
    }

    @Override
    public boolean ForgotPasswordByMail(String email) {
        return false;
    }

    @Override
    public boolean changeForgotPassword(String token, String newPwd, String email) {
        return false;
    }

    @Override
    public User findUserByEmail(String email) throws RecordNotFoundException {
        logger.info("Get user by email" + email);
        Optional<User> user = userDao.findByEmail(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RecordNotFoundException("No User  exist for given id");
        }

    }
}
