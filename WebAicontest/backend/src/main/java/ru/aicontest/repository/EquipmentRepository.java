package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.EquipmentEntity;

import java.util.List;

public interface EquipmentRepository extends JpaRepository<EquipmentEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM equipment where storage_id = ?1", nativeQuery = true)
    List<EquipmentEntity> getAllStorageItems(int storageId);

    @Transactional
    @Query(value = "SELECT NOT EXISTS (SELECT FROM storage_record where equipment_id = ?1 and closure_date is null)", nativeQuery = true)
    boolean isItemAvailable(int itemId);
}
