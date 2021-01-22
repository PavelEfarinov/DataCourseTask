package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "storage", schema = "public", catalog = "postgres")
public class StorageEntity {
    private int id;
    private String name;
    private CompetitionManagerEntity storageManager;

    @ManyToOne
    @JoinColumn(name="store_manager_id")
    public CompetitionManagerEntity getStorageManager() {
        return storageManager;
    }

    public void setStorageManager(CompetitionManagerEntity storageManager) {
        this.storageManager = storageManager;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StorageEntity that = (StorageEntity) o;

        if (id != that.id) return false;
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
