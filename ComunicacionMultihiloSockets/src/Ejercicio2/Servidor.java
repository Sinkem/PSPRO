package Ejercicio2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {
    public static void main(String[] args) {
        try {
            ServerSocket socketServer = new ServerSocket(50000);
            Socket peticion;
            while (true) {
                System.out.println("(Servidor) Esperando...");
                peticion = socketServer.accept();

                new GestorProcesos(peticion).start();
            }

        } catch (IOException e) {
            System.out.println("Se ha producido un error con la creacion del servidor");
        }
    }
}
