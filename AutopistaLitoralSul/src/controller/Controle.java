package controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import model.Fabrica;
import model.FabricaMonitor;
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
    
    private Random rand = new Random();

    public Controle() {
        listaObsController = new ArrayList<>();
    }
    
    public void adicionarObservador( ObservadorController obs ){
        listaObsController.add( obs );
    }

    public void removerObservador( ObservadorController obs ){
        listaObsController.remove( obs );
    }
    
    public void carregarMapa(String nomeArquivo, int quantidadeVeiculos, boolean ehSemaforo) {
        this.quantidadeVeiculos = quantidadeVeiculos;
        Fabrica fabrica;
        LeitorArquivo leitor;
        
        if( ehSemaforo ){
            fabrica = new FabricaSemaforo();
            System.out.println("Semaforo");
        }else{
            fabrica = new FabricaMonitor();
            System.out.println("Monitor");
        }
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
        Veiculo v;
        PedacoMapa pm;
        int contador = 0;
        while ( quantidadeVeiculos > contador ) {
            pm = posicoesIniciais.get( rand.nextInt( posicoesIniciais.size() ) );
            if( pm.tentaReservar() ){
                v = new Veiculo( pm );
                
                for( ObservadorController o : listaObsController ){
                    o.cadastrarVeiculo( v );
                }
                v.start();
                pm.liberar();
                contador++;
            }
        }
    }
    
    public void inserirNovoVeiculo(){
        boolean reservou;
        Veiculo v;
        PedacoMapa pm;
        do{
            pm = posicoesIniciais.get( rand.nextInt( posicoesIniciais.size() ) );
            reservou = pm.tentaReservar();
            if( reservou ){
                v = new Veiculo( pm );
                for( ObservadorController o : listaObsController ){
                    o.cadastrarVeiculo( v );
                }
                v.start();
                pm.liberar();
            }
        }while( !reservou );
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
