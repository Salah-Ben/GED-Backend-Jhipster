package com.icb.ged.repository;

import com.icb.ged.domain.Collaborateur;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.annotations.QueryHints;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class CollaborateurRepositoryWithBagRelationshipsImpl implements CollaborateurRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Collaborateur> fetchBagRelationships(Optional<Collaborateur> collaborateur) {
        return collaborateur.map(this::fetchDossiers);
    }

    @Override
    public Page<Collaborateur> fetchBagRelationships(Page<Collaborateur> collaborateurs) {
        return new PageImpl<>(
            fetchBagRelationships(collaborateurs.getContent()),
            collaborateurs.getPageable(),
            collaborateurs.getTotalElements()
        );
    }

    @Override
    public List<Collaborateur> fetchBagRelationships(List<Collaborateur> collaborateurs) {
        return Optional.of(collaborateurs).map(this::fetchDossiers).orElse(Collections.emptyList());
    }

    Collaborateur fetchDossiers(Collaborateur result) {
        return entityManager
            .createQuery(
                "select collaborateur from Collaborateur collaborateur left join fetch collaborateur.dossiers where collaborateur is :collaborateur",
                Collaborateur.class
            )
            .setParameter("collaborateur", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Collaborateur> fetchDossiers(List<Collaborateur> collaborateurs) {
        return entityManager
            .createQuery(
                "select distinct collaborateur from Collaborateur collaborateur left join fetch collaborateur.dossiers where collaborateur in :collaborateurs",
                Collaborateur.class
            )
            .setParameter("collaborateurs", collaborateurs)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
