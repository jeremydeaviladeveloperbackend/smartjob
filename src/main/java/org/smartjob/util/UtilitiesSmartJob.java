package org.smartjob.util;

import org.smartjob.exceptions.SmartJobException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Clase de utilidades para operaciones comunes en SmartJob.
 * <p>
 * Esta clase proporciona métodos estáticos para operaciones de utilidad,
 * como el hashing de contraseñas.
 * </p>
 *
 * @author Jeremy De Avila
 * @version 1.0
 */
public class UtilitiesSmartJob {

    /**
     * Constructor privado para prevenir la instanciación de esta clase de utilidades.
     */
    private UtilitiesSmartJob() {
    }

    /**
     * Genera un salt aleatorio para el hashing de contraseñas.
     * <p>
     * Utiliza SecureRandom para generar 16 bytes aleatorios que se convierten
     * a una cadena hexadecimal.
     * </p>
     *
     * @return String con el salt en formato hexadecimal
     */
    private static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] saltBytes = new byte[16];
        random.nextBytes(saltBytes);

        StringBuilder sb = new StringBuilder();
        for (byte b : saltBytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    /**
     * Genera un hash SHA-256 de la contraseña proporcionada.
     * <p>
     * Este método genera un salt aleatorio, lo concatena con la contraseña,
     * y luego genera un hash SHA-256 del resultado. El hash se retorna como
     * una cadena hexadecimal.
     * </p>
     *
     * @param password Contraseña en texto plano a hashear
     * @return String con el hash de la contraseña en formato hexadecimal
     * @throws SmartJobException si ocurre un error al generar el hash
     */
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = generateSalt() + password;
            byte[] hashBytes = digest.digest(saltedPassword.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new SmartJobException("Error al generar el hash.");
        }
    }


}
