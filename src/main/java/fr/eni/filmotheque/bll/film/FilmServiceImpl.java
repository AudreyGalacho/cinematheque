package fr.eni.filmotheque.bll.film;

import java.util.*;

import org.springframework.stereotype.Service;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.dal.film.FilmDAO;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FilmServiceImpl implements FilmService{
	private FilmDAO filmDao;
	
	//@Autowired
	public FilmServiceImpl(FilmDAO filmDao) {
		this.filmDao = filmDao;
	}
	//****************************** Methodes implementées ********************

	@Override
	public Optional<Film> findFilmById(int id) {
		return filmDao.findFilmById(id);
	}

	@Override
	public List<Film> findAllFilms() {
		List<Film>films = filmDao.findAllFilms();
		return films;
	}

	@Override
	@Transactional
	public void addFilm(Film film) {
		filmDao.addFilm(film);

	}



/* ****************************************************************** */


}
