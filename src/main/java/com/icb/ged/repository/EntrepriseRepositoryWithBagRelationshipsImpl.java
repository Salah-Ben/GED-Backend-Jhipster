package com.icb.ged.repository;

import com.icb.ged.domain.Entreprise;
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
public class EntrepriseRepositoryWithBagRelationshipsImpl implements EntrepriseRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Entreprise> fetchBagRelationships(Optional<Entreprise> entreprise) {
        return entreprise.map(this::fetchClients);
    }

    @Override
    public Page<Entreprise> fetchBagRelationships(Page<Entreprise> entreprises) {
        return new PageImpl<>(fetchBagRelationships(entreprises.getContent()), entreprises.getPageable(), entreprises.getTotalElements());
    }

    @Override
    public List<Entreprise> fetchBagRelationships(List<Entreprise> entreprises) {
        return Optional.of(entreprises).map(this::fetchClients).orElse(Collections.emptyList());
    }

    Entreprise fetchClients(Entreprise result) {
        return entityManager
            .createQuery(
                "select entreprise from Entreprise entreprise left join fetch entreprise.clients where entreprise is :entreprise",
                Entreprise.class
            )
            .setParameter("entreprise", result)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getSingleResult();
    }

    List<Entreprise> fetchClients(List<Entreprise> entreprises) {
        return entityManager
            .createQuery(
                "select distinct entreprise from Entreprise entreprise left join fetch entreprise.clients where entreprise in :entreprises",
                Entreprise.class
            )
            .setParameter("entreprises", entreprises)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();
    }
}
