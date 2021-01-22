package ru.aicontest.domain;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "storage_record", schema = "public", catalog = "postgres")
public class StorageRecordEntity {
    private int id;
    private Timestamp creationDate;
    private Timestamp closureDate;
    private CompetitionManagerEntity responsibleManager;
    private StorageEntity originStorage;
    private EquipmentEntity takenEquipment;

    @ManyToOne
    @JoinColumn(name = "responsible_manager_id")
    public CompetitionManagerEntity getResponsibleManager() {
        return responsibleManager;
    }

    public void setResponsibleManager(CompetitionManagerEntity responsibleManager) {
        this.responsibleManager = responsibleManager;
    }

    @ManyToOne
    @JoinColumn(name = "storage_id")
    public StorageEntity getOriginStorage() {
        return originStorage;
    }

    public void setOriginStorage(StorageEntity originStorage) {
        this.originStorage = originStorage;
    }

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    public EquipmentEntity getTakenEquipment() {
        return takenEquipment;
    }

    public void setTakenEquipment(EquipmentEntity takenEquipment) {
        this.takenEquipment = takenEquipment;
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

        StorageRecordEntity that = (StorageRecordEntity) o;

        if (id != that.id) return false;
        if (creationDate != null ? !creationDate.equals(that.creationDate) : that.creationDate != null) return false;
        return closureDate != null ? closureDate.equals(that.closureDate) : that.closureDate == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (closureDate != null ? closureDate.hashCode() : 0);
        return result;
    }
}
