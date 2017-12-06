package model;

import java.util.Collection;
import java.util.Random;
import java.util.LinkedList;

import main.Config;
import model.Cliente;

public class Fila
{
	private float taxa; 
	private Random geradorAleatorio; 
	protected Collection<Cliente> clientes; 
	protected int numeroTotalDeChegadas;
	
	// Cria uma fila
	public Fila() 
	{
		numeroTotalDeChegadas = 0;
		clientes = new LinkedList<Cliente>();
	}

	// Cria uma fila com uma semente e uma taxa
	public Fila(long semente,float taxa) 
	{
		// Iniciando a semente aleatoria
		geradorAleatorio = new Random(semente);

		this.taxa = taxa;

		numeroTotalDeChegadas = 0;

		clientes = new LinkedList<Cliente>();
	}
	
	// Calcula a proxima chegada
	public Double calculaProximaChegada()
	{
		// x = -ln(y)/LAMBDA
		return (double) - Math.log(geradorAleatorio.nextFloat()) / taxa;
	}
	
	// Verifica se a fila está vazia
	public boolean isEmpty()
	{
		return clientes.isEmpty();
	}
	
	// Cálcula o tamanho da fila
	public int size()
	{
		return clientes.size();
	}
	
	// Calcula o numero total de chegada 
	public int getNumeroTotalDeChegadas() 
	{
		return numeroTotalDeChegadas;
	}
	
	// Retorna a taxa da fila
	public float getTaxa() 
	{
		return taxa;
	}
	
	// Encaminha cliente para o atendimento
	public Cliente removerParaAtendimento() 
	{
		// Remove da lista o fregues que sera atendido
		return ((LinkedList<Cliente>)clientes).removeFirst();
	}
	
	// Adiciona cliente a fila
	public void adicionaCliente(Cliente fregues) 
	{
		if(!fregues.getTipo().equals(Config.TIPO_TRANSIENTE))
			numeroTotalDeChegadas++;

		((LinkedList<Cliente>)clientes).addLast(fregues);
	}
	
	// Adiciona cliente no inicio da fila. Utilizado pela preempção 
	public void adicionaClienteInicio(Cliente fregues) 
	{
		((LinkedList<Cliente>)clientes).addFirst(fregues);
	}
}
