package com.icb.ged.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.icb.ged.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class GroupClientMoralTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GroupClientMoral.class);
        GroupClientMoral groupClientMoral1 = new GroupClientMoral();
        groupClientMoral1.setId(1L);
        GroupClientMoral groupClientMoral2 = new GroupClientMoral();
        groupClientMoral2.setId(groupClientMoral1.getId());
        assertThat(groupClientMoral1).isEqualTo(groupClientMoral2);
        groupClientMoral2.setId(2L);
        assertThat(groupClientMoral1).isNotEqualTo(groupClientMoral2);
        groupClientMoral1.setId(null);
        assertThat(groupClientMoral1).isNotEqualTo(groupClientMoral2);
    }
}
