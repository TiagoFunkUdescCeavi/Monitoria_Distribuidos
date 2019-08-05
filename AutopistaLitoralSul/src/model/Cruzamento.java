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
    public PedacoMapa informarProximo(){
        if( caminhos.isEmpty() ){
            return null;
        }
        int proximo = new Random().nextInt( caminhos.size() );
        PedacoMapa pm = caminhos.get( proximo );
        return pm;
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
