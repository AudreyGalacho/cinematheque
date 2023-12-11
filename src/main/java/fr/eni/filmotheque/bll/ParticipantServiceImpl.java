package fr.eni.filmotheque.bll;

import fr.eni.filmotheque.bo.Participant;
import fr.eni.filmotheque.dal.participant.ParticipantDAO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private ParticipantDAO participantDAO;

    public ParticipantServiceImpl(ParticipantDAO participantDAO) {
        this.participantDAO = participantDAO;
    }

    @Override
    public List<Participant> getListeParticipants() {
        return participantDAO.getParticipantsList();
    }

    public Participant findParticipantById(Integer id) {
        return participantDAO.getParticipantById(id);
    }
}
