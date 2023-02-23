import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.util.Base64;
import java.util.Scanner;

public class Cifrado {
    public static void main(String[] args) {
        Gestora gestora = new Gestora();
        File fichero = new File("src\\mensajes");
        Scanner scn = new Scanner(System.in);
        String contrasenha;
        System.out.println("Introduce la contrasenha (entre 1 y 16 caracteres)");
        contrasenha = scn.nextLine();
        Key key = gestora.obtenerClave(contrasenha);
        System.out.println("Escribir mensaje");
        String mensaje = scn.nextLine();
        byte[] mensajeCifrado = gestora.cifrar(mensaje, key);

        String mensaje64 = null;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fichero))) {
            mensaje64 = Base64.getEncoder().encodeToString(mensajeCifrado);
            bw.write(mensaje64);
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}