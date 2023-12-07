package com.icb.ged.domain;

import java.io.Serializable;
import javax.persistence.*;

/**
 * A GroupClientMoral.
 */
@Entity
@Table(name = "group_client_moral")
public class GroupClientMoral implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nom_groupe_client_moral")
    private String nomGroupeClientMoral;

    @Column(name = "lien_groupe_client_moral")
    private String lienGroupeClientMoral;

    @Column(name = "nombre_client_moral")
    private Integer nombreClientMoral;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public GroupClientMoral id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomGroupeClientMoral() {
        return this.nomGroupeClientMoral;
    }

    public GroupClientMoral nomGroupeClientMoral(String nomGroupeClientMoral) {
        this.setNomGroupeClientMoral(nomGroupeClientMoral);
        return this;
    }

    public void setNomGroupeClientMoral(String nomGroupeClientMoral) {
        this.nomGroupeClientMoral = nomGroupeClientMoral;
    }

    public String getLienGroupeClientMoral() {
        return this.lienGroupeClientMoral;
    }

    public GroupClientMoral lienGroupeClientMoral(String lienGroupeClientMoral) {
        this.setLienGroupeClientMoral(lienGroupeClientMoral);
        return this;
    }

    public void setLienGroupeClientMoral(String lienGroupeClientMoral) {
        this.lienGroupeClientMoral = lienGroupeClientMoral;
    }

    public Integer getNombreClientMoral() {
        return this.nombreClientMoral;
    }

    public GroupClientMoral nombreClientMoral(Integer nombreClientMoral) {
        this.setNombreClientMoral(nombreClientMoral);
        return this;
    }

    public void setNombreClientMoral(Integer nombreClientMoral) {
        this.nombreClientMoral = nombreClientMoral;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GroupClientMoral)) {
            return false;
        }
        return id != null && id.equals(((GroupClientMoral) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GroupClientMoral{" +
            "id=" + getId() +
            ", nomGroupeClientMoral='" + getNomGroupeClientMoral() + "'" +
            ", lienGroupeClientMoral='" + getLienGroupeClientMoral() + "'" +
            ", nombreClientMoral=" + getNombreClientMoral() +
            "}";
    }
}
