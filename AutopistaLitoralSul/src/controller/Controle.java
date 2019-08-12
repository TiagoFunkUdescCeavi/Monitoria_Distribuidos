package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.Fabrica;
import model.FabricaSemaforo;
import model.ObservadorController;
import model.PedacoMapa;
import model.Veiculo;
import utils.LeitorArquivo;

public class Controle {
    
    private int quantidadeVeiculos;
    
    private PedacoMapa[][] mapa;
    
    private List<PedacoMapa> posicoesIniciais;
    private List<PedacoMapa> posicoesFinais;
    
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
    
    public void carregarMapa(String nomeArquivo, int quantidadeVeiculos) {
        this.quantidadeVeiculos = quantidadeVeiculos;
        Fabrica fabrica = new FabricaSemaforo();
        LeitorArquivo leitor;
        leitor = new LeitorArquivo(nomeArquivo, fabrica);
        try {
            leitor.carregarMapa();
        }catch (FileNotFoundException ex){
            JOptionPane.showMessageDialog(null, "Arquivo não foi encontrado!!");
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
        try {
        Veiculo v;
        int quantidade = quantidadeVeiculos/posicoesIniciais.size();
        if ( quantidade == 0 ) quantidade++;
        for (int i = 0; i <=  quantidade; i++) {
            for (int j = 0; j < quantidadeVeiculos && j < posicoesIniciais.size(); j++) {
                    v = new Veiculo( posicoesIniciais.get( j ) );
                    for( ObservadorController o : listaObsController ){
                        o.cadastrarVeiculo( v );
                    }
                    v.start();
                }
                Thread.sleep( PedacoMapa.TEMPO_ESPERA );
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
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
