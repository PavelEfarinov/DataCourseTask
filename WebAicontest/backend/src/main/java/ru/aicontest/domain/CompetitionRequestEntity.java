package ru.aicontest.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "competition_request", schema = "public", catalog = "postgres")
public class CompetitionRequestEntity {
    private int id;
    private String requestStatus;
    private String theme;
    private String description;
    private Timestamp creationDate;
    private Timestamp closureDate;
    private CompetitionParticipantEntity competitionParticipant;

    @ManyToOne
    @JoinColumn(name="author_id")
    public CompetitionParticipantEntity getCompetitionParticipant() {
        return competitionParticipant;
    }

    public void setCompetitionParticipant(CompetitionParticipantEntity participantAuthor) {
        this.competitionParticipant = participantAuthor;
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
    @Column(name = "request_status")
    public String getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(String requestStatus) {
        this.requestStatus = requestStatus;
    }

    @Basic
    @Column(name = "theme")
    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "creation_date")
    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }

    @Basic
    @Column(name = "closure_date")
    public Timestamp getClosureDate() {
        return closureDate;
    }

    public void setClosureDate(Timestamp closureDate) {
        this.closureDate = closureDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CompetitionRequestEntity that = (CompetitionRequestEntity) o;

        if (id != that.id) return false;
        if (requestStatus != null ? !requestStatus.equals(that.requestStatus) : that.requestStatus != null)
            return false;
        if (theme != null ? !theme.equals(that.theme) : that.theme != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        return closureDate != null ? closureDate.equals(that.closureDate) : that.closureDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (requestStatus != null ? requestStatus.hashCode() : 0);
        result = 31 * result + (theme != null ? theme.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (closureDate != null ? closureDate.hashCode() : 0);
        return result;
    }
}
