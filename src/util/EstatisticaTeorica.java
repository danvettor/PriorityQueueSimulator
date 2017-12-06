package util;

public class EstatisticaTeorica
{
	static double taxa_chegada;

	public static void setTaxaChegada(float taxa)
	{
		taxa_chegada = taxa;
	}

	public static double n1_t() 
	{
		return taxa_chegada / (1 - taxa_chegada);		
	}
	
	public static double nq1_t()
	{
		return taxa_chegada * taxa_chegada / (1 - taxa_chegada);
	}
	
	public static double w1_t()
	{
		return n1_t();
	}

	public static double t1_t() 
	{
		return 1 / (1 - taxa_chegada);
	}
	
	public static double var_w1_t() 
	{
		return taxa_chegada * (2 - taxa_chegada) / ((1 - taxa_chegada) * (1 - taxa_chegada));
	}

	public static double w2_t()
	{
        return (3 * taxa_chegada + nq1_t() + n1_t()) / (1 - 2 * taxa_chegada);
	}

	public static double t2_t() 
	{
		return w2_t() + 1;
	}

	public static double n2_t() 
	{
		return taxa_chegada * t2_t();
	}
	
	public static double nq2_t()
	{
		return taxa_chegada * w2_t();
	}	
}
