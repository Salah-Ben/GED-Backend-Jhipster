package com.icb.ged.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.icb.ged.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClientMoralTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientMoral.class);
        ClientMoral clientMoral1 = new ClientMoral();
        clientMoral1.setId(1L);
        ClientMoral clientMoral2 = new ClientMoral();
        clientMoral2.setId(clientMoral1.getId());
        assertThat(clientMoral1).isEqualTo(clientMoral2);
        clientMoral2.setId(2L);
        assertThat(clientMoral1).isNotEqualTo(clientMoral2);
        clientMoral1.setId(null);
        assertThat(clientMoral1).isNotEqualTo(clientMoral2);
    }
}
