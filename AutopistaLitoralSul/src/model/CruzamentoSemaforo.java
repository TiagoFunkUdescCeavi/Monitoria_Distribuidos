package model;

import java.util.concurrent.Semaphore;

public class CruzamentoSemaforo extends Cruzamento{
    
    private Semaphore semaforo = new Semaphore( 1 );

    public CruzamentoSemaforo(int x, int y, String sentido) {
        super(x, y, sentido);
    }
    
    @Override
    public void reservar(){
        try {
            semaforo.acquire();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public boolean tentaReservar(){
        return semaforo.tryAcquire();
    }

    @Override
    public void avancar(Veiculo v) {
        v.setPedacoMapa(this);
        this.setVeiculo( v );
    }
    
    @Override
    public void liberar(){
        semaforo.release();
    }
    
    @Override
    public void sair(){
        this.setVeiculo(null);
    }
    
}
