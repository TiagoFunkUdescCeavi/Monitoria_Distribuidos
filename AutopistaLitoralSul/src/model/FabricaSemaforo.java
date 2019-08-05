package model;

public class FabricaSemaforo implements Fabrica{

    @Override
    public Estrada criarEstrada(int x, int y, String sentido) {
        return new EstradaSemaforo(x, y, sentido);
    }

    @Override
    public Cruzamento criarCruzamento(int x, int y, String sentido) {
        return new CruzamentoSemaforo( x, y, sentido );
    }

    
    
}
