package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.CompetitionParticipantEntity;
import ru.aicontest.repository.CompetitionParticipantRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/participant")
public class CompetitionParticipantController {
    CompetitionParticipantRepository competitionParticipantRepository;

    public CompetitionParticipantController(CompetitionParticipantRepository competitionParticipantRepository) {
        this.competitionParticipantRepository = competitionParticipantRepository;
    }

    @GetMapping("{competitionId}/{participantLogin}/id")
    CompetitionParticipantEntity getCompetitionParticipantId(@PathVariable int competitionId, @PathVariable String participantLogin) {
        CompetitionParticipantEntity participant = competitionParticipantRepository.getCompetitionParticipant(competitionId, participantLogin);
        if (participant == null) {
            competitionParticipantRepository.createCompetitionParticipant(competitionId, participantLogin);
            participant = competitionParticipantRepository.getCompetitionParticipant(competitionId, participantLogin);
        }
        return participant;
    }

    @GetMapping("/competition/{competitionId}/all-participants")
    List<CompetitionParticipantEntity> getAllCompetitionParticipants(@PathVariable int competitionId) {
        return competitionParticipantRepository.getAllCompetitionParticipants(competitionId);
    }

    @GetMapping("{participantId}/competitive-result")
    CompetitionParticipantRatingPositionDTO getCompetitiveResult(@PathVariable int participantId) {
        CompetitionParticipantRatingPositionDTO result = new CompetitionParticipantRatingPositionDTO();

        int competitionId = competitionParticipantRepository.getCompetitionIdOfParticipant(participantId);
        result.setAllParticipants(competitionParticipantRepository.getAllCompetitionParticipants(competitionId).size());
        result.setPosition(competitionParticipantRepository.getParticipantPosition(competitionId, participantId));

        return result;
    }

    private static class CompetitionParticipantRatingPositionDTO {
        private int position;
        private int allParticipants;

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getAllParticipants() {
            return allParticipants;
        }

        public void setAllParticipants(int allParticipants) {
            this.allParticipants = allParticipants;
        }
    }
}
