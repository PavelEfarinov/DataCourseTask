package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "competition_participant", schema = "public", catalog = "postgres")
public class CompetitionParticipantEntity {
    private int id;

    private String baseUserLogin;
    private RatingEloEntity ratingElo;

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "participant_id")
    public RatingEloEntity getRatingElo() {
        return ratingElo;
    }

    public void setRatingElo(RatingEloEntity ratingElo) {
        this.ratingElo = ratingElo;
    }

    @Basic
    @Column(name = "base_user_login")
    public String getBaseUserLogin() {
        return baseUserLogin;
    }

    public void setBaseUserLogin(String baseUserLogin) {
        this.baseUserLogin = baseUserLogin;
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

        CompetitionParticipantEntity that = (CompetitionParticipantEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
