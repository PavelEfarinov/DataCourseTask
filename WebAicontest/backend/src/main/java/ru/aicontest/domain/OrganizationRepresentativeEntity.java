package ru.aicontest.domain;

import javax.persistence.*;

@Entity
@Table(name = "organization_representative", schema = "public", catalog = "postgres")
@IdClass(OrganizationRepresentativeEntityPK.class)
public class OrganizationRepresentativeEntity {
    private String baseUserLogin;
    private int organizationId;

    @Id
    @Column(name = "base_user_login")
    public String getBaseUserLogin() {
        return baseUserLogin;
    }

    public void setBaseUserLogin(String baseUserLogin) {
        this.baseUserLogin = baseUserLogin;
    }

    @Id
    @Column(name = "organization_id")
    public int getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(int organizationId) {
        this.organizationId = organizationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrganizationRepresentativeEntity that = (OrganizationRepresentativeEntity) o;

        if (organizationId != that.organizationId) return false;
        return baseUserLogin != null ? baseUserLogin.equals(that.baseUserLogin) : that.baseUserLogin == null;
    }

    @Override
    public int hashCode() {
        int result = baseUserLogin != null ? baseUserLogin.hashCode() : 0;
        result = 31 * result + organizationId;
        return result;
    }
}
