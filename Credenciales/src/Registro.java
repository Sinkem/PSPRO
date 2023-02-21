import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Scanner;

public class Registro {
    public static void main(String[] args) {
        Gestora gestora = new Gestora();
        File fichero = new File("credenciales.cre");

        Scanner scn = new Scanner(System.in);
        System.out.println("Introduce tu usuario");
        String usuario = scn.next();
        System.out.println("Introduce tu contrasenha");
        String contrasenha = scn.next();

        byte[] contrasenhaEncoded = gestora.getDigest(contrasenha);
        String contrasenha64 = String.format("%064x", new BigInteger(1, contrasenhaEncoded));

        try {
            FileWriter fw = new FileWriter(fichero, true);
            fw.write(usuario+":"+contrasenha64);
            fw.write(System.lineSeparator());
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}