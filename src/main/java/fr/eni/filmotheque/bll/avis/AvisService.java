package fr.eni.filmotheque.bll.avis;

import fr.eni.filmotheque.bo.Avis;

import java.util.List;

public interface AvisService {
    List<Avis> getAvisByFilm(int idFilm);
    List<Avis> getAvisList();
}
