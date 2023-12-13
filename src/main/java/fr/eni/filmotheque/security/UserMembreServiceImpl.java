package fr.eni.filmotheque.security;

import fr.eni.filmotheque.bo.User;
import fr.eni.filmotheque.dal.user.UserDAO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component("userDetailService")// renomage pour que le bean est le même nom
public class UserMembreServiceImpl implements UserDetailsService {

    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(UserMembreServiceImpl.class);
    private UserDAO userDAO;

    @Autowired
    public UserMembreServiceImpl(UserDAO userDAO) {
        logger.info("-------------FilmothequeUserService");
        this.userDAO = userDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("-------------UserDetails loadUserByUsername");
        User user = userDAO.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        logger.info("-------------UserDetails loadUserByUsername OK " + user);
        return new UserMembreUserDetail(user);
    }





}
