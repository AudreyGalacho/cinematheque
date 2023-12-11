package fr.eni.filmotheque.dal.participant;

import fr.eni.filmotheque.bo.Participant;

import java.util.List;

public interface ParticipantDAO {

    List<Participant> getListActeursByFilm(Integer idFilm);

    List<Participant> getParticipantsList();

    Participant getParticipantById(Integer id);
}
