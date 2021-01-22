package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "competition_service", schema = "public", catalog = "postgres")
@IdClass(CompetitionServiceEntityPK.class)
public class CompetitionServiceEntity {
    private int competitionId;
    private int serviceId;

    @Id
    @Column(name = "competition_id")
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Id
    @Column(name = "service_id")
    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionServiceEntity that = (CompetitionServiceEntity) o;

        if (competitionId != that.competitionId) return false;
        return serviceId == that.serviceId;
    }

    @Override
    public int hashCode() {
        int result = competitionId;
        result = 31 * result + serviceId;
        return result;
    }
}
