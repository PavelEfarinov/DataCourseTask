package ru.aicontest.controller;

import org.springframework.web.bind.annotation.*;
import ru.aicontest.domain.EquipmentEntity;
import ru.aicontest.repository.EquipmentRepository;
import ru.aicontest.repository.StorageRecordsRepository;

import java.util.List;

@RestController
@RequestMapping("/api/1/equipment")
public class EquipmentController {

    EquipmentRepository equipmentRepository;
    StorageRecordsRepository storageRecordsRepository;

    public EquipmentController(EquipmentRepository equipmentRepository, StorageRecordsRepository storageRecordsRepository) {
        this.equipmentRepository = equipmentRepository;
        this.storageRecordsRepository = storageRecordsRepository;
    }

    @GetMapping("{storageId}/list")
    public List<EquipmentEntity> findAllStorageItems(@PathVariable int storageId) {
        return equipmentRepository.getAllStorageItems(storageId);
    }

    @GetMapping("{itemId}/available")
    public boolean isItemAvailable(@PathVariable int itemId) {
        return equipmentRepository.isItemAvailable(itemId);
    }

    @PostMapping("/take")
    public void takeItem(@RequestBody StorageRecordRequestDTO request) {
        storageRecordsRepository.takeItem(request.storageId, request.managerId, request.itemId);
    }

    @PostMapping("/return")
    public void closeRecord(@RequestBody CloseStorageRecordRequestDTO closeStorageRecordRequestDTO) {
        storageRecordsRepository.closeRecord(closeStorageRecordRequestDTO.getStorageRecordId());
    }

    public static class CloseStorageRecordRequestDTO
    {
        private int storageRecordId;

        public int getStorageRecordId() {
            return storageRecordId;
        }

        public void setStorageRecordId(int storageRecordId) {
            this.storageRecordId = storageRecordId;
        }
    }

    public static class StorageRecordRequestDTO
    {
        private int itemId;
        private int storageId;
        private int managerId;

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public int getStorageId() {
            return storageId;
        }

        public void setStorageId(int storageId) {
            this.storageId = storageId;
        }

        public int getManagerId() {
            return managerId;
        }

        public void setManagerId(int managerId) {
            this.managerId = managerId;
        }

        public StorageRecordRequestDTO(int itemId, int storageId, int managerId) {
            this.itemId = itemId;
            this.storageId = storageId;
            this.managerId = managerId;
        }
    }
}
