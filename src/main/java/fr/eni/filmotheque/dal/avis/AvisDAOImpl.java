package fr.eni.filmotheque.dal.avis;

import fr.eni.filmotheque.bo.*;
import fr.eni.filmotheque.dal.film.FilmDAO;
import fr.eni.filmotheque.dal.user.UserDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AvisDAOImpl implements AvisDAO {

    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(AvisDAOImpl.class);

    private FilmDAO filmDAO;
    private UserDAO userDAO;

    @Autowired
    public AvisDAOImpl(FilmDAO filmDAO, UserDAO userDAO) {
        this.filmDAO = filmDAO;
        this.userDAO = userDAO;
    }


    // JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //ROW MAPPER AVIS
    private class AvisRowMapper implements RowMapper<Avis> {
        @Override
        public Avis mapRow(ResultSet rs, int rowNum) throws SQLException {
            Avis avis = new Avis();

            // map film
            int filmId = rs.getInt("id_film");
            Film film = new Film();
            film.setId(filmId);

            //map user
            Long userId = rs.getLong("id_membre");
            String userPrenom = rs.getString("prenom");
            String userNom = rs.getString("nom");
            User user = new User(userId, userPrenom, userNom);

            avis = new Avis(rs.getInt(1),
                    rs.getInt(2), // note
                    rs.getString(3), // commentaire
                    user,// membre
                    film// film
            );
            return avis;
        }
    }

    //TODO get Avis by film
    public List<Avis> getAvisByFilm(int idFilm) {
        List<Avis> listAvisByFilm = new ArrayList<>();

        String sql = "SELECT a.id, note , commentaire, id_membre, id_film , prenom, nom FROM avis AS a " +
                "JOIN films f ON f.id = id_film " +
                "JOIN membres m ON m.id = id_membre " +
                "WHERE id_film = ? ";

        try {
            listAvisByFilm =jdbcTemplate.query(
                    sql,
                    new AvisRowMapper(),
                    idFilm);
        } catch (Exception e) {
            logger.warn("-------------Erreur dal getAvisByFilm sql error");
            e.printStackTrace();
        }

        // TODO faire remonter l'optional (retourner un Optional<Avis>)
        logger.info("-------------getAvisByFilm DAO OK " + listAvisByFilm);
        return listAvisByFilm;
    }

    public List<Avis> getAvisList() {

        List<Avis> listAvis = new ArrayList<>();

        String sql = "SELECT a.id, note , commentaire, id_membre, id_film FROM avis AS a " +
                "JOIN films f ON f.id = id_film " +
                "JOIN membres m ON m.id = id_membre";
        try {
            listAvis = jdbcTemplate.query(sql, new AvisRowMapper());
        } catch (Exception e) {
            logger.warn("Erreur dal getAvisListFilm sql error");
            e.printStackTrace();
        }
        return listAvis;
    }

}


//    public void insertAvis(Avis Avis) {
//        String sql = "INSERT INTO membres (prenom, nom, email, password, admin) VALUES (?, ?, ?, ?, ?)";
//
//        try {
//            jdbcTemplate.update(sql,
//                    Avis.getPrenom(),
//                    Avis.getNom(),
//                    Avis.getAvisname(),
//                    Avis.getPassword(),
//                    Avis.getAdmin());
//            logger.info("-------------insertAvis DAO OK");
//        } catch (Exception e) {
//            logger.warn("---------------Erreur dal insertAvis sql error");
//            e.printStackTrace();
//        }
//    }

