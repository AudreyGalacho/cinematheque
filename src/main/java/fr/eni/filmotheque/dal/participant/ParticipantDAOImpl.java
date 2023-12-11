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

    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(ParticipantDAOImpl.class);


    // JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // Methodes implementee *********************************************

    /**
     * Methode de récupération de la liste des acteurs par l'id du film
     * @param idFilm
     * @return
     */
    // TODO retourne un optionnal (tout les film n'ont pas des acteurs
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

    /**
     * Méthode de récupérationde la liste des participants d'un film(Réalisateur ou Acteurs)
     * @return
     */
    public List<Participant> getParticipantsList() {
        List<Participant> listeParticipant = new ArrayList<>();

        String sql = "SELECT id, prenom , nom FROM participants ";
        try {
            listeParticipant = jdbcTemplate.query(sql, new BeanPropertyRowMapper (Participant.class));
        } catch (Exception e) {
            logger.warn("Erreur dal getParticipantsList() sql error");
            e.printStackTrace();
        }
        return listeParticipant;
    }

    /**
     * Méthode de récupération des information d'un participant par son id (Optional)
     * @param id
     * @return
     */
    public Participant getParticipantById(Integer id){
        Optional<Participant> participantByIdOpt = Optional.of(new Participant());

        String sql = "SELECT id, prenom, nom FROM participants WHERE id = ? ";

        try {
            participantByIdOpt = (Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> new Participant(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3)),
                    id)));
//            // Si récup ok
//            Participant participantById = participantByIdOpt.get();
//            return participantById;

        } catch (Exception e) {
            logger.warn("Erreur dal getParticipantById() sql error");
            e.printStackTrace();
        }

        // TODO faire remonter l'optional (retourner un Optional<Participant>)
        return participantByIdOpt.get();
    }
}
