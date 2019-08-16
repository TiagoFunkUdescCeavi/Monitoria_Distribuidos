package model;

import java.util.List;

public abstract class Estrada extends PedacoMapa {

    protected PedacoMapa proximo;

    public Estrada(int x, int y, String sentido) {
        super(x, y, sentido);
    }

    @Override
    public void adicionarCaminho( PedacoMapa pedacoMapa ){
        proximo = pedacoMapa;
    }
    
    @Override
    public boolean precisoProjetarCaminho(){
        return false;
    }
    
    @Override
    public List< PedacoMapa > criarCaminho( List< PedacoMapa > caminho ){
        caminho.add(0, proximo);
        if( proximo != null && proximo.precisoProjetarCaminho() ){
            caminho = proximo.criarCaminho( caminho );
        }
        return caminho;
    }
           
    @Override
    public String getTipo (){
        String s;
        if( super.getVeiculo() != null ){
            s = "estrada_carro";
        }else{
            s = "estrada_" + getSentido();
        }
        return s;
    }
    
    @Override
    public String getInfo(){
        String s = "e:" + getX() + "-" + getY()+":";
        if( proximo == null ){
            s += "nada";
        }else{
            s += proximo.getX() + "-" + proximo.getY();
        }
        return s;
    }
}
