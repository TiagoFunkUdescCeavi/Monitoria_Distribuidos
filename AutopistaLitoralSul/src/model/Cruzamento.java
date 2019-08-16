package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Cruzamento extends PedacoMapa{
    
    protected List<PedacoMapa> caminhos;

    public Cruzamento(int x, int y, String sentido) {
        super(x, y, sentido);
        caminhos = new ArrayList();
    }
    
    @Override
    public void adicionarCaminho( PedacoMapa pedacoMapa ){
        caminhos.add( pedacoMapa );
    }
    
    @Override
    public boolean precisoProjetarCaminho(){
        return true;
    }
    
    @Override
    public List< PedacoMapa > criarCaminho( List< PedacoMapa > caminho ){
        boolean estaEmLoop;
        PedacoMapa pm;
        if( caminhos.isEmpty() ){
            return caminho;
        }
        do{
            int proximo = new Random().nextInt( caminhos.size() );
            pm = caminhos.get( proximo );
            estaEmLoop = estaRepetido(caminho, pm);
            
            if( !estaEmLoop ){
                caminho.add( pm );
            }
            
        }while( estaEmLoop );
        if( pm.precisoProjetarCaminho() ){
            caminho = pm.criarCaminho( caminho );
        }
        return caminho;
    }
    
    private boolean estaRepetido( List<PedacoMapa> caminho, PedacoMapa pm){
        for (int i = 0; i < caminho.size(); i++) {
            if( pm.equals( caminho.get( i ) ) ){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getTipo(){
        String s = "cruzamento";
        if( super.getVeiculo() != null ){
            s += "_carro";
        }
        return s;
    }
    
    @Override
    public String getInfo(){
        String s = "c:" + getX() + "-" + getY()+":";
        if( caminhos.isEmpty() ){
            s += "nada";
        }else{
            for( PedacoMapa p : caminhos ){
                s += p.getX() + "-" + p.getY() + " ";
            }
        }
        return s;
    }
    
    
}
