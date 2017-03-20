package by.javateam.service.Impl;

import by.javateam.model.DeveloperUser;
import by.javateam.model.UserRoleEnum;
import by.javateam.service.DeveloperUserService;
import by.javateam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;

/**
 * Created by Erizo.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final DeveloperUserService developerUserService;

    @Autowired
    public UserDetailsServiceImpl(DeveloperUserService developerUserService) {
        this.developerUserService = developerUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        DeveloperUser developerUser = developerUserService.findByLogin(login);
        HashSet<SimpleGrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_USER.name()));
        roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ADMIN.name()));
        roles.add(new SimpleGrantedAuthority(UserRoleEnum.ROLE_ANONYMOUS.name()));
        return new org.springframework.security.core.userdetails.User(developerUser.getLogin(), developerUser.getPassword(), roles);
    }
}