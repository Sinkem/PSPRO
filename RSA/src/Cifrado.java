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
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

public class Cifrado {
    public static void main(String[] args) {
        File fichero = new File("src\\fichero");
        PublicKey clavePublicaReceptor = KeyManagerReceptor.getClavePublica();
        PrivateKey clavePrivadaEmisor = KeyManager.getClavePrivada();

        String mensaje = null;
        try (BufferedReader br = new BufferedReader(new FileReader(fichero))){
            String linea;
            while ((linea = br.readLine()) != null) {
                mensaje = linea;
            }
            mensaje.trim();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        byte[] mensajeBytes = mensaje.getBytes(StandardCharsets.UTF_8);

        Cipher cipher;
        byte[] mensajeCifradoPrivada;
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clavePrivadaEmisor);
            mensajeCifradoPrivada = cipher.doFinal(mensajeBytes);
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

        /*
        String mensajeCifradoPrivada64 = Base64.getEncoder().encodeToString(mensajeCifradoPrivada);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\clavePrivadaCifrada"))){
            bw.write(mensajeCifradoPrivada64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

        try {
            cipher.init(Cipher.ENCRYPT_MODE, clavePublicaReceptor);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        byte[] mensajeCifradoPublica;
        int tamanoBloque = (((RSAPublicKey)clavePublicaReceptor).getModulus().bitLength() + 7) / 8 - 11;
        ByteArrayOutputStream bufferSalida = new ByteArrayOutputStream();

        int offset = 0;
        while (offset < mensajeCifradoPrivada.length) {
            int tamanoBloqueActual = Math.min(tamanoBloque, mensajeCifradoPrivada.length - offset);
            byte[] bloqueCifrado;
            try {
                bloqueCifrado = cipher.doFinal(mensajeCifradoPrivada, offset, tamanoBloqueActual);
                bufferSalida.write(bloqueCifrado);
            } catch (IllegalBlockSizeException e) {
                throw new RuntimeException(e);
            } catch (BadPaddingException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            offset += tamanoBloqueActual;
        }
        mensajeCifradoPublica = bufferSalida.toByteArray();

        String mensajeCifrado64 = Base64.getEncoder().encodeToString(mensajeCifradoPublica);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\clavePublicaCifrada"))){
            bw.write(mensajeCifrado64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    /*
        try {
            cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, clavePublica);
            mensajeCifradoPublica = cipher.doFinal(mensajeBytes);
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

        String mensajeCifradoPublica64 = Base64.getEncoder().encodeToString(mensajeCifradoPublica);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("src\\clavePublicaCifrada"))){
            bw.write(mensajeCifradoPublica64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        */

    }
}