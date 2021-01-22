package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.repository.CompetitionParticipantRepository;

@RestController
@RequestMapping("/api/1/participant")
public class CompetitionParticipantController {
    CompetitionParticipantRepository competitionParticipantRepository;

    public CompetitionParticipantController(CompetitionParticipantRepository competitionParticipantRepository) {
        this.competitionParticipantRepository = competitionParticipantRepository;
    }

    @GetMapping("{competitionId}/{participantLogin}/id")
    int getCompetitionParticipantId(@PathVariable int competitionId, @PathVariable String participantLogin){
        Integer id = competitionParticipantRepository.getCompetitionParticipant(competitionId, participantLogin);
        if(id == null)
        {
            id = competitionParticipantRepository.createCompetitionParticipant(competitionId, participantLogin).get(0);
        }
        return id;
    }
}
