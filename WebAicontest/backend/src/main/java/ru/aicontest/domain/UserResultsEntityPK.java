package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class UserResultsEntityPK implements Serializable {
    private int matchResultId;
    private int participantId;

    @Column(name = "match_result_id")
    @Id
    public int getMatchResultId() {
        return matchResultId;
    }

    public void setMatchResultId(int matchResultId) {
        this.matchResultId = matchResultId;
    }

    @Column(name = "participant_id")
    @Id
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserResultsEntityPK that = (UserResultsEntityPK) o;

        if (matchResultId != that.matchResultId) return false;
        return participantId == that.participantId;
    }

    @Override
    public int hashCode() {
        int result = matchResultId;
        result = 31 * result + participantId;
        return result;
    }
}
