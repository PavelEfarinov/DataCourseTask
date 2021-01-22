package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "match_settings", schema = "public", catalog = "postgres")
@IdClass(MatchSettingsEntityPK.class)
public class MatchSettingsEntity {
    private int matchLogId;
    private int simulationSettingsId;

    @Id
    @Column(name = "match_log_id")
    public int getMatchLogId() {
        return matchLogId;
    }

    public void setMatchLogId(int matchLogId) {
        this.matchLogId = matchLogId;
    }

    @Id
    @Column(name = "simulation_settings_id")
    public int getSimulationSettingsId() {
        return simulationSettingsId;
    }

    public void setSimulationSettingsId(int simulationSettingsId) {
        this.simulationSettingsId = simulationSettingsId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchSettingsEntity that = (MatchSettingsEntity) o;

        if (matchLogId != that.matchLogId) return false;
        return simulationSettingsId == that.simulationSettingsId;
    }

    @Override
    public int hashCode() {
        int result = matchLogId;
        result = 31 * result + simulationSettingsId;
        return result;
    }
}
