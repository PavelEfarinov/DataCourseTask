package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.CompetitionEntity;
import ru.aicontest.domain.CompetitionParticipantEntity;
import ru.aicontest.domain.OrganizationEntity;
import ru.aicontest.domain.RatingRoundEntity;
import ru.aicontest.repository.CompetitionParticipantRepository;
import ru.aicontest.repository.CompetitionRepository;
import ru.aicontest.repository.OrganizationRepository;
import ru.aicontest.repository.RatingRoundRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/competition")
public class CompetitionController {
    CompetitionRepository competitionRepository;
    OrganizationRepository organizationRepository;
    CompetitionParticipantRepository userRepository;
    RatingRoundRepository ratingRoundRepository;

    public CompetitionController(CompetitionRepository competitionRepository, OrganizationRepository organizationRepository, CompetitionParticipantRepository userRepository, RatingRoundRepository ratingRoundRepository) {
        this.competitionRepository = competitionRepository;
        this.organizationRepository = organizationRepository;
        this.userRepository = userRepository;
        this.ratingRoundRepository = ratingRoundRepository;
    }

    @GetMapping("list")
    public List<CompetitionEntity> findAllActiveCompetitions() {
        return competitionRepository.getAllActiveCompetitions();
    }

    @GetMapping("/{id}/sponsors")
    public List<OrganizationEntity> findCompetitionSponsors(@PathVariable int id) {
        return organizationRepository.getCompetitionSponsors(id);
    }

    @GetMapping("/{id}/top")
    public List<CompetitionParticipantEntity> findTopPlayers(@PathVariable int id) {
        return userRepository.getFirst10OfCompetition(id);
    }

    @GetMapping("/{id}/rounds")
    public List<RatingRoundEntity> findAllRatingRounds(@PathVariable int id) {
        return ratingRoundRepository.getAllRoundsForCompetition(id);
    }
}
