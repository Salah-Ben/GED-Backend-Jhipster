package com.icb.ged.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.icb.ged.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientPhysiqueTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientPhysique.class);
        ClientPhysique clientPhysique1 = new ClientPhysique();
        clientPhysique1.setId(1L);
        ClientPhysique clientPhysique2 = new ClientPhysique();
        clientPhysique2.setId(clientPhysique1.getId());
        assertThat(clientPhysique1).isEqualTo(clientPhysique2);
        clientPhysique2.setId(2L);
        assertThat(clientPhysique1).isNotEqualTo(clientPhysique2);
        clientPhysique1.setId(null);
        assertThat(clientPhysique1).isNotEqualTo(clientPhysique2);
    }
}
