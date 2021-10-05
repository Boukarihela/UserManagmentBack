package application.configuration.securityConfig;


import dao.entities.User;
import dao.enums.Role;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import services.impl.user.UserServiceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Getter
@Setter
public class FileAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {

    private static final PasswordEncoder PASSWORD_ENCODER = new BCryptPasswordEncoder();

    @Autowired
    UserServiceImpl userService;

    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails,
                                                  UsernamePasswordAuthenticationToken authentication) {
        // nothing to do
    }

    @Override
    protected UserDetails retrieveUser(String email, UsernamePasswordAuthenticationToken authentication) {

        final List<User> users = userService.getAllUsers();
        final Optional<User> userOptional = findValidUser(email, users);
        //TO REMOVE
        PASSWORD_ENCODER.encode("admin");
        if (userOptional.isPresent()) {
            final User user = userOptional.get();
                if (PASSWORD_ENCODER.matches(authentication.getCredentials() + "", user.getPassword())) {

                    List<GrantedAuthority> grantedAuths = buildAuthoritiesList(user);
                return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
                        true, true, true, true, grantedAuths);
            } else {
                throw new BadCredentialsException("Invalid login or credentials");
            }
        }
        throw new BadCredentialsException("Invalid login or credentials");
    }

    private Optional<User> findValidUser(String email, List<User> users) {
        return users.stream().filter(user -> user.getEmail().equals(email)).findAny();
    }

    private List<GrantedAuthority> buildAuthoritiesList(User user) {
        final Role role = user.getRole();
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" +role.name()));
        return grantedAuthorities;
    }


}
