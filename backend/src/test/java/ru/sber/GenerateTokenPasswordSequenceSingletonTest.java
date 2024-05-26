package ru.sber;

import org.junit.jupiter.api.Test;
import ru.sber.singleton.GenerateTokenPasswordSequenceSingleton;

import java.security.NoSuchAlgorithmException;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateTokenPasswordSequenceSingletonTest {
    @Test
    public void testNotNullGenerateToken() throws NoSuchAlgorithmException {
        GenerateTokenPasswordSequenceSingleton instance = GenerateTokenPasswordSequenceSingleton.getInstance();

        assertNotNull(instance.generateToken());
    }

    @Test
    public void testCreateSingletonObjectGenerateToken() {
        GenerateTokenPasswordSequenceSingleton instance1 = GenerateTokenPasswordSequenceSingleton.getInstance();
        GenerateTokenPasswordSequenceSingleton instance2 = GenerateTokenPasswordSequenceSingleton.getInstance();

        assertEquals(instance1, instance2);
    }

    @Test
    public void testUniquenessGenerateToken() throws NoSuchAlgorithmException {
        GenerateTokenPasswordSequenceSingleton instance = GenerateTokenPasswordSequenceSingleton.getInstance();

        assertNotEquals(instance.generateToken(), instance.generateToken());
    }
}
