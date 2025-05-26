package processamento;

import modelo.Transacao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalisadorLinear {
	public static Map<String, Double> analisar(List<Transacao> transacoes) {
		Map<String, Double> totais = new HashMap<>();
		for (Transacao t : transacoes) {
			double valorReal = (t.getTipo() == Transacao.Tipo.ENTRADA) ? t.getValor() : -t.getValor();
			totais.merge(t.getCategoria(), valorReal, Double::sum);
		}
		return totais;
	}
}
