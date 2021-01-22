package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class MatchSolutionEntityPK implements Serializable {
    private int matchId;
    private int solutionId;

    @Column(name = "match_id")
    @Id
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Column(name = "solution_id")
    @Id
    public int getSolutionId() {
        return solutionId;
    }

    public void setSolutionId(int solutionId) {
        this.solutionId = solutionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchSolutionEntityPK that = (MatchSolutionEntityPK) o;

        if (matchId != that.matchId) return false;
        return solutionId == that.solutionId;
    }

    @Override
    public int hashCode() {
        int result = matchId;
        result = 31 * result + solutionId;
        return result;
    }
}
