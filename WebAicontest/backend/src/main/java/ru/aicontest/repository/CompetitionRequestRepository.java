package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.CompetitionRequestEntity;

import java.util.List;

public interface CompetitionRequestRepository extends JpaRepository<CompetitionRequestEntity, Long> {
    @Transactional
    @Query(value = "SELECT competition_request.* FROM competition_request join competition_participant on author_id = competition_participant.id where competition_participant.id = ?1", nativeQuery = true)
    List<CompetitionRequestEntity> getAllCreatedRequests(int participantId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 FROM create_competition_request(?1, ?2, ?3)", nativeQuery = true)
    List<Integer> createCompetitionRequest(int participantId, String theme, String description);


    @Transactional
    @Query(value = "SELECT * FROM competition_request join competition_participant on author_id = competition_participant.id where request_status = 'created' and competition_id = ?1 order by creation_date asc", nativeQuery = true)
    List<CompetitionRequestEntity> getAllOpenedRequests(int competitionId);

    @Transactional
    @Query(value = "SELECT * FROM competition_request where assignee_id = ?1 and request_status != 'closed' order by creation_date asc", nativeQuery = true)
    List<CompetitionRequestEntity> getAllAssignedRequests(int managerId);

    @Transactional
    @Query(value = "SELECT 1 FROM process_competition_request(?2, ?1)", nativeQuery = true)
    List<Integer> assignNewRequest(int managerId, int requestId);

    @Transactional
    @Query(value = "SELECT 1 FROM close_competition_request(?1)", nativeQuery = true)
    List<Integer> closeAssignedRequest(int requestId);
}
