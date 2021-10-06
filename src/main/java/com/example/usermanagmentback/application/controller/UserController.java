package com.example.usermanagmentback.application.controller;

import com.example.usermanagmentback.application.configuration.securityConfig.JwtAuthenticationFilter;
import com.example.usermanagmentback.dao.entities.User;
import com.example.usermanagmentback.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.example.usermanagmentback.services.interfaces.user.UserService;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<User> addUser(@RequestBody User user){
       // logger.debug("Invocation de la resource : POST /User/");
        long id = 0;
        if (user != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User updatedUser= userService.createOrUpdate(user);
            return new ResponseEntity<User>(updatedUser, HttpStatus.CREATED);
        } else {
            return new ResponseEntity(id, HttpStatus.NO_CONTENT);
        }
    }

    @RequestMapping(value = "/update/{passUpdated}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateUser(@RequestBody User user, @PathVariable boolean passUpdated) {
        if (passUpdated)
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        userService.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/findByEmail/{email}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> finUserByEmail(@PathVariable("email") String email) throws RecordNotFoundException {
        User user = userService.findUserByEmail(email);
        if (user != null) {
            return new ResponseEntity<User>(user, HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/delete/{userId}")
    public HttpStatus deleteUserById(@PathVariable("userId") Long id)
            throws RecordNotFoundException {
        userService.deleteUser(id);
        return HttpStatus.FORBIDDEN;
    }
    @PostMapping("/signin/{email}/{password}")
    public Map login(
            @PathVariable("email") String email, //
            @PathVariable("password") String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        return new JwtAuthenticationFilter(authenticationManager).createToken(authentication);
    }

}
