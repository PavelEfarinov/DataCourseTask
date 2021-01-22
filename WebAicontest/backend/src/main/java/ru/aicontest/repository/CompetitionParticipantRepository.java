package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.CompetitionParticipantEntity;

import java.util.List;

public interface CompetitionParticipantRepository extends JpaRepository<CompetitionParticipantEntity, Long> {

    @Transactional
    @Query(value = "SELECT competition_participant.*, rating_elo.* FROM competition_participant join rating_elo on participant_id = competition_participant.id where competition_id = ?1 order by rating desc limit 10", nativeQuery = true)
    List<CompetitionParticipantEntity> getFirst10OfCompetition(int id);

    @Transactional
    @Query(value = "SELECT count(1) FROM competition_participant where competition_id = ?1", nativeQuery = true)
    Integer countCompetitionParticipants(int competitionId);

    @Transactional
    @Query(value = "SELECT competition_id FROM competition_participant where id = ?1", nativeQuery = true)
    Integer getCompetitionIdOfParticipant(int participantId);

    @Transactional
    @Query(value = "SELECT position FROM (SELECT *, row_number() OVER(ORDER BY rating) AS position FROM rating_elo join competition_participant on participant_id = competition_participant.id where competition_id = ?1) result where participant_id = ?2", nativeQuery = true)
    Integer getParticipantPosition(int competitionId, int participantId);

    @Transactional
    @Query(value = "SELECT * FROM competition_participant where competition_id = ?1 and base_user_login = ?2", nativeQuery = true)
    CompetitionParticipantEntity getCompetitionParticipant(int competitionId, String participantLogin);

    @Transactional
    @Modifying
    @Query(value = "SELECT * FROM partake_in_competition(?2, ?1)", nativeQuery = true)
    List<Integer> createCompetitionParticipant(int competitionId, String participantLogin);
}
