package model;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class CruzamentoMonitor extends Cruzamento{
    
    private Lock bloqueio = new ReentrantLock(true);

    public CruzamentoMonitor(int x, int y, String sentido) {
        super(x, y, sentido);
    }
    
    @Override
    public void reservar(){
        bloqueio.lock();
    }
    
    @Override
    public boolean tentaReservar(){
//        return bloqueio.tryLock();
        try {
            return bloqueio.tryLock(new Random().nextInt( TEMPO_TENTATIVA ), TimeUnit.MILLISECONDS);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public void avancar(Veiculo v) {
        v.setPedacoMapa(this);
        this.setVeiculo( v );
    }
    
    @Override
    public void liberar(){
        bloqueio.unlock();
    }
    
    @Override
    public void sair(){
        this.setVeiculo(null);
    }
    
}