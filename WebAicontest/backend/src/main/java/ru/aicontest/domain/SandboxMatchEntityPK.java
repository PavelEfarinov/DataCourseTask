package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class SandboxMatchEntityPK implements Serializable {
    private int matchId;
    private int competitionId;

    @Column(name = "match_id")
    @Id
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Column(name = "competition_id")
    @Id
    public int getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(int competitionId) {
        this.competitionId = competitionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SandboxMatchEntityPK that = (SandboxMatchEntityPK) o;

        if (matchId != that.matchId) return false;
        return competitionId == that.competitionId;
    }

    @Override
    public int hashCode() {
        int result = matchId;
        result = 31 * result + competitionId;
        return result;
    }
}
