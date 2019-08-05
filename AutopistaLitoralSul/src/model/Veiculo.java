package model;

import java.util.ArrayList;
import java.util.List;

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
            System.out.println("false");
            o.avisarFinalizacao( getPedacoMapa().getX(), getPedacoMapa().getY(), false );
        }
    }

    @Override
    public void run() {
//        System.out.println("foi");
        PedacoMapa pm;
        while( !finalizado ){
            pm = getPedacoMapa();
            if( pm != null ){
                pm.avancar( this );
                if( getPedacoMapa() == null ){
                    finalizado = true;
                }else{
                    for( ObservadorVeiculo o : listaObs ){
                        o.avisarMovimento( pm.getX(), pm.getY(), getPedacoMapa().getX(), getPedacoMapa().getY() );
                    }
                }
            }else{
                finalizado = true;
            }
            if( finalizado && !finalizacaoForcada ){
                for( ObservadorVeiculo o : listaObs ){
                    System.out.println("true");
                    o.avisarFinalizacao( pm.getX(), pm.getY(), true );
                }
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
