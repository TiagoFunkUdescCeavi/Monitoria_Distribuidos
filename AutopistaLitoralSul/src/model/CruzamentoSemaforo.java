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
            assert semaforo.availablePermits() == 1:"Número de permissões deveria ser igual a um antes de adquirir.";
            semaforo.acquire();
            assert semaforo.availablePermits() == 0:"Número de permissões deveria ser igual a zero depois de adquirir.";
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
        assert this.getVeiculo() == null : "Não deveria ter veículo no PedacaMapa ao avançar.";
        v.setPedacoMapa(this);
        this.setVeiculo( v );
    }
    
    @Override
    public void liberar(){
//        assert this.getVeiculo() == null : "Não deveria ter veículo ao dar o release";
        assert semaforo.availablePermits() == 0:"Número de permissões deveria ser igual a zero antes de liberar.";
        semaforo.release();
        assert semaforo.availablePermits() == 1:"Número de permissões deveria ser igual a zero depois de liberar.";
    }
    
    @Override
    public void sair(){
        this.setVeiculo(null);
    }
    
}
