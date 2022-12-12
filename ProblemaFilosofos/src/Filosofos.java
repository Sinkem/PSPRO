//No se puede usar prioridad para el interbloqueo porque la prioridad es aleatoria, por lo que he utilizado sincronizacion. Peude tardar en cambiar los
//dos filosofos que estan comiendo.
//
//En esta solucion puede haber alguien que no coma porque el filosofo no llegue a alcanzar los palillos a tiempo porque se le adelante otro.
public class Filosofos extends Thread {
    //Id para identificar al filosofo
    int id;
    //Array de tenedores
    private static Palillo[] palillos = new Palillo[5];

    /**
     * Metodo para que un filosofo coma usando 2 tenedores. Si uno de ellos esta siendo utilizado, sera notificado y esperara hasta que pueda usarlo
     *
     * @param palillo1
     * @param palillo2
     */
    private void filosofoCome(int palillo1, int palillo2) {
        if (palillos[palillo1].isUsado()) {
            System.out.println(this.getName() + ": El palillo de la izquierda esta siendo usado");
        } else if (palillos[palillo2].isUsado()) {
            System.out.println(this.getName() + ": El palillo de la derecha esta siendo usado");
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            System.out.println("Se ha atragantado comiendo");
        }
        //Si no se hacen dos bloques synchronized distintos (uno para coger los palillo y otro para dejarlos) solo come un filosofo a la vez por la espera
        //a la hora de comer
        synchronized (palillos) {
            while (palillos[palillo1].isUsado() || palillos[palillo2].isUsado()) {
                try {
                    palillos.wait();
                } catch (InterruptedException e) {
                    System.out.println("Error en el wait");
                }
            }
            palillos[palillo1].setUsado(true);
            palillos[palillo2].setUsado(true);
        }
        System.out.println("El " + this.getName() + " esta comiendo");
        try {
            Thread.sleep((long) (1000 * (Math.random() * 5) + 1));
        } catch (InterruptedException e) {
            System.out.println("Se ha atragantado comiendo");
        }
        synchronized (palillos){
            palillos[palillo1].setUsado(false);
            palillos[palillo2].setUsado(false);
            palillos.notifyAll();
        }
        System.out.println("El " + this.getName() + " ha comido");

    }


    @Override
    public void run() {
        super.run();
        int tamanhoArray = palillos.length;

        int tenedor1 = 0;
        int tenedor2 = 0;

        if (id == 0) {
            tenedor1 = tamanhoArray - 1;
        } else {
            tenedor1 = id - 1;
            tenedor2 = id;
        }
        while (true) {
            filosofoCome(tenedor1, tenedor2);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < palillos.length; i++) {
            palillos[i] = new Palillo();
        }
        Filosofos filosofo = null;
        for (int i = 0; i < 5; i++) {
            filosofo = new Filosofos();
            filosofo.setName("filosofo " + i);
            filosofo.id = i;
            filosofo.start();
        }

        while (filosofo.isAlive()) {
            System.out.println(filosofo.getState());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                System.out.println("Error al dormir el hilo");
            }
        }
    }
}