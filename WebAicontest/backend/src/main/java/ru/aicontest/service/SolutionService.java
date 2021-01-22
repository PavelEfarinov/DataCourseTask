package ru.aicontest.service;

import org.springframework.stereotype.Service;
import ru.aicontest.domain.BaseUserEntity;
import ru.aicontest.domain.SolutionEntity;
import ru.aicontest.repository.SolutionRepository;

@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public long upload(BaseUserEntity user) {
        SolutionEntity solution = new SolutionEntity();
        solutionRepository.save(solution);
        return solution.getId();
    }
}
