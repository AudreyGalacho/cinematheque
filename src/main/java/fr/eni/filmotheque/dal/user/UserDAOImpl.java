package fr.eni.filmotheque.dal.user;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.bo.User;
import fr.eni.filmotheque.dal.participant.ParticipantDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);


    // JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<User> getUsersList() {
        List<User> listUsers = new ArrayList<>();

        String sql = "SELECT id, prenom , nom, email, admin FROM membres ";
        try {
            listUsers = jdbcTemplate.query(sql, new BeanPropertyRowMapper(Participant.class));
        } catch (Exception e) {
            logger.warn("Erreur dal getUsersList sql error");
            e.printStackTrace();
        }
        return listUsers;
    }



    public User getUserByUsername(String username) {
        Optional<User> userByUsernameOpt = Optional.of(new User());

        String sql = "SELECT id, prenom , nom, email, password, admin FROM membres WHERE email = ? ";

        try {
            userByUsernameOpt = (Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> new User(
                            rs.getLong(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getString(4),
                            rs.getString(5),
                            rs.getBoolean(6)),
                    username)));

        } catch (Exception e) {
            logger.warn("Erreur dal getUserByUsername sql error");
            e.printStackTrace();
        }

        // TODO faire remonter l'optional (retourner un Optional<User>)
        logger.info("-------------getUserByUsername DAO OK " + userByUsernameOpt.get());
        return userByUsernameOpt.get();
    }

}
