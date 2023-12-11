package fr.eni.filmotheque.dal.genre;

import fr.eni.filmotheque.bo.Genre;

import java.util.List;

public interface GenresDAO {
    public List<Genre> getListGenres();

    Genre findGenreById(Integer id) ;

}
