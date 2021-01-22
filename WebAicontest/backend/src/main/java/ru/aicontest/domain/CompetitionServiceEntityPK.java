package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class CompetitionServiceEntityPK implements Serializable {
    private int competitionId;
    private int serviceId;

    @Column(name = "competition_id")
    @Id
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Column(name = "service_id")
    @Id
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

        CompetitionServiceEntityPK that = (CompetitionServiceEntityPK) o;

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
