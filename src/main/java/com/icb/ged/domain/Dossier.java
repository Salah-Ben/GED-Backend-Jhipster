package com.icb.ged.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Dossier.
 */
@Entity
@Table(name = "dossier")
public class Dossier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dossier_id")
    private Long dossierID;

    @Column(name = "titre_dossier")
    private String titreDossier;

    @Column(name = "type_dossier")
    private String typeDossier;

    @Column(name = "type_service")
    private String typeService;

    @Column(name = "emplacement_dossier_physique")
    private String emplacementDossierPhysique;

    @Column(name = "qr_code_dossier")
    private String qrCodeDossier;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clientPhysique", "clientMoral", "groupClientMoral", "groupClientPhysique" }, allowSetters = true)
    private Client client;

    @ManyToMany(mappedBy = "dossiers")
    @JsonIgnoreProperties(value = { "entreprise", "dossiers" }, allowSetters = true)
    private Set<Collaborateur> collaborateurs = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Dossier id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getDossierID() {
        return this.dossierID;
    }

    public Dossier dossierID(Long dossierID) {
        this.setDossierID(dossierID);
        return this;
    }

    public void setDossierID(Long dossierID) {
        this.dossierID = dossierID;
    }

    public String getTitreDossier() {
        return this.titreDossier;
    }

    public Dossier titreDossier(String titreDossier) {
        this.setTitreDossier(titreDossier);
        return this;
    }

    public void setTitreDossier(String titreDossier) {
        this.titreDossier = titreDossier;
    }

    public String getTypeDossier() {
        return this.typeDossier;
    }

    public Dossier typeDossier(String typeDossier) {
        this.setTypeDossier(typeDossier);
        return this;
    }

    public void setTypeDossier(String typeDossier) {
        this.typeDossier = typeDossier;
    }

    public String getTypeService() {
        return this.typeService;
    }

    public Dossier typeService(String typeService) {
        this.setTypeService(typeService);
        return this;
    }

    public void setTypeService(String typeService) {
        this.typeService = typeService;
    }

    public String getEmplacementDossierPhysique() {
        return this.emplacementDossierPhysique;
    }

    public Dossier emplacementDossierPhysique(String emplacementDossierPhysique) {
        this.setEmplacementDossierPhysique(emplacementDossierPhysique);
        return this;
    }

    public void setEmplacementDossierPhysique(String emplacementDossierPhysique) {
        this.emplacementDossierPhysique = emplacementDossierPhysique;
    }

    public String getQrCodeDossier() {
        return this.qrCodeDossier;
    }

    public Dossier qrCodeDossier(String qrCodeDossier) {
        this.setQrCodeDossier(qrCodeDossier);
        return this;
    }

    public void setQrCodeDossier(String qrCodeDossier) {
        this.qrCodeDossier = qrCodeDossier;
    }

    public Client getClient() {
        return this.client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Dossier client(Client client) {
        this.setClient(client);
        return this;
    }

    public Set<Collaborateur> getCollaborateurs() {
        return this.collaborateurs;
    }

    public void setCollaborateurs(Set<Collaborateur> collaborateurs) {
        if (this.collaborateurs != null) {
            this.collaborateurs.forEach(i -> i.removeDossier(this));
        }
        if (collaborateurs != null) {
            collaborateurs.forEach(i -> i.addDossier(this));
        }
        this.collaborateurs = collaborateurs;
    }

    public Dossier collaborateurs(Set<Collaborateur> collaborateurs) {
        this.setCollaborateurs(collaborateurs);
        return this;
    }

    public Dossier addCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.add(collaborateur);
        collaborateur.getDossiers().add(this);
        return this;
    }

    public Dossier removeCollaborateur(Collaborateur collaborateur) {
        this.collaborateurs.remove(collaborateur);
        collaborateur.getDossiers().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dossier)) {
            return false;
        }
        return id != null && id.equals(((Dossier) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Dossier{" +
            "id=" + getId() +
            ", dossierID=" + getDossierID() +
            ", titreDossier='" + getTitreDossier() + "'" +
            ", typeDossier='" + getTypeDossier() + "'" +
            ", typeService='" + getTypeService() + "'" +
            ", emplacementDossierPhysique='" + getEmplacementDossierPhysique() + "'" +
            ", qrCodeDossier='" + getQrCodeDossier() + "'" +
            "}";
    }
}
