package model;

import java.util.Random;
import model.Cliente;

public class Servidor extends Thread
{
	private float taxa; 
	private Random geradorAleatorio; 
	private Cliente clienteSendoServido; 
	
	private float tempoTotalDeServico;
	private int numeroClientesServidosFila1;
	private int numeroClientesServidosFila2;
	
	// Instancia um novo servidor usando uma semente
	public Servidor(long semente, float taxaServidor)
	{
		geradorAleatorio = new Random(semente);
		this.taxa = taxaServidor;

		numeroClientesServidosFila1 = 0;
		numeroClientesServidosFila2 = 0;
		tempoTotalDeServico = 0.0f;
	}
	
	// Calcula o tempo de serviço
	public Double calculaTempoDeServico()
	{
		// x = -ln(y)/Lambda
		return (double)-Math.log(geradorAleatorio.nextFloat())/taxa;
		
		//return 1.0;
	}
	
	// Retorna tempo total de serviço
	public float getTempoTotalDeServico()
	{
		return this.tempoTotalDeServico;
	}
	
	// Retorna o número de clientes servidos
	public int getNumeroClientesServidos()
	{
		return numeroClientesServidosFila1+numeroClientesServidosFila2;
	}
	
	// Retorna o cliente que está sendo servido
	public Cliente getClienteSendoServido()
	{
		return clienteSendoServido;
	}
	
	// Define o cliente que está sendo serviço
	public void setClienteSendoServido(Cliente cliente)
	{
		this.clienteSendoServido = cliente;
	}
	
	// Atualiza o número de clientes servidos na fila 1 (+1)
	public void addNumeroClientesServidosFila1() 
	{
		this.numeroClientesServidosFila1++;
	}
	
	// Atualiza o número de clientes servidos na fila 2 (+1)
	public void addNumeroClientesServidosFila2() 
	{
		this.numeroClientesServidosFila2++;
	}
	
	// Retorna o número de clientes servidos na Fila 1
	public int getNumeroClientesServidosFila1() 
	{
		return numeroClientesServidosFila1;
	}
	
	// Retorna o número de clientes servidos na Fila 1
	public int getNumeroClientesServidosFila2() 
	{
		return numeroClientesServidosFila2;
	}
}
