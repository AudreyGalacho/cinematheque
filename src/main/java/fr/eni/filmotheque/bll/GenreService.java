package fr.eni.filmotheque.bll;

import fr.eni.filmotheque.bo.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreService {
    List<Genre> getListeGenres();

    Genre findGenreById(Integer id) ;

}
