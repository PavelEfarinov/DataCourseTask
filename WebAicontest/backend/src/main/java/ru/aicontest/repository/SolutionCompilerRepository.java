package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.SolutionCompilerEntity;

import java.util.List;

public interface SolutionCompilerRepository extends JpaRepository<SolutionCompilerEntity, Long> {
    @Transactional
    @Query(value = "SELECT language FROM solution_compiler", nativeQuery = true)
    List<String> getAllAvailableLanguages();
}
