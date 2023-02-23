import java.io.*;
import java.security.Key;
import java.util.Scanner;

public class Descifrar {
    public static void main(String[] args) {
        Gestora gestora = new Gestora();
        File fichero = new File("src\\mensajes");
        Scanner scn = new Scanner(System.in);
        String contrasenha;
        System.out.println("Ingrese la contraseña");
        contrasenha = scn.nextLine();
        Key key = gestora.obtenerClave(contrasenha);

        try (BufferedReader br = new BufferedReader(new FileReader(fichero))) {
            String line = br.readLine();
            while (line != null) {
                String mensajeDescifrado = gestora.descifrar(line, key);
                System.out.println(mensajeDescifrado);
                line = br.readLine();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
