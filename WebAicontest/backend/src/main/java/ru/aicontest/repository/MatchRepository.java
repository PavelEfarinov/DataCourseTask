package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.MatchEntity;

import java.util.List;

public interface MatchRepository  extends JpaRepository<MatchEntity, Long> {
    @Transactional
    @Query(value = "SELECT match.* from match join match_solution on id = match_id join solution on solution_id = solution.id where solution.participant_id = ?1 order by match.start_time",nativeQuery = true)
    List<MatchEntity> getAllParticipantMatches(int participantId);

    @Transactional
    @Modifying
    @Query(value = "SELECT match.* from match join match_solution on id = match_id join solution on solution_id = solution.id where solution.participant_id = ?1 order by match.start_time",nativeQuery = true)
    List<MatchEntity> createNewMatch(int participant );
}
