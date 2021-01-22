package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.SolutionEntity;

import java.util.List;

public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {

    @Transactional
    @Modifying
    @Query(value = "SELECT * from add_user_solution(?1, ?2, ?3)", nativeQuery = true)
    List<Integer> addNewSolution(int participantId, String fileLocation, String language);

    @Transactional
    @Modifying
    @Query(value = "SELECT 1 from make_solution_compiled(?1, ?2)", nativeQuery = true)
    List<Integer> compileNewSolution(int solutionId, String executableLocation);

    @Transactional
    @Modifying
    @Query(value = "SELECT * from solution where participant_id = ?1", nativeQuery = true)
    List<SolutionEntity> GetParticipantSolutions(int participantId);
}
