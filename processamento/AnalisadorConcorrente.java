package processamento;

import modelo.Transacao;

import java.util.*;
import java.util.concurrent.*;

public class AnalisadorConcorrente {
    public static Map<String, Double> analisar(List<Transacao> transacoes) throws InterruptedException, ExecutionException {
        Map<String, List<Transacao>> porCategoria = new HashMap<>();

        for (Transacao t : transacoes) {
            porCategoria.computeIfAbsent(t.getCategoria(), k -> new ArrayList<>()).add(t);
        }

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<Map.Entry<String, Double>>> resultados = new ArrayList<>();

        for (Map.Entry<String, List<Transacao>> entrada : porCategoria.entrySet()) {
            Callable<Map.Entry<String, Double>> tarefa = () -> {
                double soma = 0;
                for (Transacao t : entrada.getValue()) {
                    soma += t.getTipo() == Transacao.Tipo.ENTRADA ? t.getValor() : -t.getValor();
                }
                return new AbstractMap.SimpleEntry<>(entrada.getKey(), soma);
            };
            resultados.add(executor.submit(tarefa));
        }

        Map<String, Double> totais = new HashMap<>();
        for (Future<Map.Entry<String, Double>> futuro : resultados) {
            Map.Entry<String, Double> resultado = futuro.get();
            totais.put(resultado.getKey(), resultado.getValue());
        }

        executor.shutdown();
        return totais;
    }
}
