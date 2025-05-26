package processamento;

import modelo.Transacao;

import java.util.*;
import java.util.concurrent.*;

public class AnalisadorConcorrenteBlocos {
    public static Map<String, Double> analisar(List<Transacao> transacoes) throws InterruptedException, ExecutionException {
        int numThreads = Runtime.getRuntime().availableProcessors();
        int tamanhoBloco = (int) Math.ceil(transacoes.size() / (double) numThreads);

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<Map<String, Double>>> resultados = new ArrayList<>();

        for (int i = 0; i < transacoes.size(); i += tamanhoBloco) {
            int inicio = i;
            int fim = Math.min(i + tamanhoBloco, transacoes.size());

            Callable<Map<String, Double>> tarefa = () -> {
                Map<String, Double> subtotal = new HashMap<>();
                for (int j = inicio; j < fim; j++) {
                    Transacao t = transacoes.get(j);
                    double valorReal = t.getTipo() == Transacao.Tipo.ENTRADA ? t.getValor() : -t.getValor();
                    subtotal.merge(t.getCategoria(), valorReal, Double::sum);
                    try { Thread.sleep(1); } catch (InterruptedException ignored) {}
                }
                return subtotal;
            };

            resultados.add(executor.submit(tarefa));
        }

        Map<String, Double> totais = new HashMap<>();
        for (Future<Map<String, Double>> futuro : resultados) {
            Map<String, Double> parcial = futuro.get();
            for (Map.Entry<String, Double> entrada : parcial.entrySet()) {
                totais.merge(entrada.getKey(), entrada.getValue(), Double::sum);
            }
        }

        executor.shutdown();
        return totais;
    }
}
