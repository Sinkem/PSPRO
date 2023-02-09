package Ejercicio1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class GestorProcesos extends Thread{
    private DatagramSocket socket;
    private DatagramPacket datagramEntrada;

    private int numeroGenerado;

    public GestorProcesos(DatagramSocket socket, DatagramPacket datagramEntrada, int numeroGenerado){
        super();
        this.socket = socket;
        this.datagramEntrada = datagramEntrada;
        this.numeroGenerado = numeroGenerado;
    }

    @Override
    public void run(){
        realizarProceso(numeroGenerado);
    }

    private void realizarProceso(int numeroGenerado) {
        System.out.println(numeroGenerado);
        int numero = -1;

        do {
            String mensaje = new String(datagramEntrada.getData());
            System.out.println("(Servidor) Mensaje recibido: " + mensaje.trim());

            numero = Integer.parseInt(mensaje.trim());
            System.out.println(numero);
            mensaje = comprobacion(numero, numeroGenerado);

            System.out.println("(Servidor) Enviando mensaje");
            byte[] bufferSalida = new String(mensaje).getBytes();
            DatagramPacket packetSalida = new DatagramPacket(bufferSalida, bufferSalida.length, datagramEntrada.getAddress(), datagramEntrada.getPort());
            try{
                socket.send(packetSalida);
            } catch (IOException e){
                System.err.println("Error enviando el mensaje");
            }
            byte[] bufferEntrada = new byte[64];
            datagramEntrada.setData(bufferEntrada);
        } while (numero != numeroGenerado);
    }

    private static String comprobacion(int num, int numGenerado){
        String resultado;
        if (num == numGenerado){
            resultado = "¡Has acertado!";
        } else if (num < numGenerado){
            resultado = "El numero introducido es menor que el objetivo";
        } else {
            resultado = "El numero introducido es mayor que el objetivo";
        }

        return resultado;
    }
}
