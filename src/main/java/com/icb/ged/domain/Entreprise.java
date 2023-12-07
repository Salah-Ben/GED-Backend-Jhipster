package com.icb.ged.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Entreprise.
 */
@Entity
@Table(name = "entreprise")
public class Entreprise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "enterprise_id")
    private Long enterpriseID;

    @Column(name = "nom_cabinet")
    private String nomCabinet;

    @Column(name = "numero_identification_cabinet")
    private String numeroIdentificationCabinet;

    @Column(name = "email_cabinet")
    private String emailCabinet;

    @Column(name = "adresse_cabinet")
    private String adresseCabinet;

    @Column(name = "telephone_cabinet")
    private String telephoneCabinet;

    @Column(name = "ville_cabinet")
    private String villeCabinet;

    @Column(name = "activite_cabinet")
    private String activiteCabinet;

    @ManyToMany
    @JoinTable(
        name = "rel_entreprise__client",
        joinColumns = @JoinColumn(name = "entreprise_id"),
        inverseJoinColumns = @JoinColumn(name = "client_id")
    )
    @JsonIgnoreProperties(value = { "clientPhysique", "clientMoral", "groupClientMoral", "groupClientPhysique" }, allowSetters = true)
    private Set<Client> clients = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Entreprise id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getEnterpriseID() {
        return this.enterpriseID;
    }

    public Entreprise enterpriseID(Long enterpriseID) {
        this.setEnterpriseID(enterpriseID);
        return this;
    }

    public void setEnterpriseID(Long enterpriseID) {
        this.enterpriseID = enterpriseID;
    }

    public String getNomCabinet() {
        return this.nomCabinet;
    }

    public Entreprise nomCabinet(String nomCabinet) {
        this.setNomCabinet(nomCabinet);
        return this;
    }

    public void setNomCabinet(String nomCabinet) {
        this.nomCabinet = nomCabinet;
    }

    public String getNumeroIdentificationCabinet() {
        return this.numeroIdentificationCabinet;
    }

    public Entreprise numeroIdentificationCabinet(String numeroIdentificationCabinet) {
        this.setNumeroIdentificationCabinet(numeroIdentificationCabinet);
        return this;
    }

    public void setNumeroIdentificationCabinet(String numeroIdentificationCabinet) {
        this.numeroIdentificationCabinet = numeroIdentificationCabinet;
    }

    public String getEmailCabinet() {
        return this.emailCabinet;
    }

    public Entreprise emailCabinet(String emailCabinet) {
        this.setEmailCabinet(emailCabinet);
        return this;
    }

    public void setEmailCabinet(String emailCabinet) {
        this.emailCabinet = emailCabinet;
    }

    public String getAdresseCabinet() {
        return this.adresseCabinet;
    }

    public Entreprise adresseCabinet(String adresseCabinet) {
        this.setAdresseCabinet(adresseCabinet);
        return this;
    }

    public void setAdresseCabinet(String adresseCabinet) {
        this.adresseCabinet = adresseCabinet;
    }

    public String getTelephoneCabinet() {
        return this.telephoneCabinet;
    }

    public Entreprise telephoneCabinet(String telephoneCabinet) {
        this.setTelephoneCabinet(telephoneCabinet);
        return this;
    }

    public void setTelephoneCabinet(String telephoneCabinet) {
        this.telephoneCabinet = telephoneCabinet;
    }

    public String getVilleCabinet() {
        return this.villeCabinet;
    }

    public Entreprise villeCabinet(String villeCabinet) {
        this.setVilleCabinet(villeCabinet);
        return this;
    }

    public void setVilleCabinet(String villeCabinet) {
        this.villeCabinet = villeCabinet;
    }

    public String getActiviteCabinet() {
        return this.activiteCabinet;
    }

    public Entreprise activiteCabinet(String activiteCabinet) {
        this.setActiviteCabinet(activiteCabinet);
        return this;
    }

    public void setActiviteCabinet(String activiteCabinet) {
        this.activiteCabinet = activiteCabinet;
    }

    public Set<Client> getClients() {
        return this.clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Entreprise clients(Set<Client> clients) {
        this.setClients(clients);
        return this;
    }

    public Entreprise addClient(Client client) {
        this.clients.add(client);
        return this;
    }

    public Entreprise removeClient(Client client) {
        this.clients.remove(client);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Entreprise)) {
            return false;
        }
        return id != null && id.equals(((Entreprise) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entreprise{" +
            "id=" + getId() +
            ", enterpriseID=" + getEnterpriseID() +
            ", nomCabinet='" + getNomCabinet() + "'" +
            ", numeroIdentificationCabinet='" + getNumeroIdentificationCabinet() + "'" +
            ", emailCabinet='" + getEmailCabinet() + "'" +
            ", adresseCabinet='" + getAdresseCabinet() + "'" +
            ", telephoneCabinet='" + getTelephoneCabinet() + "'" +
            ", villeCabinet='" + getVilleCabinet() + "'" +
            ", activiteCabinet='" + getActiviteCabinet() + "'" +
            "}";
    }
}
