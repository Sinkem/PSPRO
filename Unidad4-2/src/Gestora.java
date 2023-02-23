import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Gestora {
    private static final int LONGITUD_BLOQUE = 16;
    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";

    public String descifrar(String line, Key key) {
        String mensajeDescifrado = "";
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] mensaje = cipher.doFinal(Base64.getDecoder().decode(line));
            mensajeDescifrado = new String(mensaje);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            System.err.println("Contraseña Incorrecta");
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
        return mensajeDescifrado;
    }

    public Key obtenerClave(String pass) {
        StringBuffer sb = new StringBuffer(pass);
        sb.setLength(LONGITUD_BLOQUE);
        Key clave = new SecretKeySpec(sb.toString().getBytes(), 0, LONGITUD_BLOQUE, "AES");
        return clave;
    }

    public byte[] cifrar(String mensaje, Key key) {
        byte[] mensajeCifrado;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            mensajeCifrado = cipher.doFinal(mensaje.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        return mensajeCifrado;
    }
}
