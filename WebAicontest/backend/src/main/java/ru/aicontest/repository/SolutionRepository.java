package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.aicontest.domain.SolutionEntity;

import java.util.List;

public interface SolutionRepository extends JpaRepository<SolutionEntity, Long> {
    List<SolutionEntity> findAllByOrderByCreationTimeDesc();
}
