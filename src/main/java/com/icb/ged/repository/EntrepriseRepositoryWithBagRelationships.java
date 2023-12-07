package com.icb.ged.repository;

import com.icb.ged.domain.Entreprise;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface EntrepriseRepositoryWithBagRelationships {
    Optional<Entreprise> fetchBagRelationships(Optional<Entreprise> entreprise);

    List<Entreprise> fetchBagRelationships(List<Entreprise> entreprises);

    Page<Entreprise> fetchBagRelationships(Page<Entreprise> entreprises);
}
