import java.util.concurrent.Semaphore;

public class Main extends Thread {
    private String nombre;
    private boolean atendidoCarniceria = false;
    private boolean atendidoCharcuteria = false;
    private boolean atendido = false;
    private static Semaphore semaphore = new Semaphore(4);
    private static Semaphore charcuteria = new Semaphore(2);

    public Main(String nombre){
        this.nombre = nombre;
    }

    public void atenderCarniceria() {
        try {
            semaphore.acquire(1);
            System.out.println("El cliente " + nombre + " esta siendo atendido por carniceria");
            Thread.sleep((long) (Math.random()*10000));
            System.out.println("El cliente " + nombre + " ha sido atendido en la carniceria");
            semaphore.release(1);
            atendidoCarniceria = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void atenderCharcuteria() {
        try {
            charcuteria.acquire(1);
            System.out.println("El cliente " + nombre + " esta siendo atendido por charcuteria");
            Thread.sleep((long) (Math.random()*10000));
            System.out.println("El cliente " + nombre + " ha sido atendido en la charcuteria");
            charcuteria.release(1);
            atendidoCharcuteria = true;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run(){
        while (!atendido){
            if (!atendidoCarniceria && semaphore.availablePermits() > 0){
                atenderCarniceria();
            }
            if (!atendidoCharcuteria && charcuteria.availablePermits() > 0){
                atenderCharcuteria();
            }
            if (atendidoCarniceria && atendidoCharcuteria){
                atendido = true;
            }
        }
        System.out.println("El cliente " + nombre + " ha sido atendido");
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Main cliente = new Main(String.valueOf(i));
            cliente.start();
        }
    }
}