package com.icb.ged.repository;

import com.icb.ged.domain.ClientPhysique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientPhysique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientPhysiqueRepository extends JpaRepository<ClientPhysique, Long> {}
