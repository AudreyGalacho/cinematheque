package fr.eni.filmotheque.bll.film;

import java.util.List;
import java.util.Optional;

import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.Participant;

public interface FilmService {
	Optional<Film> findFilmById(int id);
	List<Film> findAllFilms();
	void addFilm(Film film);


}
