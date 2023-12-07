package com.icb.ged.repository;

import com.icb.ged.domain.ClientMoral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClientMoral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClientMoralRepository extends JpaRepository<ClientMoral, Long> {}
