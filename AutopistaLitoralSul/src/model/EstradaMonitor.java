package model;

public class EstradaMonitor extends Estrada{

    public EstradaMonitor(int x, int y, String sentido) {
        super(x, y, sentido);
    }

    @Override
    public synchronized void avancar(Veiculo v) {
        System.out.println("Entrando: " + getX() + " " + getY() );
        andar( v );
        System.out.println("Saindo: " + getX() + " " + getY() );
    }
    
}
