package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "solution_compiler", schema = "public", catalog = "postgres")
public class SolutionCompilerEntity {
    private String language;
    private String applicationLocation;

    @Id
    @Column(name = "language")
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Basic
    @Column(name = "application_location")
    public String getApplicationLocation() {
        return applicationLocation;
    }

    public void setApplicationLocation(String applicationLocation) {
        this.applicationLocation = applicationLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolutionCompilerEntity that = (SolutionCompilerEntity) o;

        if (language != null ? !language.equals(that.language) : that.language != null) return false;
        return applicationLocation != null ? applicationLocation.equals(that.applicationLocation) : that.applicationLocation == null;
    }

    @Override
    public int hashCode() {
        int result = language != null ? language.hashCode() : 0;
        result = 31 * result + (applicationLocation != null ? applicationLocation.hashCode() : 0);
        return result;
    }
}
