package com.icb.ged.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;

/**
 * A Documents.
 */
@Entity
@Table(name = "documents")
public class Documents implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "id_document")
    private Long idDocument;

    @Column(name = "code_qr_document")
    private String codeQRDocument;

    @Column(name = "titre_document")
    private String titreDocument;

    @ManyToOne
    @JsonIgnoreProperties(value = { "dossier" }, allowSetters = true)
    private SousDossier sousDossier;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Documents id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdDocument() {
        return this.idDocument;
    }

    public Documents idDocument(Long idDocument) {
        this.setIdDocument(idDocument);
        return this;
    }

    public void setIdDocument(Long idDocument) {
        this.idDocument = idDocument;
    }

    public String getCodeQRDocument() {
        return this.codeQRDocument;
    }

    public Documents codeQRDocument(String codeQRDocument) {
        this.setCodeQRDocument(codeQRDocument);
        return this;
    }

    public void setCodeQRDocument(String codeQRDocument) {
        this.codeQRDocument = codeQRDocument;
    }

    public String getTitreDocument() {
        return this.titreDocument;
    }

    public Documents titreDocument(String titreDocument) {
        this.setTitreDocument(titreDocument);
        return this;
    }

    public void setTitreDocument(String titreDocument) {
        this.titreDocument = titreDocument;
    }

    public SousDossier getSousDossier() {
        return this.sousDossier;
    }

    public void setSousDossier(SousDossier sousDossier) {
        this.sousDossier = sousDossier;
    }

    public Documents sousDossier(SousDossier sousDossier) {
        this.setSousDossier(sousDossier);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Documents)) {
            return false;
        }
        return id != null && id.equals(((Documents) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Documents{" +
            "id=" + getId() +
            ", idDocument=" + getIdDocument() +
            ", codeQRDocument='" + getCodeQRDocument() + "'" +
            ", titreDocument='" + getTitreDocument() + "'" +
            "}";
    }
}
