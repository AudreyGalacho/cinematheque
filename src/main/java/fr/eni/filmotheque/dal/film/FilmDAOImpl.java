package fr.eni.filmotheque.dal.film;

import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.dal.genre.GenresDAO;
import fr.eni.filmotheque.dal.participant.ParticipantDAO;
import fr.eni.filmotheque.dal.participant.ParticipantDAOImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.*;

@Repository
public class FilmDAOImpl implements FilmDAO {
    // LOGGER trace use
    Logger logger = LoggerFactory.getLogger(FilmDAOImpl.class);

    private GenresDAO genresDAO;
    private ParticipantDAO participantDAO;

    @Autowired
    public FilmDAOImpl(GenresDAO genresDAO, ParticipantDAO participantDAO) {
        this.genresDAO = genresDAO;
        this.participantDAO = participantDAO;
    }

    // ***************************** JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    // ****************************** MAPPER FILM ********************************************************
    // FILM ROW MAPPER avec multiple requête sql


//    private class FilmRowMapper implements RowMapper<Film> {
//        @Override
//        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
//
//            Optional<Genre> optGenre = genreDAO.findGenreById(rs.getLong(7));
//            Genre genre = optGenre.get();
//
//            Optional<Participant> optRealisateur = participantDAO.findParticipantById(rs.getLong(6));
//            Participant realisateur = optRealisateur.get();
//
//            Film film = new Film(rs.getLong(1), rs.getString(2), // titre
//                    rs.getString(5), // synopsis
//                    rs.getInt(3), // duree
//                    rs.getInt(4), // annee
//                    genre, // Genre
//                    realisateur// realisateur
//            );
//            film.setActeurs(findActeurs(film.getId()));
//
//            return film;
//        }
//    }

    // FILM ROW MAPPER qui utilise la jointure pour recuperer toutes les informations d'un film
    private class FilmRowMapper implements RowMapper<Film> {
        @Override
        public Film mapRow(ResultSet rs, int rowNum) throws SQLException {
            Film film = new Film();
            film.setId(rs.getInt("id"));
            film.setTitre(rs.getString("titre"));
            film.setAnnee(rs.getInt("annee"));
            film.setDuree(rs.getInt("duree"));
            film.setSynopsis(rs.getString("synopsis"));

            // map genre
            int genreId = rs.getInt("id_genre");
            String genreLibelle = rs.getString("g_libelle");
            Genre genre = new Genre(genreId, genreLibelle);
            film.setGenre(genre);

            //map realisateur
            int realisateurId = rs.getInt("id_realisateur");
            String realisateurPrenom = rs.getString("realisateur_prenom");
            String realisateurNom = rs.getString("realisateur_nom");

            Participant realisateur = new Participant(realisateurId, realisateurPrenom, realisateurNom);
            film.setRealisateur(realisateur);

            // map acteurs (Utilisation d'une methode dans ParticipantDAOIMpl => requete sql pour récupérer la liste des acteurs du film)
            List<Participant> acteurs = participantDAO.getListActeursByFilm(film.getId());
            film.setActeurs(acteurs);

            return film;
        }
    }
    // ******************************** METHODES SQL *******************************************************

    /**
     * Methode qui retourne une liste des Film présent en base de données
     * @return
     */
    @Override
    public List<Film> findAllFilms() {
        List<Film> listFilms = new ArrayList<>();
        // requete
        String sql = "SELECT f.id, f.titre, f.annee, f.duree, f.synopsis, " +
                "f.id_genre, g.libelle as g_libelle, " + //GENRE
                "f.id_realisateur, p.prenom as realisateur_prenom, p.nom as realisateur_nom " + //REALISATEUR
                "FROM films f " +
                "JOIN genres g ON f.id_genre = g.id " + //Jointure Genre
                "JOIN participants p On f.id_realisateur = p.id ";// jointure Participants
        try {
            // Utilisation du RowMapper personnalisé
            listFilms = jdbcTemplate.query(sql, new FilmRowMapper());

        } catch (Exception e) {
            System.out.println("Erreur DAO!!!findAllFilms(): ");
            e.printStackTrace();
        }
        logger.info("Liste de film bien récupérée");
        return listFilms;
    }

    /**
     * Methode qui retourne un Optional<Film> suivant son Id
     * @param idFilm
     * @return
     */
    @Override
    public Optional<Film> findFilmById(int idFilm) {
        Optional<Film> filmById = Optional.of(new Film());
        String sql = "SELECT f.id, f.titre, f.annee, f.duree, f.synopsis, " +
                "f.id_genre, g.libelle as g_libelle, " + //GENRE
                "f.id_realisateur, p.prenom as realisateur_prenom, p.nom as realisateur_nom " + //REALISATEUR
                "FROM films f " +
                "JOIN genres g ON f.id_genre = g.id " + //Jointure Genre
                "JOIN participants p On f.id_realisateur = p.id " +// jointure Participants
                "WHERE f.id = ? ";

        /* Utilisation d'un RowMapper pour faire correspondre le résultat de la requête avec un objet */
        try {
            filmById = (Optional.ofNullable(jdbcTemplate.queryForObject(
                    sql,
                    new FilmRowMapper(),
                    idFilm)));
        } catch (Exception e) {
            System.out.println("Pas de Film d'id :" + idFilm + "/n");
            e.printStackTrace();
        }
        return filmById;
    }


    @Override
    public void addFilm(Film film) {
        // TODO verifier que le titre + année pas dejà present en base
        System.out.println("ADDING FILM");
        System.out.println(film);

        String sql = "INSERT INTO films (titre, annee, duree, synopsis, id_realisateur, id_genre)" +
                "VALUES (?, ?, ?, ?, ?, ?)";
        // Pour récupérer l'id généré lors de l'insertion du film
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Preparation de la requete
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, film.getTitre());
                ps.setInt(2, film.getAnnee());
                ps.setInt(3, film.getDuree());
                ps.setString(4, film.getSynopsis());
                ps.setInt(5, film.getRealisateur().getId());
                ps.setInt(6,film.getGenre().getId());
        return ps;
        }, keyHolder);
        // Recup de l'id généré et modification
        film.setId(keyHolder.getKey().intValue());
        //ajout des acteurs
        addActeurs(film);
        logger.info("film bien enregisté");

    }


    public void addActeurs(Film film) {
        List<Participant> acteurs = film.getActeurs();
        String sql = "INSERT INTO acteurs (id_film , id_participant) VALUES (?, ?)";

        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, film.getId());
                ps.setInt(2, acteurs.get(i).getId());
            }
            @Override
            public int getBatchSize() {
                return acteurs.size();
            }
        });
        logger.info("acteur(s) bien enregisté(s)");

    }

}