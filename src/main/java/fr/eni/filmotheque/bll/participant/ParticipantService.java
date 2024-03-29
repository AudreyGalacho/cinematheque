package fr.eni.filmotheque.bll.participant;

import fr.eni.filmotheque.bo.Participant;

import java.util.List;

public interface ParticipantService {

    List<Participant> getListeParticipants();
    Participant findParticipantById(Integer id);
}
