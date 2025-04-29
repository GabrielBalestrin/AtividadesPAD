package atividade06;

import java.util.LinkedList;
import java.util.Queue;

public class Balcao {
    private final int capacidade;
    private final Queue<Integer> pratos = new LinkedList<>();

    public Balcao(int capacidade) {
        this.capacidade = capacidade;
    }

    public synchronized void prepararPrato(int prato) throws InterruptedException {
        while (pratos.size() == capacidade) {
            wait(); 
        }
        pratos.add(prato);
        System.out.println("Cozinheiro preparou o prato: " + prato);
        System.out.println("Pratos no balcão: " + pratos.size()); 
        notifyAll(); 
    }


    public synchronized int pegarPrato() throws InterruptedException {
        while (pratos.isEmpty()) {
            wait(); 
        }
        int prato = pratos.poll();
        System.out.println("Garçom pegou o prato: " + prato);
        System.out.println("Pratos no balcão: " + pratos.size());
        notifyAll(); 
        return prato;
    }

}

