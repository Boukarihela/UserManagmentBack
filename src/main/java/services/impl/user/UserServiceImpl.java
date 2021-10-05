package services.impl.user;

import dao.entities.User;
import dao.repositories.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import services.interfaces.user.UserService;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDao userDao;

    @Override
    public long addUser(User user) {
        User userDaoByEmail = userDao.findByEmail(user.getEmail());
        if (userDaoByEmail == null) {
            long id = userDao.saveAndFlush(user).getId();
            return id;
        } else {
            return 0L;
        }
    }


    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }

    @Override
    public Optional<User> findUserById(Long userId) {
        // logger.info(" Method find user by id started ");
        // Optional<User> userToFind = userDao.findById(userId);
        return Optional.ofNullable(userDao.findById(userId).get());
    }

    @Override
    public void deleteUser(Long userId) {
        Optional<User> userToDelete = userDao.findById(userId);
        if (userToDelete.isPresent()) {
            userDao.delete(userToDelete.get());
        } else {
           // logger.info(" User with ID  : {}", userId + " NOT Found  ");
            System.out.println("no");
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
    public Optional<User> findUserByEmail(String email) {
        return Optional.ofNullable(userDao.findByEmail(email));

    }
}
