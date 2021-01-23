package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.MatchEntity;
import ru.aicontest.repository.MatchRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/match")
public class MatchController {
    MatchRepository matchRepository;

    public MatchController(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @GetMapping("participant/{participantId}/all-matches")
    public List<MatchEntity> getMatchesWithParticipant(@PathVariable int participantId){
        return matchRepository.getAllParticipantMatches(participantId);
    }
}
