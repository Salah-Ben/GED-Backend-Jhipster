package com.icb.ged.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.icb.ged.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SousDossierTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SousDossier.class);
        SousDossier sousDossier1 = new SousDossier();
        sousDossier1.setId(1L);
        SousDossier sousDossier2 = new SousDossier();
        sousDossier2.setId(sousDossier1.getId());
        assertThat(sousDossier1).isEqualTo(sousDossier2);
        sousDossier2.setId(2L);
        assertThat(sousDossier1).isNotEqualTo(sousDossier2);
        sousDossier1.setId(null);
        assertThat(sousDossier1).isNotEqualTo(sousDossier2);
    }
}
