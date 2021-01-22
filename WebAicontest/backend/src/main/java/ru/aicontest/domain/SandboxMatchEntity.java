package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "sandbox_match", schema = "public", catalog = "postgres")
@IdClass(SandboxMatchEntityPK.class)
public class SandboxMatchEntity {
    private int matchId;
    private int competitionId;

    @Id
    @Column(name = "match_id")
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Id
    @Column(name = "competition_id")
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

        SandboxMatchEntity that = (SandboxMatchEntity) o;

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
