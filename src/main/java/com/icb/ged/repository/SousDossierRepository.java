package com.icb.ged.repository;

import com.icb.ged.domain.SousDossier;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the SousDossier entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SousDossierRepository extends JpaRepository<SousDossier, Long> {}
