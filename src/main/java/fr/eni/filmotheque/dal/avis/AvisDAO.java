package fr.eni.filmotheque.dal.avis;

import fr.eni.filmotheque.bo.Avis;

import java.util.List;

public interface AvisDAO {
    List<Avis> getAvisByFilm(int idFilm);
    List<Avis> getAvisList();
    void insertAvis(Avis Avis);
}
