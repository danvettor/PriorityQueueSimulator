package model;

// Classe que armazena o Resultado de cada Rodada
public class Resultado 
{
	private double tamanhoFila1;
	private double tamanhoFila2;
	private double tempoTotalFila1;
	private double tempoTotalFila2;
	private double tamanhoTotalFila1;
	private double tamanhoTotalFila2;
	private double tempoFila1;
	private double tempoFila2;
	private double varianciaTempoFila1;
	private double varianciaTempoFila2;
	
	// Instancia a Classe Resultado
	public Resultado(double tamanhoFila1,
					  double tamanhoFila2,
					  double tempoTotalFila1,
					  double tempoTotalFila2,
					  double tamanhoTotalFila1,
					  double tamanhoTotalFila2,
					  double tempoFila1,
					  double tempoFila2,
					  double varianciaTempoFila1,
					  double varianciaTempoFila2) 
	{ 
		super();
		this.tamanhoFila1 = tamanhoFila1;
		this.tamanhoFila2 = tamanhoFila2;
		this.tempoTotalFila1 = tempoTotalFila1;
		this.tempoTotalFila2 = tempoTotalFila2;
		this.tamanhoTotalFila1 = tamanhoTotalFila1;
		this.tamanhoTotalFila2 = tamanhoTotalFila2;
		this.tempoFila1 = tempoFila1;
		this.tempoFila2 = tempoFila2;
		this.varianciaTempoFila1 = varianciaTempoFila1;
		this.varianciaTempoFila2 = varianciaTempoFila2;
	}
	
	// Retorna o tamanho da Fila 1
	public double getTamanhoFila1() 
	{
		return tamanhoFila1;
	}
	
	// Define o tamanho da Fila 1, usando tamanhoFila1
	public void setTamanhoFila1(double tamanhoFila1) 
	{
		this.tamanhoFila1 = tamanhoFila1;
	}

	// Retorna o tamanho da Fila 2 
	public double getTamanhoFila2() 
	{
		return tamanhoFila2;
	}

	// Define o tamanho da Fila 2, usando tamanhoFila2
	public void setTamanhoFila2(double tamanhoFila2) 
	{
		this.tamanhoFila2 = tamanhoFila2;
	}
	
	// Retorna Tempo Total da Fila 1
	public double getTempoTotalFila1() 
	{
		return tempoTotalFila1;
	}
	
	// Define Tempo Total da Fila 1, usando tempoTotalFila1
	public void setTempoTotalFila1(double tempoTotalFila1) 
	{
		this.tempoTotalFila1 = tempoTotalFila1;
	}
	
	// Retorna Tempo Total da Fila 2
	public double getTempoTotalFila2() 
	{
		return tempoTotalFila2;
	}
	
	// Define o tempo total da Fila 2 usando tempoTotalFila2
	public void setTempoTotalFila2(double tempoTotalFila2) 
	{
		this.tempoTotalFila2 = tempoTotalFila2;
	}

	// Retorna tamanho total da Fila 1
	public double getTamanhoTotalFila1() 
	{
		return tamanhoTotalFila1;
	}
	
	// Define tamanho total da fila 1, usando tamanhoTotalFila1
	public void setTamanhoTotalFila1(double tamanhoTotalFila1) 
	{
		this.tamanhoTotalFila1 = tamanhoTotalFila1;
	}
	
	// Retorna tamanho total da Fila 2
	public double getTamanhoTotalFila2() 
	{
		return tamanhoTotalFila2;
	}
	
	// Define tamanho total da Fila 2, usando tamanhoTotalFila2 
	public void setTamanhoTotalFila2(double tamanhoTotalFila2) 
	{
		this.tamanhoTotalFila2 = tamanhoTotalFila2;
	}
	
	// Retorna Tempo Fila 1
	public double getTempoFila1() 
	{
		return tempoFila1;
	}

	// Define tempo Fila 1
	public void setTempoFila1(double tempoFila1) 
	{
		this.tempoFila1 = tempoFila1;
	}	
	
	// Retorna tempo fila 2
	public double getTempoFila2() 
	{
		return tempoFila2;
	}
	
	// Define tempo Fila 2, usando tempoFila2 
	public void setTempoFila2(double tempoFila2) 
	{
		this.tempoFila2 = tempoFila2;
	}
	
	// Retorna a Variância do Tempo da Fila 1
	public double getVarianciaTempoFila1() 
	{
		return varianciaTempoFila1;
	}
	
	// Define a Variância do Tempo da Fila 1, usando varianciaTempoFila1
	public void setVarianciaTempoFila1(double varianciaTempoFila1) 
	{
		this.varianciaTempoFila1 = varianciaTempoFila1;
	}

	// Retorna a Variância do Tempo da Fila 2	
	public double getVarianciaTempoFila2() 
	{
		return varianciaTempoFila2;
	}
	
	// Define a Variância do Tempo da Fila 2, usando varianciaTempoFila2
	public void setVarianciaTempoFila2(double varianciaTempoFila2) 
	{
		this.varianciaTempoFila2 = varianciaTempoFila2;
	}
}
