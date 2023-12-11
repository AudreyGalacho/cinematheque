package fr.eni.filmotheque.config;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;


@ControllerAdvice
public class GestonErreurs {

    @ResponseStatus(code= HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({DataAccessException.class})
    public String gereToutesLesErreurs(DataAccessException dae) {
        System.out.println("Erreur inattendue : " + dae);
        return "error_sql";
    }

}
