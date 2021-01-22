package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "user_results", schema = "public", catalog = "postgres")
@IdClass(UserResultsEntityPK.class)
public class UserResultsEntity {
    private int matchResultId;
    private int participantId;
    private Float result;

    @Id
    @Column(name = "match_result_id")
    public int getMatchResultId() {
        return matchResultId;
    }

    public void setMatchResultId(int matchResultId) {
        this.matchResultId = matchResultId;
    }

    @Id
    @Column(name = "participant_id")
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    @Basic
    @Column(name = "result")
    public Float getResult() {
        return result;
    }

    public void setResult(Float result) {
        this.result = result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserResultsEntity that = (UserResultsEntity) o;

        if (matchResultId != that.matchResultId) return false;
        if (participantId != that.participantId) return false;
        return result != null ? result.equals(that.result) : that.result == null;
    }

    @Override
    public int hashCode() {
        int result1 = matchResultId;
        result1 = 31 * result1 + participantId;
        result1 = 31 * result1 + (result != null ? result.hashCode() : 0);
        return result1;
    }
}
