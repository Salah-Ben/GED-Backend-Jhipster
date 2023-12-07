package com.icb.ged.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A ClientMoral.
 */
@Entity
@Table(name = "client_moral")
public class ClientMoral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_client_moral")
    private String nomClientMoral;

    @Column(name = "numero_identification_client_moral")
    private String numeroIdentificationClientMoral;

    @Column(name = "adresse_client_moral")
    private String adresseClientMoral;

    @Column(name = "email_client_moral")
    private String emailClientMoral;

    @Column(name = "telephone_client_moral")
    private String telephoneClientMoral;

    @Column(name = "ville_client_moral")
    private String villeClientMoral;

    @Column(name = "activite_client_moral")
    private String activiteClientMoral;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ClientMoral id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomClientMoral() {
        return this.nomClientMoral;
    }

    public ClientMoral nomClientMoral(String nomClientMoral) {
        this.setNomClientMoral(nomClientMoral);
        return this;
    }

    public void setNomClientMoral(String nomClientMoral) {
        this.nomClientMoral = nomClientMoral;
    }

    public String getNumeroIdentificationClientMoral() {
        return this.numeroIdentificationClientMoral;
    }

    public ClientMoral numeroIdentificationClientMoral(String numeroIdentificationClientMoral) {
        this.setNumeroIdentificationClientMoral(numeroIdentificationClientMoral);
        return this;
    }

    public void setNumeroIdentificationClientMoral(String numeroIdentificationClientMoral) {
        this.numeroIdentificationClientMoral = numeroIdentificationClientMoral;
    }

    public String getAdresseClientMoral() {
        return this.adresseClientMoral;
    }

    public ClientMoral adresseClientMoral(String adresseClientMoral) {
        this.setAdresseClientMoral(adresseClientMoral);
        return this;
    }

    public void setAdresseClientMoral(String adresseClientMoral) {
        this.adresseClientMoral = adresseClientMoral;
    }

    public String getEmailClientMoral() {
        return this.emailClientMoral;
    }

    public ClientMoral emailClientMoral(String emailClientMoral) {
        this.setEmailClientMoral(emailClientMoral);
        return this;
    }

    public void setEmailClientMoral(String emailClientMoral) {
        this.emailClientMoral = emailClientMoral;
    }

    public String getTelephoneClientMoral() {
        return this.telephoneClientMoral;
    }

    public ClientMoral telephoneClientMoral(String telephoneClientMoral) {
        this.setTelephoneClientMoral(telephoneClientMoral);
        return this;
    }

    public void setTelephoneClientMoral(String telephoneClientMoral) {
        this.telephoneClientMoral = telephoneClientMoral;
    }

    public String getVilleClientMoral() {
        return this.villeClientMoral;
    }

    public ClientMoral villeClientMoral(String villeClientMoral) {
        this.setVilleClientMoral(villeClientMoral);
        return this;
    }

    public void setVilleClientMoral(String villeClientMoral) {
        this.villeClientMoral = villeClientMoral;
    }

    public String getActiviteClientMoral() {
        return this.activiteClientMoral;
    }

    public ClientMoral activiteClientMoral(String activiteClientMoral) {
        this.setActiviteClientMoral(activiteClientMoral);
        return this;
    }

    public void setActiviteClientMoral(String activiteClientMoral) {
        this.activiteClientMoral = activiteClientMoral;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ClientMoral)) {
            return false;
        }
        return id != null && id.equals(((ClientMoral) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ClientMoral{" +
            "id=" + getId() +
            ", nomClientMoral='" + getNomClientMoral() + "'" +
            ", numeroIdentificationClientMoral='" + getNumeroIdentificationClientMoral() + "'" +
            ", adresseClientMoral='" + getAdresseClientMoral() + "'" +
            ", emailClientMoral='" + getEmailClientMoral() + "'" +
            ", telephoneClientMoral='" + getTelephoneClientMoral() + "'" +
            ", villeClientMoral='" + getVilleClientMoral() + "'" +
            ", activiteClientMoral='" + getActiviteClientMoral() + "'" +
            "}";
    }
}
