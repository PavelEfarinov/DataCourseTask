package ru.aicontest.controller;

import org.springframework.web.bind.annotation.*;
import ru.aicontest.domain.MatchEntity;
import ru.aicontest.repository.MatchRepository;
import ru.aicontest.service.MatchService;

import java.util.List;

@RestController
@RequestMapping("/api/1/match")
public class MatchController {
    MatchRepository matchRepository;
    MatchService matchService;

    public MatchController(MatchRepository matchRepository, MatchService matchService) {
        this.matchRepository = matchRepository;
        this.matchService = matchService;
    }

    @GetMapping("participant/{participantId}/all-matches")
    public List<MatchEntity> getMatchesWithParticipant(@PathVariable int participantId){
        return matchRepository.getAllParticipantMatches(participantId);
    }

    @PostMapping("create-match")
    public List<MatchEntity> createNewMatch(@PathVariable int participantId){
        return matchRepository.getAllParticipantMatches(participantId);
    }

    private static class NewMatchRequestDTO
    {
        private int creatorParticipantId;
        private int opponentParticipantId;
        private int creatorSolutionId;
        private int opponentSolutionId;

        public int getCreatorParticipantId() {
            return creatorParticipantId;
        }

        public void setCreatorParticipantId(int creatorParticipantId) {
            this.creatorParticipantId = creatorParticipantId;
        }

        public int getOpponentParticipantId() {
            return opponentParticipantId;
        }

        public void setOpponentParticipantId(int opponentParticipantId) {
            this.opponentParticipantId = opponentParticipantId;
        }

        public int getCreatorSolutionId() {
            return creatorSolutionId;
        }

        public void setCreatorSolutionId(int creatorSolutionId) {
            this.creatorSolutionId = creatorSolutionId;
        }

        public int getOpponentSolutionId() {
            return opponentSolutionId;
        }

        public void setOpponentSolutionId(int opponentSolutionId) {
            this.opponentSolutionId = opponentSolutionId;
        }
    }
}
