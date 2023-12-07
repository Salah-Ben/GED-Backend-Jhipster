package com.icb.ged.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.icb.ged.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupClientPhysiqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupClientPhysique.class);
        GroupClientPhysique groupClientPhysique1 = new GroupClientPhysique();
        groupClientPhysique1.setId(1L);
        GroupClientPhysique groupClientPhysique2 = new GroupClientPhysique();
        groupClientPhysique2.setId(groupClientPhysique1.getId());
        assertThat(groupClientPhysique1).isEqualTo(groupClientPhysique2);
        groupClientPhysique2.setId(2L);
        assertThat(groupClientPhysique1).isNotEqualTo(groupClientPhysique2);
        groupClientPhysique1.setId(null);
        assertThat(groupClientPhysique1).isNotEqualTo(groupClientPhysique2);
    }
}
