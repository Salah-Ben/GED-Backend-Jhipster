package com.icb.ged.repository;

import com.icb.ged.domain.GroupClientPhysique;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GroupClientPhysique entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupClientPhysiqueRepository extends JpaRepository<GroupClientPhysique, Long> {}
