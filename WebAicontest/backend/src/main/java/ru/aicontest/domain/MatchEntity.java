package ru.aicontest.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "match", schema = "public", catalog = "postgres")
public class MatchEntity {
    private int id;
    private String matchStatus;
    private Timestamp startTime;
//    private RatingMatchEntity ratingMatch;
    private SandboxMatchEntity sandboxMatch;
    private CompetitionParticipantEntity creatorParticipant;
    private MatchResultEntity matchResult;

    @OneToOne
    @JoinColumn(name = "match_result_id", referencedColumnName = "id")
    public MatchResultEntity getMatchResult() {
        return matchResult;
    }

    public void setMatchResult(MatchResultEntity matchResult) {
        this.matchResult = matchResult;
    }

    @ManyToOne
    @JoinColumn(name = "creator_participant_id", referencedColumnName = "id")
    public CompetitionParticipantEntity getCreatorParticipant() {
        return creatorParticipant;
    }

    public void setCreatorParticipant(CompetitionParticipantEntity creatorParticipant) {
        this.creatorParticipant = creatorParticipant;
    }

//    @OneToOne
//    @JoinColumn(name = "id", referencedColumnName = "match_id")
//    public RatingMatchEntity getRatingMatch() {
//        return ratingMatch;
//    }
//
//    public void setRatingMatch(RatingMatchEntity ratingMatch) {
//        this.ratingMatch = ratingMatch;
//    }

    @OneToOne
    @JoinColumn(name = "id", referencedColumnName = "match_id")
    public SandboxMatchEntity getSandboxMatch() {
        return sandboxMatch;
    }

    public void setSandboxMatch(SandboxMatchEntity sandboxMatch) {
        this.sandboxMatch = sandboxMatch;
    }

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "match_status")
    public String getMatchStatus() {
        return matchStatus;
    }

    public void setMatchStatus(String matchStatus) {
        this.matchStatus = matchStatus;
    }

    @Basic
    @Column(name = "start_time")
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MatchEntity that = (MatchEntity) o;

        if (id != that.id) return false;
        if (matchStatus != null ? !matchStatus.equals(that.matchStatus) : that.matchStatus != null) return false;
        return startTime != null ? startTime.equals(that.startTime) : that.startTime == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (matchStatus != null ? matchStatus.hashCode() : 0);
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        return result;
    }
}
