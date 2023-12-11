package fr.eni.filmotheque.bll;

import fr.eni.filmotheque.bo.Genre;
import fr.eni.filmotheque.dal.genre.GenresDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class GenreServiceImpl implements GenreService{
    private GenresDAO genresDAO;
    @Autowired
    public GenreServiceImpl (GenresDAO genresDAO) {
        this.genresDAO =genresDAO;
    }

    //Methode implementeé
    public List<Genre> getListeGenres(){
        return genresDAO.getListGenres();
    }

    public Genre findGenreById(Integer id) {
        return genresDAO.findGenreById(id);
    }


}
