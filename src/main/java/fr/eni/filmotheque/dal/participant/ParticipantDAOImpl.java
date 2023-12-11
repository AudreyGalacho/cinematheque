package fr.eni.filmotheque.dal.participant;

import fr.eni.filmotheque.bo.Participant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class ParticipantDAOImpl implements ParticipantDAO{

    Logger logger = LoggerFactory.getLogger(ParticipantDAOImpl.class);


    //    JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Participant> getListActeursByFilm(Integer idFilm) {
        List<Participant> listeActeurs= new ArrayList<>();

        String sql = "SELECT p.id as id_acteur , prenom, nom " +
                "FROM acteurs a " +
                "JOIN participants p ON p.id =  a.id_participant " +
                "WHERE a.id_film = ?";
        try {
            listeActeurs = jdbcTemplate.query(sql,new BeanPropertyRowMapper<>(Participant.class),
                    idFilm ); // PARAMETRE
        } catch (Exception e) {
            logger.warn("Erreur DAO!! getListActeursByFilm : ");
            e.printStackTrace();
        }
        return listeActeurs;
    }

    public List<Participant> getParticipantsList() {
        List<Participant> listeParticipant = new ArrayList<>();

        // requete
        String sql = "SELECT id, prenom , nom FROM participants ";
        try {
            listeParticipant = jdbcTemplate.query(sql, new BeanPropertyRowMapper (Participant.class));
        } catch (Exception e) {
            logger.warn("Erreur dal getParticipantsList() sql error");
            e.printStackTrace();
        }
        return listeParticipant;
    }
    public Participant getParticipantById(Integer id){
        Optional<Participant> participantById = Optional.of(new Participant());

        String sql = "SELECT id, prenom, nom FROM participants WHERE id = ? ";

        /* Utilisation d'un RowMapper pour faire correspondre le résultat de la requête avec un objet */
        try {
            participantById = (Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> new Participant(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)),
                    id)));
        } catch (Exception e) {
            logger.warn("Erreur dal getParticipantById() sql error");
            e.printStackTrace();
        }

        return participantById.get();
    }
}
