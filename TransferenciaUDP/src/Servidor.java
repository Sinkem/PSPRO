import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.Arrays;

public class Servidor {
    private static final String RUTA_FICHERO = "fichero";
    public static void main(String[] args) {
        String mensaje = "";
        try {
            File fichero = new File(RUTA_FICHERO);

            System.out.println("Creacion del socket");
            DatagramSocket socket = new DatagramSocket(39000);

            while (true){
                boolean fin = false;
                BufferedWriter bw = new BufferedWriter(new FileWriter(fichero, true));
                while (!fin){
                    byte[] bufferEntrada = new byte[64];
                    DatagramPacket packetEntrada = new DatagramPacket(bufferEntrada, bufferEntrada.length);
                    socket.receive(packetEntrada);
                    mensaje = new String(packetEntrada.getData()).trim();

                    bw.write(mensaje);
                    bw.newLine();
                    System.out.println(mensaje);
                    if (mensaje.equals("10000")) {
                        fin = true;
                    }
                }
                bw.close();
            }
        } catch (SocketException e) {
            System.err.println("Error en la creacion del socket");
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.err.println("Error en mensaje" + mensaje);
            throw new RuntimeException(e);
        }
    }
}
