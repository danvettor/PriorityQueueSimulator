package controller;

import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Collection;


// Classe que irá lidar com todos os Eventos no Simulador
public class Eventos 
{
	private static Eventos instance;

	// Ele utiliza uma árvore rubro-negra para
	// fazer a ordenação na lista de Eventos
	private Eventos()
	{
		eventos = new TreeMap<Double,String>();
	}

	// Construtor Singleton
	public static Eventos getInstance()
	{
		if(instance==null)
			instance = new Eventos();

		return instance;
	}

	private SortedMap<Double, String> eventos;

	// Adiciona os eventos à Lista
	public void addEvento(Double tempo, String tipo)
	{
		if(!eventos.containsKey(tempo))
			eventos.put(tempo, tipo);
		else
		{
			// Tratando a possibilidade de ocorrerem
			// dois eventos
			String evento = eventos.get(tempo);

			// Concatenando os dois Eventos
			evento += "-" + tipo;
			removeEvento(tempo);
			eventos.put(tempo, tipo);
		}
	}

	public void removeEvento(Double tempo)
	{
		eventos.remove(tempo);
	}

	public Double getTempoProximoEvento()
	{
		return eventos.firstKey();
	}

	public String removeProximoEvento()
	{
		String tipoEvento = eventos.get(getTempoProximoEvento());
		removeEvento(getTempoProximoEvento());
		return tipoEvento;
	}

	public String removeProximoEvento(String tipo)
	{
		String tipoEvento = eventos.get(getTempoProximoEvento());
		removeEvento(getTempoProximoEvento());
		return tipoEvento;
	}
	
	public void removeTodosEventos()
	{
		this.eventos = new TreeMap<Double , String>();
	}
	
	public void imprimeListaEventos()
	{
		Collection<String> c = eventos.values();
		
		Iterator<String> itr = c.iterator();
		
		System.out.println("-------------");
		while(itr.hasNext())		
			System.out.println(itr.next());

	}
	
	public int count() {
		return this.eventos.size();
	}
	
	
}
