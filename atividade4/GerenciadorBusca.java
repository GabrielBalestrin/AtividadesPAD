package atividade4;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class GerenciadorBusca {
    private final File[] arquivos;
    private final String termoBusca;
   private final Semaphore semaforo = new Semaphore(2);  

    public GerenciadorBusca(File[] arquivos, String termoBusca) {
        this.arquivos = arquivos;
        this.termoBusca = termoBusca;
       
    }

    public void iniciarBusca() {
        List<Thread> threads = new ArrayList<>();

        for (File arquivo : arquivos) {
            Thread t = new Thread(new BuscaThread(arquivo, termoBusca, semaforo));
            threads.add(t);
            t.start();
        }

        for (Thread t : threads) {
            try {
                t.join();
            } catch (InterruptedException e) {
                System.err.println("Thread interrompida: " + e.getMessage());
            }
        }
    }
}
