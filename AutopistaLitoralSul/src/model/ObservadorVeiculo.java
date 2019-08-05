package model;

public interface ObservadorVeiculo {
    public void avisarMovimento( int xAnterior, int yAnterior, int x, int y );

    public void avisarFinalizacao(int x, int y, boolean criarNovo);
}
