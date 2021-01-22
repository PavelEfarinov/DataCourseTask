package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.StorageEntity;
import ru.aicontest.repository.StorageRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/storage")
public class StorageController {

    StorageRepository storageRepository;

    public StorageController(StorageRepository storageRepository) {
        this.storageRepository = storageRepository;
    }

    @GetMapping("{competitionId}/list")
    public List<StorageEntity> findAllActiveCompetitions(@PathVariable int competitionId) {
        return storageRepository.getAllStoragesForCompetition(competitionId);
    }

}
