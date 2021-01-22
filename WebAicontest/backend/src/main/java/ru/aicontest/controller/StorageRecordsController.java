package ru.aicontest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.aicontest.domain.StorageRecordEntity;
import ru.aicontest.repository.StorageRecordsRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/records")
public class StorageRecordsController {

    StorageRecordsRepository storageRecordsRepository;

    public StorageRecordsController(StorageRecordsRepository storageRecordsRepository) {
        this.storageRecordsRepository = storageRecordsRepository;
    }

    @GetMapping("/storage/{storageId}/list")
    public List<StorageRecordEntity> getStorageRecords(@PathVariable int storageId) {
        return storageRecordsRepository.getRecordsForStorage(storageId);
    }

    @GetMapping("/manager/{managerId}/list")
    public List<StorageRecordEntity> getManagerRecords(@PathVariable int managerId) {
        return storageRecordsRepository.getRecordsForManager(managerId);
    }
}
