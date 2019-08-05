package model;

import java.util.concurrent.Semaphore;

public class EstradaSemaforo extends Estrada{

    private Semaphore semaforo = new Semaphore( 1 );
    
    public EstradaSemaforo(int x, int y, String sentido) {
        super(x, y, sentido);
    }

    @Override
    public void avancar(Veiculo v) {
        try {
            semaforo.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        System.out.println("Saindo: " + getX() + " " + getY() );
        andar( v );
        System.out.println("Saindo: " + getX() + " " + getY() );
        semaforo.release();
    }
    
}
