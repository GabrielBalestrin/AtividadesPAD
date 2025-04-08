package atividade4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Semaphore;

public class BuscaThread implements Runnable {
    private final File arquivo;
    private final String termoBusca;
    private final Semaphore semaforo;

    public BuscaThread(File arquivo, String termoBusca, Semaphore semaforo) {
        this.arquivo = arquivo;
        this.termoBusca = termoBusca.toLowerCase();
        this.semaforo = semaforo;
    }

    @Override
    public void run() {
        try {
            semaforo.acquire();

            BufferedReader reader = new BufferedReader(new FileReader(arquivo));
            String linha;
            int numLinha = 0;

            while ((linha = reader.readLine()) != null) {
                numLinha++;
                if (linha.toLowerCase().contains(termoBusca)) {
                    System.out.printf("%s - linha: %02d - %s%n", arquivo.getName(), numLinha, linha);
                }
            }

            reader.close();
        } catch (Exception e) {
            System.err.println("Erro ao ler " + arquivo.getName() + ": " + e.getMessage());
        } finally {
            semaforo.release();
        }
    }
}
