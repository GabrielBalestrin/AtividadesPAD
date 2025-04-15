package atividade5Threads;

import java.io.*;
import java.util.*;

public class ProcessadorFitas implements Runnable {
    private String entrada;
    private String saida;

    public ProcessadorFitas(String entrada, String saida) {
        this.entrada = entrada;
        this.saida = saida;
    }

    @Override
    public void run() {
    	
        int totalFitas = 0, fitasValidas = 0, fitasInvalidas = 0;
        List<String> novaFita = new ArrayList<>();
        List<String> fitasInvalidasLista = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(entrada))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                totalFitas++;
                if (isFitaValida(linha)) {
                    novaFita.add(gerarFitaComplementar(linha));
                    fitasValidas++;
                } else {
                    novaFita.add("***FITA INVALIDA - " + linha);
                    fitasInvalidasLista.add("Fita " + totalFitas + ": " + linha);
                    fitasInvalidas++;
                }
            }

            try (BufferedWriter bw = new BufferedWriter(new FileWriter(saida))) {
                for (String fita : novaFita) {
                    bw.write(fita);
                    bw.newLine();
                }
            }

            synchronized (System.out) {
                System.out.println("Arquivo: " + new File(entrada).getName());
                System.out.println("Total de fitas: " + totalFitas);
                System.out.println("Fitas válidas: " + fitasValidas);
                System.out.println("Fitas inválidas: " + fitasInvalidas);
                if (!fitasInvalidasLista.isEmpty()) {
                    for (String f : fitasInvalidasLista) {
                        System.out.println(f);
                    }
                }
                System.out.println("---------------------------------------------");
            }

        } catch (IOException e) {
            System.err.println("Erro ao processar " + entrada + ": " + e.getMessage());
        }
    }

    private static boolean isFitaValida(String fita) {
        return fita.matches("[ATCG]+");  
    }

    private static String gerarFitaComplementar(String fita) {
        StringBuilder sb = new StringBuilder();
        for (char base : fita.toCharArray()) {
            switch (base) {
                case 'A': sb.append('T'); break;
                case 'T': sb.append('A'); break;
                case 'C': sb.append('G'); break;
                case 'G': sb.append('C'); break;
            }
        }
        return sb.toString();
    }
}
