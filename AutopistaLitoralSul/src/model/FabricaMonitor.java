package model;

public class FabricaMonitor implements Fabrica{

    @Override
    public Estrada criarEstrada( int x, int y, String sentido ) {
        return new EstradaMonitor( x, y, sentido );
    }

    @Override
    public Cruzamento criarCruzamento( int x, int y, String sentido ) {
        return new CruzamentoMonitor(x, y, sentido);
    }
    
}
