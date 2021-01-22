package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "rating_match", schema = "public", catalog = "postgres")
@IdClass(RatingMatchEntityPK.class)
public class RatingMatchEntity {
    private int matchId;
    private int ratingRoundId;

    @Id
    @Column(name = "match_id")
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Id
    @Column(name = "rating_round_id")
    public int getRatingRoundId() {
        return ratingRoundId;
    }

    public void setRatingRoundId(int ratingRoundId) {
        this.ratingRoundId = ratingRoundId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingMatchEntity that = (RatingMatchEntity) o;

        if (matchId != that.matchId) return false;
        return ratingRoundId == that.ratingRoundId;
    }

    @Override
    public int hashCode() {
        int result = matchId;
        result = 31 * result + ratingRoundId;
        return result;
    }
}
