package com.icb.ged.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A Client.
 */
@Entity
@Table(name = "client")
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "type")
    private String type;

    @OneToOne
    @JoinColumn(unique = true)
    private ClientPhysique clientPhysique;

    @OneToOne
    @JoinColumn(unique = true)
    private ClientMoral clientMoral;

    @ManyToOne
    private GroupClientMoral groupClientMoral;

    @ManyToOne
    private GroupClientPhysique groupClientPhysique;

    public Long getId() {
        return this.id;
    }

    public Client id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return this.type;
    }

    public Client type(String type) {
        this.setType(type);
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ClientPhysique getClientPhysique() {
        return this.clientPhysique;
    }

    public void setClientPhysique(ClientPhysique clientPhysique) {
        this.clientPhysique = clientPhysique;
    }

    public Client clientPhysique(ClientPhysique clientPhysique) {
        this.setClientPhysique(clientPhysique);
        return this;
    }

    public ClientMoral getClientMoral() {
        return this.clientMoral;
    }

    public void setClientMoral(ClientMoral clientMoral) {
        this.clientMoral = clientMoral;
    }

    public Client clientMoral(ClientMoral clientMoral) {
        this.setClientMoral(clientMoral);
        return this;
    }

    public GroupClientMoral getGroupClientMoral() {
        return this.groupClientMoral;
    }

    public void setGroupClientMoral(GroupClientMoral groupClientMoral) {
        this.groupClientMoral = groupClientMoral;
    }

    public Client groupClientMoral(GroupClientMoral groupClientMoral) {
        this.setGroupClientMoral(groupClientMoral);
        return this;
    }

    public GroupClientPhysique getGroupClientPhysique() {
        return this.groupClientPhysique;
    }

    public void setGroupClientPhysique(GroupClientPhysique groupClientPhysique) {
        this.groupClientPhysique = groupClientPhysique;
    }

    public Client groupClientPhysique(GroupClientPhysique groupClientPhysique) {
        this.setGroupClientPhysique(groupClientPhysique);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Client)) {
            return false;
        }
        return id != null && id.equals(((Client) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Client{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            "}";
    }
}
