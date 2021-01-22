package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.CompetitionParticipantEntity;
import ru.aicontest.repository.CompetitionParticipantRepository;

@RestController
@RequestMapping("/api/1/participant")
public class CompetitionParticipantController {
    CompetitionParticipantRepository competitionParticipantRepository;

    public CompetitionParticipantController(CompetitionParticipantRepository competitionParticipantRepository) {
        this.competitionParticipantRepository = competitionParticipantRepository;
    }

    @GetMapping("{competitionId}/{participantLogin}/id")
    CompetitionParticipantEntity getCompetitionParticipantId(@PathVariable int competitionId, @PathVariable String participantLogin){
        CompetitionParticipantEntity participant = competitionParticipantRepository.getCompetitionParticipant(competitionId, participantLogin);
        if(participant == null)
        {
            competitionParticipantRepository.createCompetitionParticipant(competitionId, participantLogin);
            participant = competitionParticipantRepository.getCompetitionParticipant(competitionId, participantLogin);
        }
        return participant;
    }
}
