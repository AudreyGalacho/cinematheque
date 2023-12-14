package fr.eni.filmotheque.bll.avis;

import fr.eni.filmotheque.bo.Avis;
import fr.eni.filmotheque.dal.avis.AvisDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AvisServiceImpl implements AvisService{

    private AvisDAO avisDAO;

    @Autowired
    public AvisServiceImpl(AvisDAO avisDAO) {
        this.avisDAO = avisDAO;
    }

    @Override
    public List<Avis> getAvisByFilm(int idFilm) {
        return avisDAO.getAvisByFilm(idFilm);
    }

    @Override
    public List<Avis> getAvisList() {
        return avisDAO.getAvisList();
    }
}
