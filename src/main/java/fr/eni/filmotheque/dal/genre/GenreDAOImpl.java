package fr.eni.filmotheque.dal.genre;

import fr.eni.filmotheque.bo.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.util.*;

@Repository
public class GenreDAOImpl implements GenresDAO {
    private List<Genre> listeGenres;
    private Map<Integer, Genre> mapGenres;


    //    JDBC CONNEXION *************************************************
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    //    METHODES INTERFACEES

    /**
     * Récupère le Genre (id, libelle) ayant pour id @param
     * @param id
     * @return Genre
     */
    @Override
    public Genre findGenreById(Integer id) {

        Optional<Genre> genreById = Optional.of(new Genre());
        //Genre genreById = new Genre();
        String sql = "SELECT id, libelle FROM genres WHERE id = ? ";

        /* Utilisation d'un RowMapper pour faire correspondre le résultat de la requête avec un objet */
        try {
            genreById = (Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    (ResultSet rs, int rowNum) -> new Genre(rs.getInt(1), rs.getString(2)),
                    id)));
        } catch (Exception e) {
            System.out.println("Erreur !!!: findGenreById() DAO ");
            e.printStackTrace();
        }

        return genreById.get();
    }

    /** Récupère la liste (id, libelle) des genres insérés en base
     * @return List Genre
     */
    @Override
    public List<Genre> getListGenres() {
        List<Genre> listeGenres = new ArrayList<>();

        // requete
        String sql = "SELECT id, libelle FROM genres ";
        try {
            listeGenres = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Genre.class));
        } catch (Exception e) {
            System.out.println("Erreur !!!: ");
            e.printStackTrace();
        }
        return listeGenres;
    }


}
