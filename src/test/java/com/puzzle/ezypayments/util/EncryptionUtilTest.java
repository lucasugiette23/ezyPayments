package com.puzzle.ezypayments.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionUtilTest {

    private final EncryptionUtil encryptionUtil = new EncryptionUtil();

    @Test
    void encryptReturnsEncryptedStringForValidInput() {
        String plainText = "testString";

        String encryptedText = encryptionUtil.encrypt(plainText);

        assertNotNull(encryptedText);
        assertNotEquals(plainText, encryptedText);
    }

    @Test
    void encryptThrowsRuntimeExceptionForNullInput() {
        assertThrows(RuntimeException.class, () -> encryptionUtil.encrypt(null));
    }

    @Test
    void decryptReturnsOriginalStringForValidEncryptedInput() {
        String plainText = "testString";
        String encryptedText = encryptionUtil.encrypt(plainText);

        String decryptedText = encryptionUtil.decrypt(encryptedText);

        assertEquals(plainText, decryptedText);
    }

    @Test
    void decryptThrowsRuntimeExceptionForInvalidEncryptedInput() {
        String invalidEncryptedText = "invalidEncryptedString";

        assertThrows(RuntimeException.class, () -> encryptionUtil.decrypt(invalidEncryptedText));
    }

    @Test
    void decryptThrowsRuntimeExceptionForNullInput() {
        assertThrows(RuntimeException.class, () -> encryptionUtil.decrypt(null));
    }

}