package com.icb.ged.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * A Collaborateur.
 */
@Entity
@Table(name = "collaborateur")
public class Collaborateur implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "collaborateur_id")
    private Long collaborateurID;

    @Column(name = "nom_collaborateur")
    private String nomCollaborateur;

    @Column(name = "prenom_collaborateur")
    private String prenomCollaborateur;

    @Column(name = "email_collaborateur")
    private String emailCollaborateur;

    @Column(name = "telephone_collaborateur")
    private String telephoneCollaborateur;

    @Column(name = "cin_collaborateur")
    private String cinCollaborateur;

    @Column(name = "adresse_collaborateur")
    private String adresseCollaborateur;

    @Column(name = "ville_collaborateur")
    private String villeCollaborateur;

    @ManyToOne
    @JsonIgnoreProperties(value = { "clients" }, allowSetters = true)
    private Entreprise entreprise;

    @ManyToMany
    @JoinTable(
        name = "rel_collaborateur__dossier",
        joinColumns = @JoinColumn(name = "collaborateur_id"),
        inverseJoinColumns = @JoinColumn(name = "dossier_id")
    )
    @JsonIgnoreProperties(value = { "client", "collaborateurs" }, allowSetters = true)
    private Set<Dossier> dossiers = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Collaborateur id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollaborateurID() {
        return this.collaborateurID;
    }

    public Collaborateur collaborateurID(Long collaborateurID) {
        this.setCollaborateurID(collaborateurID);
        return this;
    }

    public void setCollaborateurID(Long collaborateurID) {
        this.collaborateurID = collaborateurID;
    }

    public String getNomCollaborateur() {
        return this.nomCollaborateur;
    }

    public Collaborateur nomCollaborateur(String nomCollaborateur) {
        this.setNomCollaborateur(nomCollaborateur);
        return this;
    }

    public void setNomCollaborateur(String nomCollaborateur) {
        this.nomCollaborateur = nomCollaborateur;
    }

    public String getPrenomCollaborateur() {
        return this.prenomCollaborateur;
    }

    public Collaborateur prenomCollaborateur(String prenomCollaborateur) {
        this.setPrenomCollaborateur(prenomCollaborateur);
        return this;
    }

    public void setPrenomCollaborateur(String prenomCollaborateur) {
        this.prenomCollaborateur = prenomCollaborateur;
    }

    public String getEmailCollaborateur() {
        return this.emailCollaborateur;
    }

    public Collaborateur emailCollaborateur(String emailCollaborateur) {
        this.setEmailCollaborateur(emailCollaborateur);
        return this;
    }

    public void setEmailCollaborateur(String emailCollaborateur) {
        this.emailCollaborateur = emailCollaborateur;
    }

    public String getTelephoneCollaborateur() {
        return this.telephoneCollaborateur;
    }

    public Collaborateur telephoneCollaborateur(String telephoneCollaborateur) {
        this.setTelephoneCollaborateur(telephoneCollaborateur);
        return this;
    }

    public void setTelephoneCollaborateur(String telephoneCollaborateur) {
        this.telephoneCollaborateur = telephoneCollaborateur;
    }

    public String getCinCollaborateur() {
        return this.cinCollaborateur;
    }

    public Collaborateur cinCollaborateur(String cinCollaborateur) {
        this.setCinCollaborateur(cinCollaborateur);
        return this;
    }

    public void setCinCollaborateur(String cinCollaborateur) {
        this.cinCollaborateur = cinCollaborateur;
    }

    public String getAdresseCollaborateur() {
        return this.adresseCollaborateur;
    }

    public Collaborateur adresseCollaborateur(String adresseCollaborateur) {
        this.setAdresseCollaborateur(adresseCollaborateur);
        return this;
    }

    public void setAdresseCollaborateur(String adresseCollaborateur) {
        this.adresseCollaborateur = adresseCollaborateur;
    }

    public String getVilleCollaborateur() {
        return this.villeCollaborateur;
    }

    public Collaborateur villeCollaborateur(String villeCollaborateur) {
        this.setVilleCollaborateur(villeCollaborateur);
        return this;
    }

    public void setVilleCollaborateur(String villeCollaborateur) {
        this.villeCollaborateur = villeCollaborateur;
    }

    public Entreprise getEntreprise() {
        return this.entreprise;
    }

    public void setEntreprise(Entreprise entreprise) {
        this.entreprise = entreprise;
    }

    public Collaborateur entreprise(Entreprise entreprise) {
        this.setEntreprise(entreprise);
        return this;
    }

    public Set<Dossier> getDossiers() {
        return this.dossiers;
    }

    public void setDossiers(Set<Dossier> dossiers) {
        this.dossiers = dossiers;
    }

    public Collaborateur dossiers(Set<Dossier> dossiers) {
        this.setDossiers(dossiers);
        return this;
    }

    public Collaborateur addDossier(Dossier dossier) {
        this.dossiers.add(dossier);
        dossier.getCollaborateurs().add(this);
        return this;
    }

    public Collaborateur removeDossier(Dossier dossier) {
        this.dossiers.remove(dossier);
        dossier.getCollaborateurs().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Collaborateur)) {
            return false;
        }
        return id != null && id.equals(((Collaborateur) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Collaborateur{" +
            "id=" + getId() +
            ", collaborateurID=" + getCollaborateurID() +
            ", nomCollaborateur='" + getNomCollaborateur() + "'" +
            ", prenomCollaborateur='" + getPrenomCollaborateur() + "'" +
            ", emailCollaborateur='" + getEmailCollaborateur() + "'" +
            ", telephoneCollaborateur='" + getTelephoneCollaborateur() + "'" +
            ", cinCollaborateur='" + getCinCollaborateur() + "'" +
            ", adresseCollaborateur='" + getAdresseCollaborateur() + "'" +
            ", villeCollaborateur='" + getVilleCollaborateur() + "'" +
            "}";
    }
}
