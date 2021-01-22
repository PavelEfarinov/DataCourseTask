package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.CompetitionManagerEntity;

public interface CompetitionManagerRepository  extends JpaRepository<CompetitionManagerEntity, Long> {
    @Transactional
    @Query(value = "SELECT id FROM competition_manager where competition_id = ?1 and base_user_login = ?2", nativeQuery = true)
    Integer getCompetitionManagerId(int competitionId, String participantLogin);
}
