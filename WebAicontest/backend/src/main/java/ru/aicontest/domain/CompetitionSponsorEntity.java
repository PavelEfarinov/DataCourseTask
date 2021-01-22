package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "competition_sponsor", schema = "public", catalog = "postgres")
@IdClass(CompetitionSponsorEntityPK.class)
public class CompetitionSponsorEntity {
    private int competitionId;
    private int organizationId;

    @Id
    @Column(name = "competition_id")
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Id
    @Column(name = "organization_id")
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionSponsorEntity that = (CompetitionSponsorEntity) o;

        if (competitionId != that.competitionId) return false;
        return organizationId == that.organizationId;
    }

    @Override
    public int hashCode() {
        int result = competitionId;
        result = 31 * result + organizationId;
        return result;
    }
}
