package ru.sber.singleton;

import ru.sber.exceptions.GenerateTokenException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Генерация токена и пароля
 */
public class GenerateTokenPasswordSequenceSingleton {
    private static GenerateTokenPasswordSequenceSingleton instance = null;

    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";

    private GenerateTokenPasswordSequenceSingleton() {
    }

    public static GenerateTokenPasswordSequenceSingleton getInstance() {
        if (instance == null) {
            instance = new GenerateTokenPasswordSequenceSingleton();
        }
        return instance;
    }

    /**
     * Генерация токена для изменения данных пользователя
     *
     * @return Токен
     */
    public String generateToken() {
        try {
            // Создание случайной последовательности байтов
            byte[] randomBytes = new byte[32];
            secureRandom.nextBytes(randomBytes);

            // Хеширование случайной последовательности с использованием SHA-256
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(randomBytes);

            // Кодирование хеша в Base64
            return Base64.getEncoder().encodeToString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new GenerateTokenException("Не удалось сгенерировать токен");
        }
    }

    /**
     * Генерация пароля при регистрации
     *
     * @return Пароль
     */
    public String generatePassword(int length) {
        String setCharacters = LOWER + UPPER + DIGITS;

        StringBuilder password = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            password.append(setCharacters.charAt(ThreadLocalRandom.current().nextInt(setCharacters.length())));
        }
        return password.toString();
    }
}
