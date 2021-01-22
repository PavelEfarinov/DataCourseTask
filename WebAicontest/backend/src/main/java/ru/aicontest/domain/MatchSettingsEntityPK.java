package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MatchSettingsEntityPK implements Serializable {
    private int matchLogId;
    private int simulationSettingsId;

    @Column(name = "match_log_id")
    @Id
    public int getMatchLogId() {
        return matchLogId;
    }

    public void setMatchLogId(int matchLogId) {
        this.matchLogId = matchLogId;
    }

    @Column(name = "simulation_settings_id")
    @Id
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

        MatchSettingsEntityPK that = (MatchSettingsEntityPK) o;

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
