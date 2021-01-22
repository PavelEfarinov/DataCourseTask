package ru.aicontest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.aicontest.domain.OrganizationEntity;

import java.util.List;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {
    @Transactional
    @Query(value = "SELECT organization.* FROM competition join competition_sponsor on id = competition_id join organization on organization_id = organization.id where competition.id = ?1", nativeQuery = true)
    List<OrganizationEntity> getCompetitionSponsors(int id);

}
