package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "competition_manager", schema = "public", catalog = "postgres")
public class CompetitionManagerEntity {
    private int id;
    private String baseUserLogin;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "base_user_login")
    public String getBaseUserLogin() {
        return baseUserLogin;
    }

    public void setBaseUserLogin(String baseUserLogin) {
        this.baseUserLogin = baseUserLogin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionManagerEntity that = (CompetitionManagerEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
