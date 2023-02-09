package Ejercicio2;

import java.io.*;
import java.net.Socket;

public class GestorProcesos extends Thread {
    private Socket socket;
    private OutputStream os;
    private InputStream is;
    private File fichero;

    public GestorProcesos(Socket socket) {
        super();
        this.socket = socket;
        this.fichero = new File("Fichero");
    }

    @Override
    public void run() {
        realizarProceso();
    }

    private void realizarProceso(){
        try {
            os = this.socket.getOutputStream();
            is = this.socket.getInputStream();

            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String url = br.readLine();

            FileReader fr = new FileReader(this.fichero);
            String ip ="No nos queda esa pagina ninho, solo gugol.com";
            br = new BufferedReader(fr);
            String lineaLeida;
            while ((lineaLeida = br.readLine()) != null && ip.equals("No nos queda esa pagina ninho, solo gugol.com")) {
                String lineaMap[] = lineaLeida.trim().split(":");
                if(lineaMap[0].equals(url)){
                    ip = lineaMap[1];
                }
            }

            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(ip);
            bw.newLine();
            bw.flush();

            br.close();
            fr.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
