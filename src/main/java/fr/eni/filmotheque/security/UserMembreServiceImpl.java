package fr.eni.filmotheque.security;

import fr.eni.filmotheque.bll.user.UserService;
import fr.eni.filmotheque.bo.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;



@Component("userDetailService")// renomage pour que le bean est le même nom
public class UserMembreServiceImpl implements UserDetailsService {

    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(UserMembreServiceImpl.class);
    private UserService userService;

    @Autowired
    public UserMembreServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new UserMembreUserDetail(user);
    }





}
