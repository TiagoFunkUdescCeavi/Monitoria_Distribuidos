package model;

public abstract class PedacoMapa {
    
    private int x;
    private int y;
    private String sentido;
    private Veiculo veiculo;
    
    protected final int TEMPO_ESPERA = 500;

    public PedacoMapa(int x, int y, String sentido) {
        this.x = x;
        this.y = y;
        this.sentido = sentido;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public String getSentido() {
        return sentido;
    }

    public void setSentido(String sentido) {
        this.sentido = sentido;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
    
    public void andar(Veiculo v){
        try {
            Thread.sleep( TEMPO_ESPERA );
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        setVeiculo( null );
        v.setPedacoMapa( this.informarProximo() );
    }
    
    public abstract void adicionarCaminho( PedacoMapa pedacoMapa );
    public abstract void avancar(Veiculo v);
    public abstract PedacoMapa informarProximo();
       
    public abstract String getTipo();
    public abstract String getInfo();

}