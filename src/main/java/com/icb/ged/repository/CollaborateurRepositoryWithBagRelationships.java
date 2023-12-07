package com.icb.ged.repository;

import com.icb.ged.domain.Collaborateur;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CollaborateurRepositoryWithBagRelationships {
    Optional<Collaborateur> fetchBagRelationships(Optional<Collaborateur> collaborateur);

    List<Collaborateur> fetchBagRelationships(List<Collaborateur> collaborateurs);

    Page<Collaborateur> fetchBagRelationships(Page<Collaborateur> collaborateurs);
}
