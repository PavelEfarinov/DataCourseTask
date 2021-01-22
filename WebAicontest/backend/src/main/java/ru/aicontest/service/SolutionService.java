package ru.aicontest.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.aicontest.repository.SolutionRepository;

@Service
public class SolutionService {
    private final SolutionRepository solutionRepository;

    public SolutionService(SolutionRepository solutionRepository) {
        this.solutionRepository = solutionRepository;
    }

    public void uploadSolution(int participantId, MultipartFile file, String language) {
        int solutionId = solutionRepository.addNewSolution(participantId, participantId + "/" + file.getOriginalFilename() + "_" + file.getSize(), language).get(0);
        solutionRepository.compileNewSolution(solutionId, participantId + "/compiled/" + file.getOriginalFilename() + "_" + file.getSize());
    }
}
