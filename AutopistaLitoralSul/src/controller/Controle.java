package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import model.Cruzamento;
import model.Fabrica;
import model.FabricaMonitor;
import model.FabricaSemaforo;
import model.ObservadorController;
import model.PedacoMapa;
import model.Veiculo;
import utils.LeitorArquivo;

public class Controle {

    
    private static final String NOME_ARQUIVO = "src/malhas/malha-exemplo-3.txt";
    
    private int quantidadeVeiculos;
    
    private PedacoMapa[][] mapa;
    
    private List<Cruzamento> posicoesIniciais;
    private List<Cruzamento> posicoesFinais;
    
    private List<ObservadorController> listaObsController;

    public Controle() {
        listaObsController = new ArrayList<>();
    }
    
    public void adicionarObservador( ObservadorController obs ){
        listaObsController.add( obs );
    }

    public void removerObservador( ObservadorController obs ){
        listaObsController.remove( obs );
    }
    
    public void carregarMapa(int quantidadeVeiculos, boolean semaforo) {
        this.quantidadeVeiculos = quantidadeVeiculos;
        Fabrica fabrica;
        LeitorArquivo leitor;
        if (semaforo) {
            fabrica = new FabricaSemaforo();
        } else {
            fabrica = new FabricaMonitor();
        }
        leitor = new LeitorArquivo(NOME_ARQUIVO, fabrica);
        try {
            leitor.carregarMapa();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        mapa = leitor.getMatriz();
        posicoesIniciais = leitor.getPosicoesIniciais();
        posicoesFinais = leitor.getPosicoesFinais();
        
        inserirMapaNaView();
        inserirVeiculos();
    }

    private void inserirMapaNaView() {
        for( ObservadorController o : listaObsController ){
            o.inserirMapa( mapa );
        }
    }

    private void inserirVeiculos() {
        Veiculo v;
        System.out.println( quantidadeVeiculos/posicoesIniciais.size() + 1 );
        for (int i = 0; i < quantidadeVeiculos/posicoesIniciais.size() + 1; i++) {
            for (int j = 0; j < quantidadeVeiculos && j < posicoesIniciais.size(); j++) {
                v = new Veiculo( posicoesIniciais.get( j ) );
                for( ObservadorController o : listaObsController ){
                    o.cadastrarVeiculo( v );
                }
                v.start();
            }
        }
    }
    
    public void inserirNovoVeiculo(){
        Veiculo v;
        int numero = new Random().nextInt( posicoesIniciais.size() );
        v = new Veiculo( posicoesIniciais.get( numero ) );
        for( ObservadorController o : listaObsController ){
            o.cadastrarVeiculo( v );
        }
        v.start();
    }

    public void finalizarTudo() {
        for (int i = 0; i < mapa.length; i++) {
            for (int j = 0; j < mapa[i].length; j++) {
                if( mapa[i][j] != null ){
                    if( mapa[i][j].getVeiculo() != null ){
                        mapa[i][j].getVeiculo().finalizar();
                    }
                }
            }
        }
    }
}
