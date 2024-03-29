package fr.eni.filmotheque.dal.film;

import java.util.List;
import java.util.Optional;

import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.Participant;

public interface FilmDAO {
	Optional<Film> findFilmById(int id);
	List<Film> findAllFilms();
	void addFilm(Film film);

}
