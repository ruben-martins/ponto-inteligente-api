package com.rubenmartins.pontointeligente.api.utils;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class PasswordUtilsTest {

    private static final String SENHA = "123456";
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Test
    public void testSenhaNula() {
        assertNull(PasswordUtils.gerarBCrypt(null));
    }

    @Test
    public void testGerarHashSenha() {
        String hash = encoder.encode(SENHA);
        assertTrue(encoder.matches(SENHA, hash));
    }
}
