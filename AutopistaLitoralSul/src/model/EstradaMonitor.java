package model;

import java.util.concurrent.locks.ReentrantLock;

public class EstradaMonitor extends Estrada{

    private ReentrantLock bloqueio = new ReentrantLock(true);
    
    public EstradaMonitor(int x, int y, String sentido) {
        super(x, y, sentido);
    }
    
    @Override
    public void reservar(){
        bloqueio.lock();
    }

    @Override
    public boolean tentaReservar(){
        boolean tentar = bloqueio.tryLock();
        return tentar;
    }

    @Override
    public void avancar(Veiculo v) {
        v.setPedacoMapa(this);
        this.setVeiculo( v );
    }
    
    @Override
    public void liberar(){
        assert bloqueio.isLocked() : "Não esta lockend";
        System.out.println( bloqueio.getHoldCount() );
        bloqueio.unlock();
    }
    
    @Override
    public void sair(){
        this.setVeiculo(null);
    }

    @Override
    public String toString() {
        return super.toString() + bloqueio.toString();
    }
}
