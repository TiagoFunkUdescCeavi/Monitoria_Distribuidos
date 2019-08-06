package model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Veiculo extends Thread{
    
    private PedacoMapa pedacoMapa;
    private boolean finalizado;
    private boolean finalizacaoForcada;
    
    private List<ObservadorVeiculo> listaObs = new ArrayList<>();

    public Veiculo(PedacoMapa pedacoMapa) {
        finalizado = false;
        this.pedacoMapa = pedacoMapa;
        pedacoMapa.setVeiculo( this );
    }

    public void setPedacoMapa(PedacoMapa pedacoMapa) {
        this.pedacoMapa = pedacoMapa;
        if( pedacoMapa != null ){
            pedacoMapa.setVeiculo( this );
        }
    }

    public PedacoMapa getPedacoMapa() {
        return pedacoMapa;
    }
    
    public void finalizar(){
        finalizado = true;
        finalizacaoForcada = true;
        for( ObservadorVeiculo o : listaObs ){
            o.avisarFinalizacao( getPedacoMapa().getX(), getPedacoMapa().getY(), false );
        }
    }

    @Override
    public void run() {
        PedacoMapa atual;
        PedacoMapa proximo;
        while( !finalizado ){
            atual = getPedacoMapa();
            proximo = atual.informarProximo();
            if( atual != null ){
                if( proximo != null ){
                    proximo.avancar(this);
                    atual.sair();
                    for( ObservadorVeiculo o : listaObs ){
                        o.avisarMovimento( atual.getX(), atual.getY(), proximo.getX(), proximo.getY() );
                    }
                }else{
                    finalizado = true;
                    atual.sair();
                }
            }else{
                finalizado = true;
            }
            if( finalizado && !finalizacaoForcada ){
                for( ObservadorVeiculo o : listaObs ){
                    o.avisarFinalizacao( atual.getX(), atual.getY(), true );
                }
            }
            
            try {
                Thread.sleep( PedacoMapa.TEMPO_ESPERA );
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    public void adicionarObservador( ObservadorVeiculo obs ){
        listaObs.add( obs );
    }
    
    public void removerObservador( ObservadorVeiculo obs ){
        listaObs.remove( obs );
    }
}
