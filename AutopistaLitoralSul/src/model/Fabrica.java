package model;

public interface Fabrica {
    
    public Estrada criarEstrada( int x, int y, String sentido );
    public Cruzamento criarCruzamento( int x, int y, String sentido );
}
