package fr.eni.filmotheque.controllers;



import fr.eni.filmotheque.bll.ParticipantService;
import fr.eni.filmotheque.bo.Participant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StringToActeursConverter implements Converter<String, Participant> {
    private ParticipantService participantService;

    @Autowired
    public void setFilmService( ParticipantService participantService ) {
        this.participantService = participantService;
    }

    @Override
    public Participant convert(String id) {
        Integer idConvert = Integer.parseInt(id);

        Optional<Participant> participantOptional = Optional.ofNullable(participantService.findParticipantById(idConvert));

        Participant participant = participantOptional.get();
        return participant;
    }



}
