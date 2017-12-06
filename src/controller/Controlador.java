package controller;

import java.util.ArrayList;
import java.util.Collection;

import main.Config;
import model.Fila;
import model.Cliente;
import model.Resultado;
import model.Servidor;

public class Controlador {

	//Constante que define que o evento ocorrido eh uma chegada
	private static final String CHEGADA ="chegada";

	//Constante que define que o evento ocorrido eh o fim de um servico
	private static final String SERVICO ="servico";

	//Filas do sistema
	private Fila fila1,fila2;

	//Servidor
	private Servidor servidor;

	// Classe construtora do Controlador, setando as variaveis iniciais
	public Controlador(long sementeTemp , float taxaTemp, float taxaServidor)
	{
		//System.out.println("Semente servidor : " + semente);
		long semente = sementeTemp * 100 + System.currentTimeMillis() % 40;

		servidor = new Servidor(semente,taxaServidor);
		
		fila1 = new Fila(sementeTemp, taxaTemp);

		/*
		 * Importante ressaltar que a Fila 2 nao tem que gerar chegadas por
		 * isso o construtor eh diferente
		 */
		fila2 = new Fila();
	}

	// Executa uma rodada, retornando um Resultado
	public Resultado executarRodada()
	{		
		// Nessa variáveis que armazenaremos o somatório
		// dos tempos para calcular a Média da Rodada
		double somatorioTempoEsperaFila1 = 0.0d,
			   	somatorioTempoEsperaFila2 = 0.0d,
				somatorioTempoServico1 = 0.0d,
				somatorioTempoServico2 = 0.0d,
				somatorioVariancia1 = 0.0d,
				somatorioVariancia2 = 0.0d;
		
		// Armazena cada um dos tempos de cada cliente 
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


		// Indicam o tempo que esta sendo simulado, 
		// e o momento em que acabaram de ocorrer chegadas da fase transiente
		double tempoAtual = 0.0d,
				tempoUltimaChegadaTransiente = 0.0d;

		// Indica o numero de chegadas que ocorreram, 
		// incluindo os transientes, eh utilizado para saber se a fase transiente acabou
		int numeroChegadas = 0;

		// Caculando quando ocorrera a primeira chegada
		double chegadaFila1 = fila1.calculaProximaChegada();

		Eventos.getInstance().addEvento(chegadaFila1, CHEGADA);

		// A partir daqui, o programa fica lendo sempre a lista de eventos e servindo 
		// o próximo cliente, dependendo da situação atual do sistema
		while(fila1.getNumeroTotalDeChegadas() < Config.TAMANHO_RODADA)
		{			
			try{
				tempoAtual = Eventos.getInstance().getTempoProximoEvento();
			}catch(Exception e) {
				//System.out.println(e);
			}
			
			// Remove o proximo evento da fila, retornando o seu tipo
			String tipoEvento = Eventos.getInstance().removeProximoEvento();

			// Trata eventos do tipo 'Chegada'
			if(tipoEvento.contains(CHEGADA))
			{
				// Se a chegada estiver dentro do intervalo da Fase Transiente, 
				// adiciono a fila um cliente do tipo transiente
				// Caso contrario, adiciono um client e normal
				if(numeroChegadas < Config.TAMANHO_FASE_TRANSIENTE)
					fila1.adicionaCliente(new Cliente(tempoAtual,Config.TIPO_TRANSIENTE));
				else
				{
					if(numeroChegadas == Config.TAMANHO_FASE_TRANSIENTE)
						tempoUltimaChegadaTransiente = tempoAtual;

					fila1.adicionaCliente(new Cliente(tempoAtual,"naoTransiente"));
				}

				// Trato a nova chegada na fila
				chegadaFila1 = fila1.calculaProximaChegada();
				numeroChegadas++;

				Eventos.getInstance().addEvento(tempoAtual + chegadaFila1, CHEGADA);
				
				if (Config.TESTE_DE_CORRECAO)
					System.out.println(">> Numero de Chegadas:" + numeroChegadas);
								
				// Se nenhum cliente estiver sendo servido...
				if(servidor.getClienteSendoServido() == null)
				{
					Cliente cliente = fila1.removerParaAtendimento();
										
					if (cliente == null) 
						System.out.println("Fila 1 está vazia.");					
					
					// Seto o W1 do cliente corrente
					cliente.setTempoSaidaFila1(tempoAtual);

					// Ocorreu uma chegada e ninguem esta sendo servido,
					// eu ja encaminho para o servidor
					servidor.setClienteSendoServido(cliente);

					double tempoServico = servidor.calculaTempoDeServico();

					Eventos.getInstance().addEvento(tempoAtual + tempoServico, SERVICO);
					
					if (Config.TESTE_DE_CORRECAO) 
					{
						System.out.println("\nEvento: Servidor Vazio");
						System.out.println("Tipo Evento:" + tipoEvento);
						System.out.println("Cliente a ser Servido:" + cliente);
						System.out.println("Fila de Origem:" + cliente.getFilaDeOrigem());
						System.out.println("Tempo: " + tempoAtual);
					}					
				}
				else
				{
					Cliente cliente_sendo_servido = servidor.getClienteSendoServido();
					
					if(cliente_sendo_servido.getFilaDeOrigem().equalsIgnoreCase("fila2"))
					{
						Cliente cliente_interrompido = servidor.getClienteSendoServido();
								
						double tempoServido = tempoAtual - cliente_interrompido.getTempoSaidaFila2();
						double tempoDeServicoResidual = cliente_sendo_servido.getInterrompido() ?
								cliente_interrompido.getTempoResidual() : cliente_interrompido.getTempoServico2();
						
						cliente_interrompido.setTempoResidual(tempoDeServicoResidual - tempoServido);
						
						Eventos.getInstance().removeEvento(tempoAtual + cliente_interrompido.getTempoResidual());
						
						cliente_interrompido.setInterrompido(true);
						
						servidor.setClienteSendoServido(null);

						fila2.adicionaClienteInicio(cliente_interrompido);
						
						// Volto a servir o cliente normalmente
						Cliente cliente = fila1.removerParaAtendimento();
						
						// Seto o W1 do cliente corrente
						cliente.setTempoSaidaFila1(tempoAtual);

						// Ocorreu uma chegada e ninguem esta sendo servido,
						// eu ja encaminho para o servidor
						servidor.setClienteSendoServido(cliente);

						double tempoServico = servidor.calculaTempoDeServico();

						Eventos.getInstance().addEvento(tempoAtual + tempoServico, SERVICO);
						
						if (Config.TESTE_DE_CORRECAO) 
						{
							System.out.println("\nEvento: PREEMPÇÃO");
							System.out.println("Tipo Evento:" + tipoEvento);
							System.out.println("Cliente Interrompido:" + cliente_interrompido);
							System.out.println("Fila de Origem:" + cliente_interrompido.getFilaDeOrigem());							
							System.out.println("Cliente a ser Servido:" + cliente);
							System.out.println("Fila de Origem:" + cliente.getFilaDeOrigem());
							System.out.println("Tempo: " + tempoAtual);
						}						
					}
				}
			}
			// Trata eventos do tipo 'Servico'
			else if(tipoEvento.contains(SERVICO))
			{
				Cliente cliente_sendo_servido = servidor.getClienteSendoServido();
				
				if(Config.TESTE_DE_CORRECAO) 
				{
					System.out.println("\nEvento: Cliente sendo Servido");
					System.out.println("Tipo Evento:" + tipoEvento);
					System.out.println("Cliente sendo Servido:" + cliente_sendo_servido);
					System.out.println("Fila de Origem:" + cliente_sendo_servido.getFilaDeOrigem());
					System.out.println("Tempo: " + tempoAtual);
				}
				
				// Se o cliente estiver saindo da Fila 1
				if(cliente_sendo_servido.getFilaDeOrigem().equalsIgnoreCase("fila1"))
				{
					cliente_sendo_servido.setTempoChegadaFila2(tempoAtual);

					// Contabilizando apenas clientes que não são transientes
					if(!cliente_sendo_servido.getTipo().equals(Config.TIPO_TRANSIENTE))
						servidor.addNumeroClientesServidosFila1();

					// Se o cliente que terminou o servico veio da fila 1 ele vai pra fila 2
					fila2.adicionaCliente(cliente_sendo_servido);
				}
				// Se o cliente estiver saindo da Fila 2
				// É aqui que seus respectivos tempos são finalmente armazenados
				else
				{
					cliente_sendo_servido.setTempoSaida(tempoAtual);
					
					//Contabilizando apenas clientes que não são transientes
					if(!cliente_sendo_servido.getTipo().equals(Config.TIPO_TRANSIENTE))
					{						
						servidor.addNumeroClientesServidosFila2();
						
						double tempoEsperaFila1 = cliente_sendo_servido.getTempoSaidaFila1() -
						cliente_sendo_servido.getTempoChegadaFila1();
						
						double variancia1 = tempoEsperaFila1 * tempoEsperaFila1;
						
						double tempoServico1 = cliente_sendo_servido.getTempoChegadaFila2() - 
						cliente_sendo_servido.getTempoSaidaFila1();
						
						double tempoTotal1 = tempoEsperaFila1 + tempoServico1;
						
						somatorioTempoEsperaFila1 += tempoEsperaFila1;
						somatorioVariancia1 += variancia1;
						somatorioTempoServico1 += tempoServico1;
						
						double tempoEsperaFila2 = (cliente_sendo_servido.getTempoSaida() - 
													cliente_sendo_servido.getTempoChegadaFila2() -
													cliente_sendo_servido.getTempoServico2());

						double variancia2 = tempoEsperaFila2 * tempoEsperaFila2;
						
						double tempoServico2 = cliente_sendo_servido.getTempoServico2();
						
						double tempoTotal2 = tempoEsperaFila2 + tempoServico2;
						
						somatorioTempoEsperaFila2 += tempoEsperaFila2;											  
						somatorioVariancia2 += variancia2;
						somatorioTempoServico2 += tempoServico2;	
						
						// Utilizaremos os dados seguintes para obter o cálculo da Fase Transiente
						// Calculando a média acumulada de cada um das Collections
						// gerando a média acumulada e plotando seus respectivos gráficos
						if (Config.ESTIMAR_FASE_TRANSIENTE) 
						{
							double tamanhoFila1	  = fila1.getNumeroTotalDeChegadas() / 
							(tempoAtual - tempoUltimaChegadaTransiente) * tempoEsperaFila1;

							double tamanhoFila2	  = fila2.getNumeroTotalDeChegadas() /
													(tempoAtual - tempoUltimaChegadaTransiente) * tempoEsperaFila2;
							
							double tamanhoTotalFila1 = fila1.getNumeroTotalDeChegadas() / 
														(tempoAtual - tempoUltimaChegadaTransiente) * (tempoTotal1);
							
							double tamanhoTotalFila2 = fila2.getNumeroTotalDeChegadas() /
														(tempoAtual - tempoUltimaChegadaTransiente) * (tempoTotal2);
							
							w1.add(tempoEsperaFila1);
							w2.add(tempoEsperaFila2);
							t1.add(tempoEsperaFila1 + tempoServico1);
							t2.add(tempoEsperaFila2 + tempoServico2);
							nq1.add(tamanhoFila1);
							nq2.add(tamanhoFila2);
							n1.add(tamanhoTotalFila1);
							n2.add(tamanhoTotalFila2);
							variancias1.add(variancia1);
							variancias2.add(variancia2);	
						}
						
					}

					servidor.setClienteSendoServido(null);
				}

				// Se a Fila1 tiver clientes, atendo eles primeiramente
				// pois possuem maior prioridade
				if(!fila1.isEmpty())
				{
					Cliente cliente = fila1.removerParaAtendimento();
					cliente.setTempoSaidaFila1(tempoAtual);

					//Se tiver alguem da fila 1 para ser atendido, ele possui prioridade
					servidor.setClienteSendoServido(cliente);

					cliente.setFilaDeOrigem("fila1");

					double tempoServico = servidor.calculaTempoDeServico();

					Eventos.getInstance().addEvento(tempoAtual + tempoServico, SERVICO);
					
					if(Config.TESTE_DE_CORRECAO) 
					{
						System.out.println("\nEvento: Fila1 com Clientes.");
						System.out.println("Tipo Evento:" + tipoEvento);
						System.out.println("Cliente Servido:" + cliente_sendo_servido);
						System.out.println("Fila de Origem:" + cliente_sendo_servido.getFilaDeOrigem());
						System.out.println("Cliente a ser Servido:" + cliente);
						System.out.println("Fila de Origem:" + cliente.getFilaDeOrigem());
						System.out.println("Tempo: " + tempoAtual);
					}
				}

				// Se a Fila1 estiver vazia e ainda haver clientes na Fila2,
				// comeco a atende-los
				else if(!fila2.isEmpty())
				{
					Cliente cliente = fila2.removerParaAtendimento();
					
					double tempoServico;
					
					if (!cliente.getInterrompido())
					{
						cliente.setTempoServico2(servidor.calculaTempoDeServico());
						tempoServico = tempoAtual + cliente.getTempoServico2();
					
						if (Config.TESTE_DE_CORRECAO)
							System.out.println("\nEvento: Fila1 vazia com Cliente2 Normal.");	
					}						
					else
					{
						tempoServico = tempoAtual + cliente.getTempoResidual();
						
						if (Config.TESTE_DE_CORRECAO)
							System.out.println("\nEvento: Fila1 vazia com Cliente2 Residual.");
					}
					
					cliente.setTempoSaidaFila2(tempoAtual);					
					servidor.setClienteSendoServido(cliente);
					cliente.setFilaDeOrigem("fila2");

					Eventos.getInstance().addEvento(tempoServico, SERVICO);
						
					if(Config.TESTE_DE_CORRECAO) 
					{						
						System.out.println("Tipo Evento:" + tipoEvento);
						System.out.println("Cliente a ser Servido:" + cliente);
						System.out.println("Fila de Origem:" + cliente.getFilaDeOrigem());
						System.out.println("Tempo: " + tempoAtual);
					}					
				}
				else
				{
					// Alguma coisa deveria acontecer aqui?
					// Em teoria, nada deveria acontecer aqui
					// Apenas espera a proxima chegada na Fila
					if(Config.TESTE_DE_CORRECAO)
						System.out.println("\nEvento: As duas Filas estão vazias.\n");
				}

			} // -> Fim do tratamento do evento do tipo "Servico"
			
		} // -> Fim do While que loopa a Fila de Eventos
		
		// Finalmente calcula os tempos e tamanhos médios
		// de cada uma das variáveis do Sistema

		double tempoEsperaFila1 = somatorioTempoEsperaFila1 / servidor.getNumeroClientesServidosFila1(),
				tempoEsperaFila2 = somatorioTempoEsperaFila2 / servidor.getNumeroClientesServidosFila2();

    	double tempoServico1	= somatorioTempoServico1 / servidor.getNumeroClientesServidosFila1(),
				tempoServico2	= somatorioTempoServico2 / servidor.getNumeroClientesServidosFila2();

		double tempoTotalFila1	= tempoEsperaFila1 + tempoServico1,
				tempoTotalFila2	= tempoEsperaFila2 + tempoServico2;

		double tamanhoMedioFila1	  = fila1.getNumeroTotalDeChegadas() / 
										(tempoAtual - tempoUltimaChegadaTransiente) * tempoEsperaFila1;

		double tamanhoMedioFila2	  = fila2.getNumeroTotalDeChegadas() /
										(tempoAtual - tempoUltimaChegadaTransiente) * tempoEsperaFila2;

		double tamanhoMedioTotalFila1 = fila1.getNumeroTotalDeChegadas() / 
										(tempoAtual - tempoUltimaChegadaTransiente) * tempoTotalFila1;

		double tamanhoMedioTotalFila2 = fila2.getNumeroTotalDeChegadas() /
										(tempoAtual - tempoUltimaChegadaTransiente) * tempoTotalFila2;

		double varianciaFila1 = (somatorioVariancia1 - tempoEsperaFila1 * tempoEsperaFila1 * Config.TAMANHO_RODADA) / 
								(Config.TAMANHO_RODADA-1);

		double varianciaFila2 = (somatorioVariancia2 - tempoEsperaFila2 * tempoEsperaFila2 * Config.TAMANHO_RODADA) /
								(Config.TAMANHO_RODADA-1);
		
		if (Config.ESTIMAR_FASE_TRANSIENTE)
		{
//			System.out.println("#>> Médias Acumuladas da Fase Transiente:");
//			
//			System.out.println("#N1:");
//			imprimeMediaAcumulada(n1);
//
//			System.out.println("#N2:");
//			imprimeMediaAcumulada(n2);
//			
//			System.out.println("#T1:");
//			imprimeMediaAcumulada(t1);
//			
//			System.out.println("#T2:");
//			imprimeMediaAcumulada(t2);
//			
//			System.out.println("#Nq1:");
//			imprimeMediaAcumulada(nq1);
//			
//			System.out.println("#Nq2:");
//			imprimeMediaAcumulada(nq2);
//			
//			System.out.println("#W1:");
//			imprimeMediaAcumulada(w1);
//			
//			System.out.println("#W2:");
//			imprimeMediaAcumulada(w2);
//			
//			System.out.println("#Var(W1):");
//			imprimeMediaAcumulada(variancias1);
//			
			System.out.println("#Var(W2):");
			imprimeMediaAcumulada(variancias2);
		}
		
		return new Resultado(tamanhoMedioFila1,
							 tamanhoMedioFila2,
							 tempoTotalFila1,
							 tempoTotalFila2,
							 tamanhoMedioTotalFila1,
							 tamanhoMedioTotalFila2,
							 tempoEsperaFila1,
							 tempoEsperaFila2,
							 varianciaFila1,
							 varianciaFila2
							);
	}
	
	private static void imprimeMediaAcumulada(Collection<Double> media)
	{
		Double acumulado = 0.0d;
		int contador = 0;		
		
		System.out.println("# de Clientes: " + media.size());
		
		for(Double i : media)
		{
			contador++;
			acumulado += i;
			
			System.out.println((Double)(acumulado / contador));
		}
	}
}