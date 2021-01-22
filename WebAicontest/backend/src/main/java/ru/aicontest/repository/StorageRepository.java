package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.StorageEntity;

import java.util.List;

public interface StorageRepository extends JpaRepository<StorageEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM storage where competition_id = ?1", nativeQuery = true)
    List<StorageEntity> getAllStoragesForCompetition(int competitionId);
}
