package fr.eni.filmotheque.security;

import fr.eni.filmotheque.bo.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserMembreUserDetail  implements UserDetails {

    Logger logger = LoggerFactory.getLogger(UserMembreServiceImpl.class);
    private User user;

    public UserMembreUserDetail(User user) {
        logger.info("----------construct UserMembreUserDetail");
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        logger.info("-------------UserDetails MyUserPrincipal");

        List<GrantedAuthority> authorities  = new ArrayList<>();
        if (user.getAdmin()){
            authorities .add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        authorities .add(new SimpleGrantedAuthority("ROLE_USER"));
        // Retourne la liste des rôles ou autorités de l'utilisateur.
        logger.info(authorities .toString());
        return authorities ;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}

