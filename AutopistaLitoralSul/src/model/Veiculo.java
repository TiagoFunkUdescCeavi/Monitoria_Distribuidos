package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Veiculo extends Thread{
    
    private PedacoMapa pedacoMapa;
    private boolean finalizado;
    private boolean finalizacaoForcada;
    private int cor;
    
    private Random rand = new Random();
    
    private List<ObservadorVeiculo> listaObs = new ArrayList<>();

    public Veiculo(PedacoMapa pedacoMapa) {
        finalizado = false;
        this.pedacoMapa = pedacoMapa;
        pedacoMapa.setVeiculo( this );
        this.cor = rand.nextInt( 5 );
    }

    public void setPedacoMapa(PedacoMapa pedacoMapa) {
        this.pedacoMapa = pedacoMapa;
        pedacoMapa.setVeiculo( this );
    }

    public PedacoMapa getPedacoMapa() {
        return pedacoMapa;
    }

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
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
        try {
            PedacoMapa atual;
            PedacoMapa proximo;
            List< PedacoMapa > caminho;
            
            while( !finalizado ){
                atual = getPedacoMapa();
                if( atual != null ){
//                    boolean deuBoa;
//                    do{
//                        caminho = atual.criarCaminho();
//                        deuBoa = adquirirAcesso(caminho);
//                    }while( !deuBoa );
                    caminho = atual.criarCaminho( new ArrayList<>() );
                    estaOk(caminho);
                    adquirirAcesso(caminho);
                    
                    for( int i = 0; i < caminho.size(); i++ ){
                        proximo = caminho.get( i );
                        
                        if( proximo != null ){
                            proximo.avancar(this);
                            atual.sair();
                            for( ObservadorVeiculo o : listaObs ){
                                o.avisarMovimento( atual.getX(), atual.getY(), proximo.getX(), proximo.getY() );
                            }
                            atual.liberar();
                            atual = proximo;
                        }else{
                            atual.sair();
                            atual.liberar();
                            finalizado = true;
                        }
                        sleep( PedacoMapa.TEMPO_ESPERA );
                    }
                }else{
                    finalizado = true;
                }
                if( finalizado && !finalizacaoForcada ){
                    for( ObservadorVeiculo o : listaObs ){
                        o.avisarFinalizacao( atual.getX(), atual.getY(), true );
                    }
                }
                
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
    //private 
    
    private boolean estaOk( List<PedacoMapa> caminho){
        PedacoMapa pm;
        for (int i = 0; i < caminho.size(); i++) {
            pm = caminho.get( i );
            for (int j = i+1; j < caminho.size(); j++) {
                if( pm.equals( caminho.get( j ) ) ){
                    System.out.println("Eita !!!");
                    for (int k = 0; k < caminho.size(); k++) {
                        System.out.print( caminho.get( k ).toString() + " " );
                    }
                    System.out.println("");
                    return false;
                }
            }
        }
        return true;
    }
    
    private int acessos = 0;
    
    private void adquirirAcesso( List< PedacoMapa > caminho ) throws InterruptedException{
        if( caminho.size() == 1 ){
            if( caminho.get( 0 ) != null ){
                caminho.get( 0 ).reservar();
            }
        }else{
            boolean reservou;
            do{
                reservou = true;
                for( int i = 0; i < caminho.size(); i++ ){
                    if( !caminho.get( i ).tentaReservar() ){
                        liberarAcesso(caminho, i);
                        reservou = false;
                        sleep( rand.nextInt(100) );
                        break;
                    }else{
                        acessos++;
                    }
                }
            }while( !reservou );
        }
    }
    
    private void liberarAcesso( List< PedacoMapa > caminho, int cont ){
        for( int i = 0; i < cont; i++ ){
            caminho.get( i ).liberar();
            acessos--;
        }
//        System.out.println( this.getId() + ":" + acessos);
    }
    
    public void adicionarObservador( ObservadorVeiculo obs ){
        listaObs.add( obs );
    }
    
    public void removerObservador( ObservadorVeiculo obs ){
        listaObs.remove( obs );
    }
}
