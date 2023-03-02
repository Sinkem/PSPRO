import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Base64;

public class Descifrar {
    public static void main(String[] args) {
        File file = new File("src\\clavePublicaCifrada");
        PrivateKey privateKey = KeyManagerReceptor.getClavePrivada();
        PublicKey publicKey = KeyManager.getClavePublica();

        String mensajeCifrado64 = null;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            mensajeCifrado64 = br.readLine();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] mensajeCifradoPublica = Base64.getDecoder().decode(mensajeCifrado64);

        Cipher cipher;
        byte[] mensajeCifradoPrivada;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();
        int offset = 0;
        int tamanoBloque = (((RSAPrivateKey) privateKey).getModulus().bitLength() + 7) / 8;
        while (offset < mensajeCifradoPublica.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, mensajeCifradoPublica.length - offset);
            byte[] bloqueCifrado = Arrays.copyOfRange(mensajeCifradoPublica, offset, offset + tamanoBloqueActual);
            byte[] bloqueClaro;
            try {
                bloqueClaro = cipher.doFinal(bloqueCifrado);
                bufferSalida.write(bloqueClaro);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            offset += tamanoBloqueActual;
        }
        byte[] mensajeDescifradoPrivado = bufferSalida.toByteArray();

        offset = 0;
        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        tamanoBloque = (((RSAPublicKey) publicKey).getModulus().bitLength() + 7) / 8;
        while (offset < mensajeDescifradoPrivado.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, mensajeDescifradoPrivado.length - offset);
            byte[] bloqueCifrado = Arrays.copyOfRange(mensajeDescifradoPrivado, offset, offset + tamanoBloqueActual);
            byte[] bloqueClaro;
            try {
                bloqueClaro = cipher.doFinal(bloqueCifrado);
                bufferSalida.write(bloqueClaro);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            offset += tamanoBloqueActual;
        }
        byte[] mensajeResultado = bufferSalida.toByteArray();

        String mensaje = new String(mensajeResultado);
        System.out.println("Mensaje: " + mensaje);

        /*
        Cipher cipher;
        byte[] mensajeCifradoPrivada;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            mensajeCifradoPrivada = cipher.doFinal(mensajeCifradoPublica);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        }

        byte[] mensajeCifradoBytes = mensajeCifradoPrivada;

        try {
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();
        int offset = 0;
        int tamanoBloque = (((RSAPublicKey) publicKey).getModulus().bitLength() + 7) / 8;
        while (offset < mensajeCifradoBytes.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, mensajeCifradoBytes.length - offset);
            byte[] bloqueCifrado = Arrays.copyOfRange(mensajeCifradoBytes, offset, offset + tamanoBloqueActual);
            byte[] bloqueClaro;
            try {
                bloqueClaro = cipher.doFinal(bloqueCifrado);
                bufferSalida.write(bloqueClaro);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            offset += tamanoBloqueActual;
        }*/

        //String mensaje = new String(bufferSalida.toByteArray(), StandardCharsets.UTF_8);

    }
}
