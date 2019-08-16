package monitores;

import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JantarMonitorEficiente {

    public static void main(String[] args) throws IOException, InterruptedException {
        Lock[] garfos = new Lock[5];

        for (int i = 0; i < garfos.length; i++) {
            garfos[i] = new ReentrantLock(true); // Lock com imparcialidade ativa: a thread que está mais tempo esperando é a que será liberada
        }

//																		// esq    , dir
        FilosofoMonitorEficiente fil0 = new FilosofoMonitorEficiente(0, garfos[0], garfos[4]);
        FilosofoMonitorEficiente fil1 = new FilosofoMonitorEficiente(1, garfos[1], garfos[0]);
        FilosofoMonitorEficiente fil2 = new FilosofoMonitorEficiente(2, garfos[2], garfos[1]);
        FilosofoMonitorEficiente fil3 = new FilosofoMonitorEficiente(3, garfos[3], garfos[2]);
        FilosofoMonitorEficiente fil4 = new FilosofoMonitorEficiente(4, garfos[4], garfos[3]);

        fil0.start();
        fil1.start();
        fil2.start();
        fil3.start();
        fil4.start();

        // aguarda o usuario pressionar alguma tecla e então desativa os filosofos
        System.in.read();

        fil0.desativar();
        fil1.desativar();
        fil2.desativar();
        fil3.desativar();
        fil4.desativar();

        // espera filosofos finalizarem para imprimir quantidade de porções comidas
        fil0.join();
        fil1.join();
        fil2.join();
        fil3.join();
        fil4.join();

        System.out.println("Filosofo 0. Porções comidas até agora: " + fil0.getPorcoesComidas());
        System.out.println("Filosofo 1. Porções comidas até agora: " + fil1.getPorcoesComidas());
        System.out.println("Filosofo 2. Porções comidas até agora: " + fil2.getPorcoesComidas());
        System.out.println("Filosofo 3. Porções comidas até agora: " + fil3.getPorcoesComidas());
        System.out.println("Filosofo 4. Porções comidas até agora: " + fil4.getPorcoesComidas());

    }
}
