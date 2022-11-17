public class Main extends Thread{

    public static int numeroAdivinar;
    public static boolean acertado = false;

    public Main(){
        numeroAdivinar = (int) (Math.random() * 101);
    }

    @Override
    public void run() {
        int res = 0;
        int numero = (int) (Math.random() * 101);
        while (!acertado){
            res = propuestaNumero(numero);
            switch (res){
                case 1 -> System.out.println("Numero acertado: " + numeroAdivinar);
                case -1 -> acertado = true;
            }
        }


    }

    public static int propuestaNumero(int numero) {
        int res = 0;
        if (acertado) {
            res = -1;
            Thread.interrupted();
        }else if (numero==numeroAdivinar){
            res = 1;
            acertado=true;
            Thread.interrupted();
        }


        return res;
    }

    public static void main(String[] args) {
        for (int i=1; i<=10; i++){
            new Main().start();
        }
    }
}