package fr.eni.filmotheque.config;

import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GestionErreurs {

    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public String gereToutesLesErreurs(Exception dae) {
        System.out.println("Erreur inattendue : " + dae);
        return "error";
    }

}
