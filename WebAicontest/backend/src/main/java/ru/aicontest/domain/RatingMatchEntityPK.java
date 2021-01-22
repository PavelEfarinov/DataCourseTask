package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class RatingMatchEntityPK implements Serializable {
    private int matchId;
    private int ratingRoundId;

    @Column(name = "match_id")
    @Id
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Column(name = "rating_round_id")
    @Id
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

        RatingMatchEntityPK that = (RatingMatchEntityPK) o;

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
