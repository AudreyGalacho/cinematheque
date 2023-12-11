package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bll.FilmService;
import fr.eni.filmotheque.bll.GenreService;
import fr.eni.filmotheque.bo.Film;

import jakarta.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


@Controller
@SessionAttributes("genres")
public class FilmController {

    // Déclaration du logger
    Logger logger = LoggerFactory.getLogger(FilmController.class);


    // Déclaration des services utilisés
    public FilmService filmService;
    public GenreService genreService;

    // Constructeur du FilmController et injection des services
    @Autowired
    public FilmController(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
    }

    /**
     * Affiche une liste d'années pour la saisie d'un nouveau film
     * @return
     */
    @ModelAttribute("years")
    public List<Integer> populateYears() {
        int currentYear = LocalDate.now().getYear();
        return IntStream.rangeClosed(1930, currentYear)
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
    }

    //************************************************** ROUTES ***************************************
    @GetMapping(path = {"/"})
    public String accueil() {
        //retourne la vue souhaitée
        return "accueil";
    }

    /**
     * Methode qui affiche la liste des films (du Mook)
     *
     * @param model
     * @return
     */
    @RequestMapping(path = "/films", method = RequestMethod.GET)
    public String displayFilms(Model model) {//injection de Model
        // recuperation des films du service et DAO
        List<Film> films = filmService.findAllFilms();

        //mapping avec la vue
        model.addAttribute("films", films);
        logger.info("Demande de la liste des films");

        // affichage de la vue concernée
        return "films";
    }


    /**
     * Methode qui affiche le detail d'un film en fonction de son id
     * @param model
     * @param idFilm
     * @return
     */
    @RequestMapping(path = "/films/detail", method = RequestMethod.GET)
    public String displayFilmDetail(Model model, @RequestParam(name = "id") int idFilm) {  //injection de Model
        logger.info("Demande d'affichage d'un film d'id :" + idFilm);
        Optional<Film> filmOptional = filmService.findFilmById(idFilm);
        if (filmOptional.isEmpty()) {
            // affichage de la vue concernée
            logger.error("PAS de film à l'id demander " +idFilm);
            return "error_sql";
        } else {
            Film film = filmOptional.get();
            // mapping avec la vue
            model.addAttribute("film", film);
            // affichage de la vue concernée
            return "detail";
        }
    }

    //******************* GESTION DU FORMULAIRE ***************************************
    /**
     * Route d'affichage du formulaire d'un film
     * @param model
     * @return
     */
    @GetMapping(path = "/ajoutfilm")
    public String addFilm(Model model) {
        model.addAttribute("film", new Film());
        return "filmFormAdd";
    }


    /**
     * @param film
     * @return
     */
    @PostMapping(path = {"/ajoutfilm"})
    public String addFilm(@Valid @ModelAttribute("film") Film film, BindingResult validFilm){//Gestion de la validation des attributs
        if(validFilm.hasErrors()){
            logger.warn("Erreur dans la validation des données entrées par l'utilisateur");
            return "filmFormAdd";
        }
        filmService.addFilm(film);
        logger.info("Film correctement inséré");
        return "redirect:/films";
    }
}
