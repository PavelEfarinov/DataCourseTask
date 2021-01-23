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
    @Query(value = "SELECT * from create_match(?1, ?2, false, null, ARRAY[?3, ?4])",nativeQuery = true)
    List<Integer> createNewSandboxMatch(int creatorId, String matchMap, int solutionId1, int solution2);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from add_match_settings(?1, ?2, ?3)",nativeQuery = true)
    List<Integer> addMatchSettings(String key, String value, int matchId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from fill_board_on_startup(?2, ?3, ?1)",nativeQuery = true)
    List<Integer> fillBoardOnStartup(int matchId, int whiteSolution, int blackSolution);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from add_match_tick(?1, '', 1)",nativeQuery = true)
    List<Integer> addMatchTick(int matchId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from start_match(?1)",nativeQuery = true)
    List<Integer> startMatch(int matchId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from end_match(?1)",nativeQuery = true)
    List<Integer> endMatch(int matchId);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from add_user_results((select match_result_id from match where id = ?1), ?2, ?3)",nativeQuery = true)
    List<Integer> addUserResults(int matchId, int participantId, float result);

}
