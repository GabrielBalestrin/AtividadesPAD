package atividade06;

public class Garcom extends Thread {
    private final Balcao balcao;

    public Garcom(Balcao balcao) {
        this.balcao = balcao;
    }

    @Override
    public void run() {
        try {
            while (true) {
                balcao.pegarPrato();
                Thread.sleep(1100); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


