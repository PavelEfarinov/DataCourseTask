package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "match_solution", schema = "public", catalog = "postgres")
@IdClass(MatchSolutionEntityPK.class)
public class MatchSolutionEntity {
    private int matchId;
    private int solutionId;

    @Id
    @Column(name = "match_id")
    public int getMatchId() {
        return matchId;
    }

    public void setMatchId(int matchId) {
        this.matchId = matchId;
    }

    @Id
    @Column(name = "solution_id")
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

        MatchSolutionEntity that = (MatchSolutionEntity) o;

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
