package atividade4;

import java.io.File;


public class BuscaNomesMain {

    public static void main(String[] args) {

        String caminhoPasta = "G:\\Meu Drive\\Faculdade\\7ª fase\\Programação Alto Desempenho\\Atividade04\\arquivosNomes";
        String termoBusca = "gl";
       

        File pasta = new File(caminhoPasta);
        

        File[] arquivos = pasta.listFiles((dir, name) ->
            name.startsWith("nomescompletos") && name.endsWith(".txt")
        );

        if (arquivos == null || arquivos.length == 0) {
            System.out.println("Nenhum arquivo encontrado.");
            return;
        }

        long inicio = System.currentTimeMillis();

        GerenciadorBusca gerenciador = new GerenciadorBusca(arquivos, termoBusca);
        gerenciador.iniciarBusca();

        long fim = System.currentTimeMillis();
        System.out.println("Tempo total: " + (fim - inicio) + " ms");
    }
}
