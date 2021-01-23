package ru.aicontest.service;

import org.springframework.stereotype.Service;
import ru.aicontest.repository.MatchRepository;

@Service
public class MatchService {
    MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void createNewMatch(int participantId)
    {
        matchRepository.createNewMatch(participantId);
    }
}
