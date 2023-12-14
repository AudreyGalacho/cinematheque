package fr.eni.filmotheque.controllers;

import fr.eni.filmotheque.bll.avis.AvisService;
import fr.eni.filmotheque.bll.film.FilmService;
import fr.eni.filmotheque.bll.genre.GenreService;
import fr.eni.filmotheque.bll.user.UserService;
import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.bo.Film;
import fr.eni.filmotheque.bo.User;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.PrimitiveIterator;

@Controller
public class AvisController {
    // Déclaration du logger
    Logger logger = LoggerFactory.getLogger(AvisController.class);

    public UserService userService;
    public AvisService avisService;
    @Autowired
    public AvisController(UserService userService, AvisService avisService) {
        this.userService = userService;
        this.avisService = avisService;
    }
    //************************************************** ROUTES ***************************************

    @GetMapping(path = {"/add-avis"})
    public String ajoutDAvis(Principal principal, @RequestParam(name = "film") int idFilm) {

        // Verif si il y a déjà un avis de l'utilisateur connecté
        User userLog = userService.getUserByUsername(principal.getName());

        List<Avis> avisList = avisService.getAvisByFilm(idFilm);
        if ( avisList.isEmpty() && !(avisList.contains(userLog)) ){
            logger.info("-------------------Avis liste Vide et ne contient pas le user de session");
            return "form-avis";
        } else {
            // TODO AJOUTER un message d'erreur a l'utilisateur
            return  "redirect:/films";
        }

    }


}
