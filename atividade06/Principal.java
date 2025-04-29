package atividade06;

public class Principal {
    public static void main(String[] args) {
        Balcao balcao = new Balcao(5); 

        Cozinheiro cozinheiro = new Cozinheiro(balcao);
        Garcom garcom = new Garcom(balcao);

        cozinheiro.start();
        garcom.start();
    }
}
