package main;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.math3.distribution.ChiSquaredDistribution;
import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.TDistribution;

import model.Resultado;
import util.EstatisticaTeorica;
import controller.Controlador;
import controller.Eventos;

public class Main 
{
	public static void main(String[] args) 
	{		
		rodadaTaxaChegada(0.1f);
		rodadaTaxaChegada(0.2f);
		rodadaTaxaChegada(0.3f);
		rodadaTaxaChegada(0.4f);
		rodadaTaxaChegada(0.45f);
	}

	public static void rodadaTaxaChegada(float taxa_chegada)
	{
		System.out.println("Taxa = " + taxa_chegada + "; Fator: " + Config.fator() +"\n");
		
		Collection <Resultado> resultadosDasRodadas = new ArrayList<Resultado>();

		for(int i = 0 ; i < Config.NUMERO_RODADAS ; i++)
		{
			// Multiplicamos por um numero suficientemente grande
			// pra garantir que a semente não se repita entre
			// diferentes rodadas			
			long semente = (long)(Math.random() * 2000000);

			// Instancio o Controlador com a semente, o lambda passado por parametro
			// e a taxa de servico do servidor, que no caso eh um
			Controlador controlador = new Controlador(semente, taxa_chegada, 1.0f);

			try 
			{
				// Tento executar a rodada, caso contrario, volto
				// e tento novamente
				Resultado resultado = controlador.executarRodada();
				
				// Adiciono o resultado da Rodada em um Array de Resultados
				resultadosDasRodadas.add(resultado);	
			} 
			catch (Exception e) 
			{
				i--;
			}
		
			// Limpa o Singleton que Gerencia os Eventos para
			// rodarmos uma nova rodada limpa
			Eventos.getInstance().removeTodosEventos();
		}

		Collection<Double> 	w1 = new ArrayList<Double>(),
							w2 = new ArrayList<Double>(),
							t1 = new ArrayList<Double>(),
							t2 = new ArrayList<Double>(),
							nq1 = new ArrayList<Double>(),
							nq2 = new ArrayList<Double>(),
							n1 = new ArrayList<Double>(),
							n2 = new ArrayList<Double>(),
							variancias1 = new ArrayList<Double>(),
							variancias2 = new ArrayList<Double>();

		// Percorro toda a lista que contem os resultados
		// e os separo nos tempos de suas respectivas variáveis
		for(Resultado res : resultadosDasRodadas)
		{
			w1.add(res.getTempoFila1());
			w2.add(res.getTempoFila2());
			t1.add(res.getTempoTotalFila1());
			t2.add(res.getTempoTotalFila2());
			nq1.add(res.getTamanhoFila1());
			nq2.add(res.getTamanhoFila2());
			n1.add(res.getTamanhoTotalFila1());
			n2.add(res.getTamanhoTotalFila2());
			variancias1.add(res.getVarianciaTempoFila1());
			variancias2.add(res.getVarianciaTempoFila2());
		}

		EstatisticaTeorica.setTaxaChegada(taxa_chegada);
		
		System.out.println("N1: ");
		System.out.println("Teórico: " + EstatisticaTeorica.n1_t());				
		//intervaloDeConfianca(n1,"n1");
		System.out.println("");
		
		System.out.println("N2: ");
		System.out.println("Teórico: " + EstatisticaTeorica.n2_t());
		//intervaloDeConfianca(n2,"n2");
		System.out.println("");

		System.out.println("T1: ");
		System.out.println("Teórico: " + EstatisticaTeorica.t1_t());
		//intervaloDeConfianca(t1,"t1");
		System.out.println("");

		System.out.println("T2: ");
		System.out.println("Teórico: " + EstatisticaTeorica.t2_t());
		//intervaloDeConfianca(t2,"t1");		
		System.out.println("");

		System.out.println("Nq1: ");
		System.out.println("Teórico: " + EstatisticaTeorica.nq1_t());
		//intervaloDeConfianca(nq1,"nq1");
		System.out.println("");

		System.out.println("Nq2: ");
		System.out.println("Teórico: " + EstatisticaTeorica.nq2_t());
		//intervaloDeConfianca(nq2,"nq2");
		System.out.println("");

		System.out.println("W1: ");
		System.out.println("Teórico: " + EstatisticaTeorica.w1_t());
		intervaloDeConfianca(w1,"w1");
		System.out.println("");

		System.out.println("W2: ");
		System.out.println("Teórico: " + EstatisticaTeorica.w2_t());
		intervaloDeConfianca(w2,"w2");
		System.out.println("");

		System.out.println("Var(W1): ");
		System.out.println("Teórico: " + EstatisticaTeorica.var_w1_t());
		//intervaloDeConfianca(variancias1,"varw1");
		System.out.println("");

		System.out.println("Var(W2): ");
		//intervaloDeConfianca(variancias2,"varw2");
		System.out.println("");
	}
	
	// Calcula o intervalo de Confiança das Médias
	private static void intervaloDeConfianca(Collection<Double> medias, String variavel)
	{
		double mediaDasMedias = 0.0d,
				somatorioVariancia = 0.0d,
				desvioPadrao = 0.0d,
				minimoIntervalo = 0.0d,
				maximoIntervalo = 0.0d,
				novaMedia = 0.0;
		
		// Tentativa de arrendondar os valores pra 6 casas		
		novaMedia = medias.size();
		
		for(Double media : medias)
		{
			if(media.isNaN() || media <= 0.0){
				novaMedia--;
			}else{
				mediaDasMedias += media / novaMedia;
				somatorioVariancia += media * media;
			}
		}
		
		System.out.println("Media: " + mediaDasMedias);
		
		
		if(Config.NUMERO_RODADAS != 1)
		{
			desvioPadrao = Math.sqrt(
									(somatorioVariancia - mediaDasMedias * mediaDasMedias * novaMedia) /
									(novaMedia - 1)
								);
		
			double intervalo = 1.96 * desvioPadrao / novaMedia;		
			
			minimoIntervalo = mediaDasMedias - intervalo;
			maximoIntervalo = mediaDasMedias + intervalo;			
			
			TDistribution student = new TDistribution(novaMedia - 1);
			
			double z = 1.0 - (1 - 0.95) / 2;
			
			double critVal = student.inverseCumulativeProbability(z);
	        // Calculate confidence interval
			double trust = critVal * desvioPadrao / Math.sqrt(novaMedia);
			
			
			NormalDistribution normal = new NormalDistribution();
			
			critVal = normal.inverseCumulativeProbability(z);
			
			double normalLo = mediaDasMedias - (critVal * (desvioPadrao/novaMedia));
			double normalHi = mediaDasMedias + (critVal * (desvioPadrao/novaMedia));
			

			ChiSquaredDistribution chisquare = new ChiSquaredDistribution(novaMedia - 1);
			
			critVal = chisquare.inverseCumulativeProbability(z);
			double chisquareLo = ((novaMedia - 1) * desvioPadrao * desvioPadrao) / critVal;
			double chisquareHi = ((novaMedia - 1) * desvioPadrao * desvioPadrao) / chisquare.inverseCumulativeProbability(0.95 / 2);
			
			System.out.println("Intervalo de confiança Normal: " + (normalLo) + " < u < " + (normalHi) );
			System.out.printf("Intervalor de confiança ChiQuadrado: %.8f < dv^2 < %.8f\nVariância: %.8f\n", chisquareLo, chisquareHi, (desvioPadrao*desvioPadrao) );
			
			System.out.println("Desvio-Padrao: " + desvioPadrao);

			System.out.println("Minimo: " + (minimoIntervalo));
			System.out.println("Maximo: " + (maximoIntervalo));
			
			System.out.println("Porcentagem: " + (intervalo * 100 / mediaDasMedias) + "%");
			
			System.out.println("Limite Inferior no Intervalo de Confiança T-Student: " + (mediaDasMedias - trust) + "\nLimite Superior no Intervalo de Confiança T-Student: " + (mediaDasMedias + trust) + "");
		}
	}	
}
