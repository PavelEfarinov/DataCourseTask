package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "rating_elo", schema = "public", catalog = "postgres")
public class RatingEloEntity {
    private int participantId;
    private float rating;

    @Id
    @Column(name = "participant_id")
    public int getParticipantId() {
        return participantId;
    }

    public void setParticipantId(int participantId) {
        this.participantId = participantId;
    }

    @Basic
    @Column(name = "rating")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RatingEloEntity that = (RatingEloEntity) o;

        if (participantId != that.participantId) return false;
        return Float.compare(that.rating, rating) == 0;
    }

    @Override
    public int hashCode() {
        int result = participantId;
        result = 31 * result + (rating != +0.0f ? Float.floatToIntBits(rating) : 0);
        return result;
    }
}
