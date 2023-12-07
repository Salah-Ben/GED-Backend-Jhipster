package com.icb.ged.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ClientPhysique.
 */
@Entity
@Table(name = "client_physique")
public class ClientPhysique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_client_physisque")
    private String nomClientPhysisque;

    @Column(name = "prenom_client_physique")
    private String prenomClientPhysique;

    @Column(name = "cin_client_physisque")
    private String cinClientPhysisque;

    @Column(name = "email_client_physique")
    private String emailClientPhysique;

    @Column(name = "telephone_client_physique")
    private String telephoneClientPhysique;

    @Column(name = "adresse_client_physique")
    private String adresseClientPhysique;

    @Column(name = "ville_client_physique")
    private String villeClientPhysique;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientPhysique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClientPhysisque() {
        return this.nomClientPhysisque;
    }

    public ClientPhysique nomClientPhysisque(String nomClientPhysisque) {
        this.setNomClientPhysisque(nomClientPhysisque);
        return this;
    }

    public void setNomClientPhysisque(String nomClientPhysisque) {
        this.nomClientPhysisque = nomClientPhysisque;
    }

    public String getPrenomClientPhysique() {
        return this.prenomClientPhysique;
    }

    public ClientPhysique prenomClientPhysique(String prenomClientPhysique) {
        this.setPrenomClientPhysique(prenomClientPhysique);
        return this;
    }

    public void setPrenomClientPhysique(String prenomClientPhysique) {
        this.prenomClientPhysique = prenomClientPhysique;
    }

    public String getCinClientPhysisque() {
        return this.cinClientPhysisque;
    }

    public ClientPhysique cinClientPhysisque(String cinClientPhysisque) {
        this.setCinClientPhysisque(cinClientPhysisque);
        return this;
    }

    public void setCinClientPhysisque(String cinClientPhysisque) {
        this.cinClientPhysisque = cinClientPhysisque;
    }

    public String getEmailClientPhysique() {
        return this.emailClientPhysique;
    }

    public ClientPhysique emailClientPhysique(String emailClientPhysique) {
        this.setEmailClientPhysique(emailClientPhysique);
        return this;
    }

    public void setEmailClientPhysique(String emailClientPhysique) {
        this.emailClientPhysique = emailClientPhysique;
    }

    public String getTelephoneClientPhysique() {
        return this.telephoneClientPhysique;
    }

    public ClientPhysique telephoneClientPhysique(String telephoneClientPhysique) {
        this.setTelephoneClientPhysique(telephoneClientPhysique);
        return this;
    }

    public void setTelephoneClientPhysique(String telephoneClientPhysique) {
        this.telephoneClientPhysique = telephoneClientPhysique;
    }

    public String getAdresseClientPhysique() {
        return this.adresseClientPhysique;
    }

    public ClientPhysique adresseClientPhysique(String adresseClientPhysique) {
        this.setAdresseClientPhysique(adresseClientPhysique);
        return this;
    }

    public void setAdresseClientPhysique(String adresseClientPhysique) {
        this.adresseClientPhysique = adresseClientPhysique;
    }

    public String getVilleClientPhysique() {
        return this.villeClientPhysique;
    }

    public ClientPhysique villeClientPhysique(String villeClientPhysique) {
        this.setVilleClientPhysique(villeClientPhysique);
        return this;
    }

    public void setVilleClientPhysique(String villeClientPhysique) {
        this.villeClientPhysique = villeClientPhysique;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientPhysique)) {
            return false;
        }
        return id != null && id.equals(((ClientPhysique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientPhysique{" +
            "id=" + getId() +
            ", nomClientPhysisque='" + getNomClientPhysisque() + "'" +
            ", prenomClientPhysique='" + getPrenomClientPhysique() + "'" +
            ", cinClientPhysisque='" + getCinClientPhysisque() + "'" +
            ", emailClientPhysique='" + getEmailClientPhysique() + "'" +
            ", telephoneClientPhysique='" + getTelephoneClientPhysique() + "'" +
            ", adresseClientPhysique='" + getAdresseClientPhysique() + "'" +
            ", villeClientPhysique='" + getVilleClientPhysique() + "'" +
            "}";
    }
}
