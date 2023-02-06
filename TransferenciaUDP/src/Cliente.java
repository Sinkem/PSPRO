import java.io.IOException;
import java.net.*;

public class Cliente {
    public static void main(String[] args) {
        try {
            InetAddress direccion = InetAddress.getLocalHost();
            int puerto = 39000;

            System.out.println("Creacion del socket");
            DatagramSocket socket = new DatagramSocket();

            System.out.println("Envio de mensajes");
            for (int i = 0; i < 10001; i++) {
                String mensaje = String.valueOf(i);
                byte[] bufferSalida = mensaje.getBytes();
                DatagramPacket packetSalida = new DatagramPacket(bufferSalida, bufferSalida.length, direccion, puerto);
                socket.send(packetSalida);
            }

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
