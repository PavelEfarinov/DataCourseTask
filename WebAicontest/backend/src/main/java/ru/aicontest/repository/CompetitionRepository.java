package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.CompetitionEntity;

import java.util.List;

public interface CompetitionRepository extends JpaRepository<CompetitionEntity, Long> {
    @Transactional
    @Query(value = "SELECT * FROM competition where end_date > now() or end_date is null", nativeQuery = true)
    List<CompetitionEntity> getAllActiveCompetitions();
}
