package ru.aicontest.controller;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.aicontest.domain.CompetitionRequestEntity;
import ru.aicontest.exception.ValidationException;
import ru.aicontest.form.request.CompetitionRequestCredentials;
import ru.aicontest.repository.CompetitionRequestRepository;
import ru.aicontest.util.BindingResultUtils;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/1/request")
public class CompetitionRequestController {

    CompetitionRequestRepository competitionRequestRepository;

    public CompetitionRequestController(CompetitionRequestRepository competitionRequestRepository) {
        this.competitionRequestRepository = competitionRequestRepository;
    }

    @GetMapping("/{participantId}/created")
    public List<CompetitionRequestEntity> findAllCreatedRequests(@PathVariable int participantId) {
        return competitionRequestRepository.getAllCreatedRequests(participantId);
    }

    @PostMapping("/{participantId}/new")
    public void createNewCompetitionRequest(@PathVariable int participantId,
                                                                      @RequestBody @Valid CompetitionRequestCredentials competitionRequest,
                                                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(BindingResultUtils.getErrorMessage(bindingResult));
        }

        competitionRequestRepository.createCompetitionRequest(participantId, competitionRequest.getTheme(), competitionRequest.getDescription());
    }

    @GetMapping("/{competitionId}/opened")
    public List<CompetitionRequestEntity> findAllOpenedRequests(@PathVariable int competitionId) {
        return competitionRequestRepository.getAllOpenedRequests(competitionId);
    }

    @GetMapping("/{managerId}/assigned")
    public List<CompetitionRequestEntity> findAllAssignedRequests(@PathVariable int managerId) {
        return competitionRequestRepository.getAllAssignedRequests(managerId);
    }

    @PostMapping("/assign")
    public void assignNewRequest(@RequestBody AssignRequestBodyDTO request) {
        System.out.println(request.managerId);
        System.out.println(request.requestId);
        competitionRequestRepository.assignNewRequest(request.managerId, request.requestId);
    }

    @PostMapping("/close")
    public void closeAssignedRequest(@RequestBody AssignRequestBodyDTO request) {
        System.out.println(request.requestId);
        competitionRequestRepository.closeAssignedRequest(request.requestId);
    }

    public static class AssignRequestBodyDTO
    {
        public AssignRequestBodyDTO(int managerId, int requestId) {
            this.managerId = managerId;
            this.requestId = requestId;
        }

        public int getManagerId() {
            return managerId;
        }

        public void setManagerId(int managerId) {
            this.managerId = managerId;
        }

        public int getRequestId() {
            return requestId;
        }

        public void setRequestId(int requestId) {
            this.requestId = requestId;
        }

        public int managerId;
        public int requestId;
    }
}
