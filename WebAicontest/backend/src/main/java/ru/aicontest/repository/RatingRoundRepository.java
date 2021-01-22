package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.RatingRoundEntity;

import java.util.List;

public interface RatingRoundRepository extends JpaRepository<RatingRoundEntity, Long> {


    @Transactional
    @Query(value = "SELECT * FROM rating_round where competition_id = ?1 order by start_time asc", nativeQuery = true)
    public List<RatingRoundEntity> getAllRoundsForCompetition(int id);
}
