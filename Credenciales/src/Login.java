import java.io.*;
import java.math.BigInteger;
import java.util.Base64;
import java.util.HexFormat;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Gestora gestora = new Gestora();
        File fichero = new File("credenciales.cre");

        String usuarioFichero = null;
        String contrasenhaFichero = null;
        boolean iguales = false;

        Scanner scn = new Scanner(System.in);
        System.out.println("Introduce tu usuario");
        String usuario = scn.next();
        System.out.println("Introduce tu contrasenha");
        String contrasenha = scn.next();

        byte[] contrasenhaEncoded = gestora.getDigest(contrasenha);
        String contrasenha64 = String.format("%064x", new BigInteger(1, contrasenhaEncoded));

        try {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            String linea = br.readLine();
            while (linea != null && !usuario.equals(usuarioFichero)) {
                String[] lineaDividida = linea.split(":");
                usuarioFichero = lineaDividida[0];
                contrasenhaFichero = lineaDividida[1];
                linea = br.readLine();
            }
            if (usuarioFichero.equals(usuario)) {
                iguales = gestora.compararResumenes(contrasenha64, contrasenhaFichero);
            }

            if (iguales){
                System.out.println("Inicio de sesion correcto");
            } else {
                System.out.println("Inicio de sesion incorrecto");
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
