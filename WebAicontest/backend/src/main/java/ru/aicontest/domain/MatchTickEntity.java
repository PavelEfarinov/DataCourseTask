package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "match_tick", schema = "public", catalog = "postgres")
public class MatchTickEntity {
    private int id;
    private String simulationState;
    private int simulationTick;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "simulation_state")
    public String getSimulationState() {
        return simulationState;
    }

    public void setSimulationState(String simulationState) {
        this.simulationState = simulationState;
    }

    @Basic
    @Column(name = "simulation_tick")
    public int getSimulationTick() {
        return simulationTick;
    }

    public void setSimulationTick(int simulationTick) {
        this.simulationTick = simulationTick;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchTickEntity that = (MatchTickEntity) o;

        if (id != that.id) return false;
        if (simulationTick != that.simulationTick) return false;
        return simulationState != null ? simulationState.equals(that.simulationState) : that.simulationState == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (simulationState != null ? simulationState.hashCode() : 0);
        result = 31 * result + simulationTick;
        return result;
    }
}
