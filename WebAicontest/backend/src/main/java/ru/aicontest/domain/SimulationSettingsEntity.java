package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "simulation_settings", schema = "public", catalog = "postgres")
public class SimulationSettingsEntity {
    private int id;
    private String settingsKey;
    private String settingsValue;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "settings_key")
    public String getSettingsKey() {
        return settingsKey;
    }

    public void setSettingsKey(String settingsKey) {
        this.settingsKey = settingsKey;
    }

    @Basic
    @Column(name = "settings_value")
    public String getSettingsValue() {
        return settingsValue;
    }

    public void setSettingsValue(String settingsValue) {
        this.settingsValue = settingsValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimulationSettingsEntity that = (SimulationSettingsEntity) o;

        if (id != that.id) return false;
        if (settingsKey != null ? !settingsKey.equals(that.settingsKey) : that.settingsKey != null) return false;
        return settingsValue != null ? settingsValue.equals(that.settingsValue) : that.settingsValue == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (settingsKey != null ? settingsKey.hashCode() : 0);
        result = 31 * result + (settingsValue != null ? settingsValue.hashCode() : 0);
        return result;
    }
}
