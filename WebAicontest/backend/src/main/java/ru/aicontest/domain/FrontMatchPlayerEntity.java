package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "front_match_player", schema = "public", catalog = "postgres")
public class FrontMatchPlayerEntity {
    private int id;
    private String location;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "location")
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FrontMatchPlayerEntity that = (FrontMatchPlayerEntity) o;

        if (id != that.id) return false;
        return location != null ? location.equals(that.location) : that.location == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        return result;
    }
}
