package ru.aicontest.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "match_result", schema = "public", catalog = "postgres")
public class MatchResultEntity {
    private int id;
    private CompetitionParticipantEntity winnerParticipant;
    private MatchLogEntity matchLog;
    private List<UserResultsEntity> userResults;

    @OneToMany
    @JoinColumn(name = "match_result_id", referencedColumnName = "id")
    public List<UserResultsEntity> getUserResults() {
        return userResults;
    }

    public void setUserResults(List<UserResultsEntity> userResults) {
        this.userResults = userResults;
    }

    @OneToOne
    @JoinColumn(name = "winner_participant_id", referencedColumnName = "id")
    public CompetitionParticipantEntity getWinnerParticipant() {
        return winnerParticipant;
    }

    public void setWinnerParticipant(CompetitionParticipantEntity winnerParticipant) {
        this.winnerParticipant = winnerParticipant;
    }

    @OneToOne
    @JoinColumn(name = "match_log_id", referencedColumnName = "id")
    public MatchLogEntity getMatchLog() {
        return matchLog;
    }

    public void setMatchLog(MatchLogEntity matchLog) {
        this.matchLog = matchLog;
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

        MatchResultEntity that = (MatchResultEntity) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}
