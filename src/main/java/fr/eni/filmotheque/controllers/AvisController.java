package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bll.avis.AvisService;
import fr.eni.filmotheque.bll.film.FilmService;
import fr.eni.filmotheque.bll.user.UserService;
import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.User;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;


@Controller
@SessionAttributes("avis")
public class AvisController {
    // Déclaration du logger
    Logger logger = LoggerFactory.getLogger(AvisController.class);

    public UserService userService;
    public AvisService avisService;
    public FilmService filmService;

    @Autowired
    public AvisController(UserService userService, AvisService avisService, FilmService filmService) {
        this.userService = userService;
        this.avisService = avisService;
        this.filmService = filmService;
    }

    //************************************************** ROUTES ***************************************
    //******************* GESTION DU FORMULAIRE ***************************************
    @GetMapping(path = {"/add-avis"})
    public String ajoutDAvis(Model model, Principal principal, @RequestParam(name = "film") int idFilm) {

        logger.info("----------GET avis form");

        // Verif si il y a déjà un avis de l'utilisateur connecté
        User userLog = userService.getUserByUsername(principal.getName());

        // verif film id et récupération
        Optional<Film> filmOpt = filmService.findFilmById(idFilm);

        if (filmOpt.isEmpty()) {
            logger.warn("----------PAS DE FILM par id en parametre");
            // TODO message il n'y a pas de film correspondant
            return "redirect:/films";
        } else {
            List<Avis> avisList = avisService.getAvisByFilm(idFilm);
//            if (avisList.contains(userLog)) {
//                logger.warn("-------------------Avis liste contient le user de session");
//
//                // TODO AJOUTER un message d'erreur a l'utilisateur connecté
//                return "redirect:/films";
//            }
            Avis avis = new Avis();
            avis.setFilm(filmOpt.get());
            avis.setUser(userLog);
            model.addAttribute("avis", avis);

            return "avisFormAdd";
        }
    }


    @PostMapping(path = {"/add-avis"})
    public String handleFormAvis(@Valid @ModelAttribute("avis") Avis avis,
                                 BindingResult avisForm) {
        if (avisForm.hasErrors()) {
            logger.warn("----------Erreur dans la validation des données de l'AVIS entrées par l'utilisateur");
            return "avisFormAdd";
        }
        logger.info("---------------------------------"+avis);

        avisService.insertAvis(avis);
        return "redirect:/films";

    }


}
