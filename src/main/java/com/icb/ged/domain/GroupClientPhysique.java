package com.icb.ged.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A GroupClientPhysique.
 */
@Entity
@Table(name = "group_client_physique")
public class GroupClientPhysique implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_groupe_physique")
    private String nomGroupePhysique;

    @Column(name = "lien_groupe_physique")
    private String lienGroupePhysique;

    @Column(name = "nombre_personne_physique")
    private Integer nombrePersonnePhysique;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroupClientPhysique id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGroupePhysique() {
        return this.nomGroupePhysique;
    }

    public GroupClientPhysique nomGroupePhysique(String nomGroupePhysique) {
        this.setNomGroupePhysique(nomGroupePhysique);
        return this;
    }

    public void setNomGroupePhysique(String nomGroupePhysique) {
        this.nomGroupePhysique = nomGroupePhysique;
    }

    public String getLienGroupePhysique() {
        return this.lienGroupePhysique;
    }

    public GroupClientPhysique lienGroupePhysique(String lienGroupePhysique) {
        this.setLienGroupePhysique(lienGroupePhysique);
        return this;
    }

    public void setLienGroupePhysique(String lienGroupePhysique) {
        this.lienGroupePhysique = lienGroupePhysique;
    }

    public Integer getNombrePersonnePhysique() {
        return this.nombrePersonnePhysique;
    }

    public GroupClientPhysique nombrePersonnePhysique(Integer nombrePersonnePhysique) {
        this.setNombrePersonnePhysique(nombrePersonnePhysique);
        return this;
    }

    public void setNombrePersonnePhysique(Integer nombrePersonnePhysique) {
        this.nombrePersonnePhysique = nombrePersonnePhysique;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupClientPhysique)) {
            return false;
        }
        return id != null && id.equals(((GroupClientPhysique) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupClientPhysique{" +
            "id=" + getId() +
            ", nomGroupePhysique='" + getNomGroupePhysique() + "'" +
            ", lienGroupePhysique='" + getLienGroupePhysique() + "'" +
            ", nombrePersonnePhysique=" + getNombrePersonnePhysique() +
            "}";
    }
}
