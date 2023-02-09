package Ejercicio2;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try {
            // 1 - Crear un socket de tipo cliente indicando IP y puerto del servidor
            System.out.println("(Cliente): Creación del socket...");
            InetAddress direccion = InetAddress.getLocalHost();
            Socket socketCliente = new Socket(direccion, 50000);

            // 2 - Abrir flujos de lectura y escritura
            InputStream is = socketCliente.getInputStream();
            OutputStream os = socketCliente.getOutputStream();

            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);

            // 3 - Intercambiar datos con el servidor
            System.out.println("(Cliente) Introduzca la direccion (Spoiler: solo esta gugol.com): ");
            String url = scn.nextLine();
            bw.write(url);
            bw.newLine();
            bw.flush();
            // Leo mensajes que me envía el servidor
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String linea = br.readLine();
            System.out.println("El servidor me envía el siguiente mensaje: " + linea);

            // 4 - Cerrar flujos de lectura y escritura
            bw.close();
            osw.close();
            is.close();
            os.close();

            // 5 - Cerrar la conexión
            System.out.println("(Cliente) Cerrando conexiones...");
            socketCliente.close();
            System.out.println("(Cliente) Conexiones cerradas...");

        } catch (UnknownHostException e) {
            System.err.println("No se encuentra el host especificado.");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("Se ha producido un error en la conexión con el servidor.");
            e.printStackTrace();
        }
    }
}
