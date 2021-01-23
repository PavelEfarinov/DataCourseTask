package ru.aicontest.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match_log", schema = "public", catalog = "postgres")
public class MatchLogEntity {
    private int id;
    private SimulationMapEntity matchMap;
    private List<MatchSettingsEntity> matchSettings;
    private List<MatchTickEntity> matchTicks;

    @OneToOne
    @JoinColumn(name = "match_map_id", referencedColumnName = "id")
    public SimulationMapEntity getMatchMap() {
        return matchMap;
    }

    public void setMatchMap(SimulationMapEntity matchMap) {
        this.matchMap = matchMap;
    }

    @OneToMany
    @JoinColumn(name = "match_log_id", referencedColumnName = "id")
    public List<MatchSettingsEntity> getMatchSettings() {
        return matchSettings;
    }

    public void setMatchSettings(List<MatchSettingsEntity> matchSettings) {
        this.matchSettings = matchSettings;
    }

    @OneToMany
    @JoinColumn(name = "match_log_id", referencedColumnName = "id")
    public List<MatchTickEntity> getMatchTicks() {
        return matchTicks;
    }

    public void setMatchTicks(List<MatchTickEntity> matchTicks) {
        this.matchTicks = matchTicks;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchLogEntity that = (MatchLogEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
