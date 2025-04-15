package atividade5Threads;

import java.io.File;

public class ReplicacaoDNA {
    public static void main(String[] args) {
        String pasta = "G:\\Meu Drive\\Faculdade\\7ª fase\\Programação Alto Desempenho\\Atividade5\\arquivosDNA\\";
        String pastaSaida = pasta + "complementares\\";

        File dirSaida = new File(pastaSaida);
        if (!dirSaida.exists()) {
            dirSaida.mkdirs();
        }

        for (int i = 0; i < 10; i++) {
            String entrada = pasta + "dna-" + i + ".txt";
            String saida = pastaSaida + "dna-" + i + "-complementar";
            Thread thread = new Thread(new ProcessadorFitas(entrada, saida));
            thread.start();  
        }
    }
}
