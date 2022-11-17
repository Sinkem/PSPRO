public class Main extends Thread {
    private String nombre;

    public Main(){}

    public Main(String nombre){
        this.nombre = nombre;
    }

    public void ejecutar(){
        while (true){
            System.out.println("Soy el bucle " + nombre + " y estoy trabajando");
            try {
                int tiempoEjecucion = (int) (Math.random() * 10000) + 1000;
                Thread.sleep(tiempoEjecucion);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void run(){
        this.ejecutar();
    }

    public static void main(String[] args) {
        Main hilo1 = new Main("1");
        Main hilo2 = new Main("2");
        Main hilo3 = new Main("3");
        Main hilo4 = new Main("4");
        Main hilo5 = new Main("5");

        hilo1.start();
        hilo2.start();
        hilo3.start();
        hilo4.start();
        hilo5.start();
    }
}