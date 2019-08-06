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
        if( this.getVeiculo() == null ){
            v.setPedacoMapa(this);
            this.setVeiculo( v );
        }
    }
    
    @Override
    public void sair(){
        this.setVeiculo(null);
        semaforo.release();
    }
}
