package Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class Servidor {
    public static void main(String[] args) {
        System.out.println("Creacion del socket");
        try {
            DatagramSocket socket = new DatagramSocket(39000);
            int numeroGenerado = (int) (Math.random()*100)+1;
            while (true){
                byte[] bufferEntrada = new byte[64];
                DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                System.out.println("(Servidor) Esperando...");
                socket.receive(packetEntrada);

                GestorProcesos gesto = new GestorProcesos(socket, packetEntrada, numeroGenerado);
                gesto.start();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
