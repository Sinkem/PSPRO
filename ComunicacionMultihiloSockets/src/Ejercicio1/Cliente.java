package Ejercicio1;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Cliente {
    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        try {
            InetAddress direccion = InetAddress.getLocalHost();
            int puerto = 39000;

            System.out.println("Creacion del socket");
            DatagramSocket socket = new DatagramSocket();
            String mensaje;

            do {
                System.out.println("Envio de mensajes");
                System.out.println("Introduce el numero");
                mensaje = scn.next();
                byte[] bufferSalida = mensaje.getBytes();
                DatagramPacket packetSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccion, puerto);
                socket.send(packetSalida);

                byte[] bufferEntrada = new byte[64];
                DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                socket.receive(packetEntrada);
                mensaje = new String(packetEntrada.getData()).trim();
                System.out.println(mensaje);
            } while (!mensaje.equals("¡Has acertado!"));


            System.out.println("Cierre del socket");
            socket.close();
        } catch (SocketException e) {
            System.err.println("Error al crear el socket");
        } catch (UnknownHostException e) {
            System.err.println("Error al obtener la direccion de local host");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
