package model;

public class Cliente 
{
	private String filaDeOrigem;
	
	//Diz se o cliente eh transiente, ou de qual rodada ele faz parte
	private String tipo;
	
	private double tempoChegadaFila1;
	private double tempoSaidaFila1;
	private double tempoChegadaFila2;
	private double tempoSaidaFila2;
	private double tempoSaida;
	private double tempoServico2;
	private boolean interrompido;
	private double tempoResidual;
	
	public Cliente(Double tempoChegadaNaFila, String tipo) 
	{
		this.filaDeOrigem = "fila1";
		this.tempoChegadaFila1  =  tempoChegadaNaFila;
		this.tipo = tipo;
		this.interrompido = false;

		tempoSaidaFila1 = 0.0;
		tempoChegadaFila2 = 0.0;
		tempoSaidaFila2 = 0.0;
		tempoSaida = 0.0;
		tempoServico2 = 0.0;
		tempoResidual = 0.0;		
	}

	public String getFilaDeOrigem() 
	{
		return filaDeOrigem;
	}
	
	public void setFilaDeOrigem(String filaDeOrigem) 
	{
		this.filaDeOrigem = filaDeOrigem;
	}

	public String getTipo() 
	{
		return tipo;
	}
	
	public double getTempoChegadaFila1() 
	{
		return tempoChegadaFila1;
	}

	public void setTempoChegadaFila1(double tempoChegadaFila1) 
	{
		this.tempoChegadaFila1 = tempoChegadaFila1;
	}

	public double getTempoSaidaFila1() 
	{
		return tempoSaidaFila1;
	}

	public void setTempoSaidaFila1(double tempoSaidaFila1) 
	{
		this.tempoSaidaFila1 = tempoSaidaFila1;
	}

	public double getTempoChegadaFila2() 
	{
		return tempoChegadaFila2;
	}

	public void setTempoChegadaFila2(double tempoChegadaFila2) 
	{
		this.tempoChegadaFila2 = tempoChegadaFila2;
	}

	public double getTempoSaidaFila2() 
	{
		return tempoSaidaFila2;
	}

	public void setTempoSaidaFila2(double tempoSaidaFila2) 
	{
		this.tempoSaidaFila2 = tempoSaidaFila2;
	}

	public double getTempoServico2()
	{
		return tempoServico2;
	}
	
	public void setTempoServico2(double tempoServico2)
	{
		this.tempoServico2 = tempoServico2;
	}

	public void adicionaTempoServico2(double tempoServico2) 
	{
		this.tempoServico2 += tempoServico2;
	}
		
	public double getTempoSaida() 
	{
		return tempoSaida;
	}

	public void setTempoSaida(double tempoSaida) 
	{
		this.tempoSaida = tempoSaida;
	}
	
	public boolean getInterrompido()
	{
		return interrompido;
	}
	
	public void setInterrompido(boolean foi)
	{
		this.interrompido = foi;
	}
	
	public double getTempoResidual() 
	{
		return tempoResidual;
	}

	public void setTempoResidual(double tempoResidual) 
	{
		this.tempoResidual = tempoResidual;
	}
}
