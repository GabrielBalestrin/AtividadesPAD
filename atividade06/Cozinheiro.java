package atividade06;

public class Cozinheiro extends Thread {
    private final Balcao balcao;

    public Cozinheiro(Balcao balcao) {
        this.balcao = balcao;
    }

    @Override
    public void run() {
        try {
            int prato = 1;
            while (true) {
                balcao.prepararPrato(prato++);
                Thread.sleep(1000); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
