package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "match_result", schema = "public", catalog = "postgres")
public class MatchResultEntity {
    private int id;

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

        MatchResultEntity that = (MatchResultEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
