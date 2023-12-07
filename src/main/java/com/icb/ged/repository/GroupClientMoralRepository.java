package com.icb.ged.repository;

import com.icb.ged.domain.GroupClientMoral;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the GroupClientMoral entity.
 */
@SuppressWarnings("unused")
@Repository
public interface GroupClientMoralRepository extends JpaRepository<GroupClientMoral, Long> {}
