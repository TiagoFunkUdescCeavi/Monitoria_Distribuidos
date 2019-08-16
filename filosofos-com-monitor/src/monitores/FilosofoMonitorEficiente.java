package monitores;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class FilosofoMonitorEficiente extends Filosofo {

    private Lock garfoEsquerdo;
    private Lock garfoDireito;

    public FilosofoMonitorEficiente(int numero, Lock garfoEsq, Lock garfoDir) {
        super(numero);
        this.garfoEsquerdo = garfoEsq;
        this.garfoDireito = garfoDir;
    }

    @Override
    public void run() {
        Random rand = new Random();
        try {
            while (ativo) {
                pensar();
                boolean comeu = false;
                do {
                    System.out.println("Filosofo " + numero + " tentando pegar garfo esquerdo.");
                    garfoEsquerdo.lock();
                    System.out.println("Filosofo " + numero + " tentando pegar garfo direito.");
                    boolean pegou = garfoDireito.tryLock(100, TimeUnit.MILLISECONDS);
                    if (pegou) {
                        comer();
                        comeu = true;
                        // libera os 2 garfos
                        garfoDireito.unlock();
                        garfoEsquerdo.unlock();
                    } else {
                        // libera o garfo que havia tomado posse e aguarda
                        System.out.println("Filosofo " + numero + " não conseguiu pegar garfo direito. Liberando o esquerdo.");
                        garfoEsquerdo.unlock();
                        sleep(100 + rand.nextInt(400)); // solução funcional é aguardar tempo aleatório, diminui chance de starvation
                    }
                } while (!comeu);
            }
        } catch (InterruptedException e) {
            System.out.println("Filosofo " + numero + " interrompido. Abortando.");
            e.printStackTrace();
        }
    }
}
