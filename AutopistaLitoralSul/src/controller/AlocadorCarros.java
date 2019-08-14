package controller;

import java.util.List;
import java.util.Random;
import model.ObservadorController;
import model.PedacoMapa;
import model.Veiculo;

public class AlocadorCarros extends Thread{
    
    private int quantidadeVeiculos;
    
    private PedacoMapa[][] mapa;
    
    private List<PedacoMapa> posicoesIniciais;
    private List<PedacoMapa> posicoesFinais;
    
    private List<ObservadorController> listaObsController;
    
    private Random rand = new Random();

    public AlocadorCarros(int quantidadeVeiculos, PedacoMapa[][] mapa, List<PedacoMapa> posicoesIniciais, List<ObservadorController> listaObsController) {
        this.quantidadeVeiculos = quantidadeVeiculos;
        this.mapa = mapa;
        this.posicoesIniciais = posicoesIniciais;
        this.listaObsController = listaObsController;
    }

    public void setQuantidadeVeiculos(int quantidadeVeiculos) {
        this.quantidadeVeiculos = quantidadeVeiculos;
    }

    @Override
    public void run() {
        Veiculo v;
        PedacoMapa pm;
        for( int i = 0; i < quantidadeVeiculos; i++ ){
            pm = posicoesIniciais.get( rand.nextInt( posicoesIniciais.size() ) );
            if( !pm.tentaReservar() ){
                v = new Veiculo( pm );
                for( ObservadorController o : listaObsController ){
                    o.cadastrarVeiculo( v );
                }
                v.start();
                System.out.println("Carro " + i);
            }else{
                i--;
            }
        }
    }
    
}
