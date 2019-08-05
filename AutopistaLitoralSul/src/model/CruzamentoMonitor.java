package model;

public class CruzamentoMonitor extends Cruzamento{

    public CruzamentoMonitor(int x, int y, String sentido) {
        super(x, y, sentido);
    }

    @Override
    public synchronized void avancar(Veiculo v) {
        System.out.println("Entrando: " + getX() + " " + getY() );
        andar( v );
        System.out.println("Saindo: " + getX() + " " + getY() );
    }
}
