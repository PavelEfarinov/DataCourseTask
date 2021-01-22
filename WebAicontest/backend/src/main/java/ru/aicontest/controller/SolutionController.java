package ru.aicontest.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.aicontest.exception.NoSuchResourceException;
import ru.aicontest.repository.SolutionCompilerRepository;
import ru.aicontest.service.SolutionService;

import java.util.List;

@RestController
@RequestMapping("/api/1/solution")
public class SolutionController extends ApiController {

    private final SolutionCompilerRepository solutionCompilerRepository;
    private final SolutionService solutionService;

    public SolutionController(SolutionCompilerRepository solutionCompilerRepository, SolutionService solutionService) {
        this.solutionCompilerRepository = solutionCompilerRepository;
        this.solutionService = solutionService;
    }

    @PostMapping("submit")
    public void handleFileUpload(@RequestParam("file") MultipartFile file,
                                 @RequestParam("language") String language, @RequestParam("participant") int participantId) {
        if (file != null && !file.isEmpty() && language != null) {
                solutionService.uploadSolution(participantId, file, language);

        } else {
            throw new NoSuchResourceException("Upload a correct file!");
        }
    }
    @GetMapping("languages")
    public List<String> getAllCompilerLanguages() {
        return solutionCompilerRepository.getAllAvailableLanguages();
    }

}
