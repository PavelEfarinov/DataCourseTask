package ru.aicontest.service;

import org.springframework.stereotype.Service;
import ru.aicontest.repository.MatchRepository;

@Service
public class MatchService {
    MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public void createNewMatch(int creatorId, int opponentId, int creatorSolutionId, int opponentSolutionId)
    {
        int matchId = matchRepository.createNewSandboxMatch(creatorId, "standard chess map", creatorSolutionId, opponentSolutionId).get(0);
        matchRepository.addMatchSettings("white_player", creatorSolutionId + "", matchId);
        matchRepository.addMatchSettings("black_player", opponentSolutionId + "", matchId);
        matchRepository.startMatch( matchId);
        int tick = matchRepository.addMatchTick( matchId).get(0);
        matchRepository.fillBoardOnStartup(tick, creatorSolutionId, opponentSolutionId);
        matchRepository.addUserResults(matchId, creatorId, 1);
        matchRepository.addUserResults(matchId, opponentId, 0);
        matchRepository.endMatch(matchId);
    }
}
