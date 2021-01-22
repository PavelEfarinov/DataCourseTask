package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "player_position", schema = "public", catalog = "postgres")
public class PlayerPositionEntity {
    private int id;
    private Float x;
    private Float y;
    private Float z;
    private Float rotationX;
    private Float rotationY;
    private Float rotationZ;
    private Float score;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "x")
    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    @Basic
    @Column(name = "y")
    public Float getY() {
        return y;
    }

    public void setY(Float y) {
        this.y = y;
    }

    @Basic
    @Column(name = "z")
    public Float getZ() {
        return z;
    }

    public void setZ(Float z) {
        this.z = z;
    }

    @Basic
    @Column(name = "rotation_x")
    public Float getRotationX() {
        return rotationX;
    }

    public void setRotationX(Float rotationX) {
        this.rotationX = rotationX;
    }

    @Basic
    @Column(name = "rotation_y")
    public Float getRotationY() {
        return rotationY;
    }

    public void setRotationY(Float rotationY) {
        this.rotationY = rotationY;
    }

    @Basic
    @Column(name = "rotation_z")
    public Float getRotationZ() {
        return rotationZ;
    }

    public void setRotationZ(Float rotationZ) {
        this.rotationZ = rotationZ;
    }

    @Basic
    @Column(name = "score")
    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlayerPositionEntity that = (PlayerPositionEntity) o;

        if (id != that.id) return false;
        if (x != null ? !x.equals(that.x) : that.x != null) return false;
        if (y != null ? !y.equals(that.y) : that.y != null) return false;
        if (z != null ? !z.equals(that.z) : that.z != null) return false;
        if (rotationX != null ? !rotationX.equals(that.rotationX) : that.rotationX != null) return false;
        if (rotationY != null ? !rotationY.equals(that.rotationY) : that.rotationY != null) return false;
        if (rotationZ != null ? !rotationZ.equals(that.rotationZ) : that.rotationZ != null) return false;
        return score != null ? score.equals(that.score) : that.score == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        result = 31 * result + (rotationX != null ? rotationX.hashCode() : 0);
        result = 31 * result + (rotationY != null ? rotationY.hashCode() : 0);
        result = 31 * result + (rotationZ != null ? rotationZ.hashCode() : 0);
        result = 31 * result + (score != null ? score.hashCode() : 0);
        return result;
    }
}
