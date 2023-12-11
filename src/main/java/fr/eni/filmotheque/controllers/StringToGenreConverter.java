package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bll.GenreService;
import fr.eni.filmotheque.bo.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;


import java.util.Optional;


@Component //Définir le converter en tant que bean Spring
public class StringToGenreConverter implements Converter<String, Genre> {//Implementer Converter

    private GenreService genreService;

    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    @Override
    public Genre convert(String id) {

        Integer idConvert = Integer.parseInt(id);
        Optional<Genre> genreOptional = Optional.ofNullable(genreService.findGenreById(idConvert));
        Genre genre = new Genre();
        // affichage de la vue concernée
        genre = genreOptional.get();
        return genre;

    }


}
