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
            
            // tratamento especial da primeira posicao, precisa obter acesso exclusivo
            getPedacoMapa().reservar();
            sleep( PedacoMapa.TEMPO_ESPERA );
            
            while( !finalizado ){
                atual = getPedacoMapa();
                if( atual != null ){
                    caminho = atual.criarCaminho( new ArrayList<>() );
//                    imprimirCaminho(caminho);
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
    
    private boolean imprimirCaminho( List<PedacoMapa> caminho){
        PedacoMapa pm;
        for (int k = 0; k < caminho.size(); k++) {
            pm = caminho.get( k );
            if( pm != null ){
                System.out.print( pm.toString() + " " );
            }
        }
        System.out.println("");
        return true;
    }
    
    private void adquirirAcesso( List< PedacoMapa > caminho ) throws InterruptedException{
        if( caminho.size() == 1 ){
            if( caminho.get( 0 ) != null ){
                caminho.get( 0 ).reservar();
            }
        }else{
            boolean reservou;
            do{
//                System.out.println( "Reservando: " + this.getId() + "-" + caminho.get(0).toString() );
                reservou = true;
                for( int i = 0; i < caminho.size(); i++ ){
                    if( !caminho.get( i ).tentaReservar() ){
                        liberarAcesso(caminho, i);
                        reservou = false;
                        sleep( rand.nextInt(100) );
                        break;
                    }
                }
            }while( !reservou );
//            System.out.println( "Reservando (sucesso): " + this.getId() + "-" + caminho.get(0).toString() );
        }
    }
    
    private void liberarAcesso( List< PedacoMapa > caminho, int cont ){
//        System.out.println( "Liberando: " + this.getId() + "-" + caminho.get(0).toString() );
        for( int i = 0; i < cont; i++ ){
            caminho.get( i ).liberar();
        }
    }
    
    public void adicionarObservador( ObservadorVeiculo obs ){
        listaObs.add( obs );
    }
    
    public void removerObservador( ObservadorVeiculo obs ){
        listaObs.remove( obs );
    }
}
