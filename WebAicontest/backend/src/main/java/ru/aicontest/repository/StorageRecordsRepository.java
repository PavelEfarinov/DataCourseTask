package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.StorageRecordEntity;

import java.util.List;

public interface StorageRecordsRepository extends JpaRepository<StorageRecordEntity, Long> {
    @Transactional
    @Modifying
    @Query(value = "SELECT 1 FROM create_storage_record(?1, ?2, ?3)", nativeQuery = true)
    List<Integer> takeItem(int storageId, int managerId, int itemId);

    @Transactional
    @Query(value = "SELECT * FROM storage_record where storage_id = ?1 and closure_date is null order by creation_date", nativeQuery = true)
    List<StorageRecordEntity> getRecordsForStorage(int storageId);

    @Transactional
    @Query(value = "SELECT * FROM storage_record where responsible_manager_id = ?1 and closure_date is null order by storage_id", nativeQuery = true)
    List<StorageRecordEntity> getRecordsForManager(int managerId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 FROM close_storage_record(?1)", nativeQuery = true)
    List<Integer> closeRecord(int storageRecordId);

}
