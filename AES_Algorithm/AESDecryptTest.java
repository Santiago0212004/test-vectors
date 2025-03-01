package AES_Algorithm;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.util.Scanner;

class AESDecryptTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Solicitar la clave en hexadecimal
        System.out.print("Ingrese la clave en hexadecimal (16 bytes para AES-128): ");
        String keyHex = scanner.nextLine();

        // Solicitar el mensaje cifrado en hexadecimal
        System.out.print("Ingrese el mensaje cifrado en hexadecimal: ");
        String cphHex = scanner.nextLine();

        // Solicitar el resultado esperado en hexadecimal
        System.out.print("Ingrese el resultado esperado en hexadecimal: ");
        String expHex = scanner.nextLine();

        try {
            // Convertir las entradas hexadecimales a bytes
            byte[] theKey = hexToBytes(keyHex);
            byte[] theCph = hexToBytes(cphHex);
            byte[] theExp = hexToBytes(expHex);

            // Verificar que la clave tenga 16 bytes (AES-128)
            if (theKey == null || theKey.length != 16) {
                System.out.println("La clave debe tener 16 bytes (32 caracteres hexadecimales).");
                return;
            }

            // Verificar que el mensaje cifrado tenga 16 bytes (para NoPadding)
            if (theCph == null || theCph.length != 16) {
                System.out.println("El mensaje cifrado debe tener 16 bytes (32 caracteres hexadecimales) para usar NoPadding.");
                return;
            }

            // Crear la clave AES
            SecretKeySpec ks = new SecretKeySpec(theKey, "AES");

            // Configurar el descifrado AES en modo ECB con NoPadding
            Cipher cf = Cipher.getInstance("AES/ECB/NoPadding");
            cf.init(Cipher.DECRYPT_MODE, ks);

            // Descifrar el mensaje
            byte[] theMsg = cf.doFinal(theCph);

            // Mostrar los resultados en el formato solicitado
            System.out.println("\nKey     : " + bytesToHex(theKey));
            System.out.println("Cipher  : " + bytesToHex(theCph));
            System.out.println("Expected: " + bytesToHex(theExp));
            System.out.println("Message : " + bytesToHex(theMsg));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            scanner.close(); // Cerrar el scanner
        }
    }

    // Método para convertir una cadena hexadecimal en un arreglo de bytes
    public static byte[] hexToBytes(String str) {
        if (str == null || str.length() < 2) {
            return null;
        }
        int len = str.length() / 2;
        byte[] buffer = new byte[len];
        for (int i = 0; i < len; i++) {
            buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return buffer;
    }

    // Método para convertir un arreglo de bytes en una cadena hexadecimal
    public static String bytesToHex(byte[] data) {
        if (data == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (byte b : data) {
            str.append(String.format("%02X", b));
        }
        return str.toString();
    }
}