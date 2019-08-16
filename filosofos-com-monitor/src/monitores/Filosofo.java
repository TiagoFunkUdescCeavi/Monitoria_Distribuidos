package monitores;

public abstract class Filosofo extends Thread {

    protected boolean ativo = true;
    protected int numero;
    protected int porcoesComidas;

    protected Filosofo(int numero) {
        this.numero = numero;
    }

    public void desativar() {
        this.ativo = false;
    }

    public int getPorcoesComidas() {
        return porcoesComidas;
    }

    protected void pensar() {
        System.out.println("Filosofo " + numero + " pensou.");
    }

    protected void comer() {
        System.out.println("Filosofo " + numero + " comeu.");
        porcoesComidas++;
    }
}
