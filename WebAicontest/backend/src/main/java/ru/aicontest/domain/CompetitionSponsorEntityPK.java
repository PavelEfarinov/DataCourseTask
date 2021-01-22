package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CompetitionSponsorEntityPK implements Serializable {
    private int competitionId;
    private int organizationId;

    @Column(name = "competition_id")
    @Id
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Column(name = "organization_id")
    @Id
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

        CompetitionSponsorEntityPK that = (CompetitionSponsorEntityPK) o;

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
