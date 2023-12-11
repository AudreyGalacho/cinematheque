package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bll.FilmService;
import fr.eni.filmotheque.bll.GenreService;
import fr.eni.filmotheque.bo.Film;

import jakarta.validation.Valid;
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

    public FilmService filmService;
    public GenreService genreService;

    @Autowired
    public FilmController(FilmService filmService, GenreService genreService) {
        this.filmService = filmService;
        this.genreService = genreService;
    }

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

        // affichage de la vue concernée
        return "films";
    }

    //@RequestMapping(path = "/films/detail{id}", method = RequestMethod.GET)
    //public String displayFilmDetail(Model model, @PathVariable(name= "id") int idFilm) {

    /**
     * Methode qui le detail d'un film en fonction de son id
     *
     * @param model
     * @param idFilm
     * @return
     */
    @RequestMapping(path = "/films/detail", method = RequestMethod.GET)
    public String displayFilmDetail(Model model, @RequestParam(name = "id") int idFilm) {  //injection de Model

        Optional<Film> filmOptional = filmService.findFilmById(idFilm);
        if (filmOptional.isEmpty()) {
            // affichage de la vue concernée
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
     * Methode d'affichage du formulaire d'un film
     * @param model
     * @return
     */
    @GetMapping(path = "/ajoutfilm")
    public String addFilm(Model model) {
//        if(Binding.hasError){
//
//        }
        model.addAttribute("film", new Film());

        return "filmFormAdd";
    }

    //    SUBMIT FORM

    /**
     *
     * @param film
     * @return
     */
    @PostMapping(path = {"/ajoutfilm"})
    public String addFilm(@Valid @ModelAttribute("film") Film film, BindingResult validFilm){//Gestion de la validation des attributs
        if(validFilm.hasErrors()){
            System.out.println("ERREUR");
            return "filmFormAdd";
        }

        filmService.addFilm(film);

        System.out.println(film);
        return "redirect:/films";
    }
}
