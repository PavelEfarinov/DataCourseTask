package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.exception.NoSuchManagerException;
import ru.aicontest.repository.CompetitionManagerRepository;

@RestController
@RequestMapping("/api/1/manager")
public class CompetitionManagerController {
    CompetitionManagerRepository competitionManagerRepository;

    public CompetitionManagerController(CompetitionManagerRepository competitionManagerRepository) {
        this.competitionManagerRepository = competitionManagerRepository;
    }

    @GetMapping("{competitionId}/{participantLogin}/id")
    int getCompetitionManagerId(@PathVariable int competitionId, @PathVariable String participantLogin) throws NoSuchManagerException {
        Integer id = competitionManagerRepository.getCompetitionManagerId(competitionId, participantLogin);
        if(id == null)
        {
            throw new NoSuchManagerException("This user is not authorised to manage competitions.");
        }
        return id;
    }
}
