import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Gestora {
    private byte[] resumen;
    public byte[] getDigest(String mensaje){
        byte[] mensajeBytes;
        try{
            mensajeBytes = mensaje.getBytes("UTF-8");
            MessageDigest algoritmo = MessageDigest.getInstance("SHA-256");
            algoritmo.reset();
            algoritmo.update(mensajeBytes);
            resumen = algoritmo.digest();
        } catch (NoSuchAlgorithmException e){
            System.err.println("El algoritmo seleccionado no existe");
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            System.err.println("No se conoce la codificación especificada");
            throw new RuntimeException(e);
        }

        return resumen;
    }

    public boolean compararResumenes(String resumen1, String resumen2){
        boolean iguales = false;
        if (resumen1.equals(resumen2)){
            iguales = true;
        }
        return iguales;
    }
}
