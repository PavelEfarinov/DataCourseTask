package ru.aicontest.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "solution", schema = "public", catalog = "postgres")
public class SolutionEntity {
    private int id;
    private String compilationStatus;
    private Timestamp creationTime;
    private String fileLocation;
    private String executableLocation;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "compilation_status")
    public String getCompilationStatus() {
        return compilationStatus;
    }

    public void setCompilationStatus(String compilationStatus) {
        this.compilationStatus = compilationStatus;
    }

    @Basic
    @Column(name = "creation_time")
    public Timestamp getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(Timestamp creationTime) {
        this.creationTime = creationTime;
    }

    @Basic
    @Column(name = "file_location")
    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Basic
    @Column(name = "executable_location")
    public String getExecutableLocation() {
        return executableLocation;
    }

    public void setExecutableLocation(String executableLocation) {
        this.executableLocation = executableLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolutionEntity that = (SolutionEntity) o;

        if (id != that.id) return false;
        if (compilationStatus != null ? !compilationStatus.equals(that.compilationStatus) : that.compilationStatus != null)
            return false;
        if (creationTime != null ? !creationTime.equals(that.creationTime) : that.creationTime != null) return false;
        if (fileLocation != null ? !fileLocation.equals(that.fileLocation) : that.fileLocation != null) return false;
        return executableLocation != null ? executableLocation.equals(that.executableLocation) : that.executableLocation == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (compilationStatus != null ? compilationStatus.hashCode() : 0);
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (fileLocation != null ? fileLocation.hashCode() : 0);
        result = 31 * result + (executableLocation != null ? executableLocation.hashCode() : 0);
        return result;
    }
}
