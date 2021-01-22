package ru.aicontest.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

public class OrganizationRepresentativeEntityPK implements Serializable {
    private String baseUserLogin;
    private int organizationId;

    @Column(name = "base_user_login")
    @Id
    public String getBaseUserLogin() {
        return baseUserLogin;
    }

    public void setBaseUserLogin(String baseUserLogin) {
        this.baseUserLogin = baseUserLogin;
    }

    @Column(name = "organization_id")
    @Id
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

        OrganizationRepresentativeEntityPK that = (OrganizationRepresentativeEntityPK) o;

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
