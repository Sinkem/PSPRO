public class Estudiante extends Thread{

    private static Libro[] librosUsados = new Libro[9];

    @Override
    public void run() {
        super.run();
        try {
            int libro1 = (int)(Math.random()*9);
            int libro2 = (int)(Math.random()*9);
            while (libro2 == libro1){
                libro2 = (int) (Math.random() * 9);
            }
            synchronized (librosUsados[libro1]){
                synchronized (librosUsados[libro2]){
                    while (librosUsados[libro1].isUsandose() || librosUsados[libro2].isUsandose()){
                        librosUsados[libro1].wait();
                    }
                    librosUsados[libro1].setUsandose(true);
                    librosUsados[libro2].setUsandose(true);
                    System.out.println("El " + this.getName() + " esta usando los libros " + (libro1+1) + " y " + (libro2+1));
                    Thread.sleep((long) (1000*(Math.random()*3+3)));
                    librosUsados[libro1].setUsandose(false);
                    librosUsados[libro2].setUsandose(false);
                    librosUsados[libro1].notifyAll();
                    librosUsados[libro2].notifyAll();
                    System.out.println("El " + this.getName() + " ha devuelto los libros " + (libro1+1) +" y " + (libro2+1));
                }
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < librosUsados.length; i++) {
            librosUsados[i] = new Libro();
        }
        for (int i = 0; i < 4; i++) {
            Estudiante estudiante = new Estudiante();
            estudiante.setName("estudiante " + (i+1));
            estudiante.start();
        }
    }
}
