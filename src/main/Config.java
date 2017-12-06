package main;

public class Config 
{	
	// Variável que define o tamanho da rodada (número de clientes)
	public static final int TAMANHO_RODADA = 1794;
	
	// Variável que define o número de rodadas
	public static final int NUMERO_RODADAS = 988;
	
	// Variável que define o tamanho (número de clientes da fase transiente)
	public static final int TAMANHO_FASE_TRANSIENTE = 20000;
		
	// String relacionada aos clientes da fase transiente
	public static final String TIPO_TRANSIENTE = "transiente";
	
	// Variável que ativa/desativa os output dos testes de correção do simulador
	public static final boolean TESTE_DE_CORRECAO = false;
	
	// Variável que ativa/desativa os outputs para  as estimativas da fase transiente
	public static final boolean ESTIMAR_FASE_TRANSIENTE = false;
	
	// Método que calcula o fator 
	public static int fator() 
	{
		return TAMANHO_RODADA * NUMERO_RODADAS + TAMANHO_FASE_TRANSIENTE;
	}
}
