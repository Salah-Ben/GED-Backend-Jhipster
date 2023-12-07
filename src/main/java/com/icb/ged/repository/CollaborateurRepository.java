package com.icb.ged.repository;

import com.icb.ged.domain.Collaborateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Collaborateur entity.
 */
@Repository
public interface CollaborateurRepository extends CollaborateurRepositoryWithBagRelationships, JpaRepository<Collaborateur, Long> {
    default Optional<Collaborateur> findOneWithEagerRelationships(Long id) {
        return this.fetchBagRelationships(this.findById(id));
    }

    default List<Collaborateur> findAllWithEagerRelationships() {
        return this.fetchBagRelationships(this.findAll());
    }

    default Page<Collaborateur> findAllWithEagerRelationships(Pageable pageable) {
        return this.fetchBagRelationships(this.findAll(pageable));
    }
}
