from queue import *
from client import *
from event import *
from utils import *


import numpy as np
import datetime

def main():

	# beta  = 1/lambda
	# beta = input("Informe beta:\n")
	# if(beta == None or beta == ""):
	# 	print("beta nao informado, definindo para 2\n")
	# 	beta = 20
	# beta = int(beta)


	# MARRETADO
	rho = 0.1
	arrivalRate = rho/2
	beta = 1/arrivalRate
	# trasientCount = input("Informe o numero de coletas da fase transiente:\n")
	# if(trasientCount == None or trasientCount == ""):
	# 	print("Numero de coletas da fase transiente nao informado, definindo para 10000\n")
	# 	trasientCount = 10000
	# trasientCount = int(trasientCount)

	# roundNum = input("Informe o numero de rodadas:\n")
	# if(roundNum == None or roundNum == ""):
	# 	print("Numero de rodadas nao informado, definindo para 5\n")
	# 	roundNum = 5
	# roundNum = int(roundNum)
		
	# roundSize = input("Informe o numero de coletas por rodada:\n")
	# if(roundSize == None or roundSize == ""):
	# 	print("Numero de coletas por rodada nao informado, definindo para 1000\n")
	# 	roundSize = 1000
	# roundSize = int(roundSize)


	# queue2 = FIFOQueue.FIFOQueue()

	# #Trasient fase
	# i = 0
	# x=[]
	# np.random.seed(40)
	# #Generating events transient fase
	# while (i < trasientCount):
	# 	x.append(np.random.exponential(beta))
	# 	i+=1
	
	events = Queue()
	

	print("======================================")
	print("Starting with rate: " + str(arrivalRate))
	print("======================================")
	#Starting simulation
	
	i=0
	j=0
	numArrivals = 0
	allServicesTime = []
	server = []

	newClient = Client(i, np.random.exponential(beta), ServiceType.ONE)
	events.push(Event(newClient, EventType.ARRIVAL))
	numArrivals += 1
	
	##TODO: Verificar se o tempo de um servico conta pro tempo de espera W de todo mundo

	while(i < 100000):
		numArrivals += 1
		if(events.length() > 0):
			nextEvent = events.pop()
			## PEGO O EVENTO DO COMECO DA FILA E SE FOR DO TIPO DE CHEGADA
			if(nextEvent.eventType == EventType.ARRIVAL):
				## VERIFICO SE O SERVIDOR JA NAO TEM ALGUEM OCUPADO
				if(len(server) == 0):
					nextEvent.EventType = EventType.SERVICE
					server.append(nextEvent.client)
					nextEvent.client.startService(nextEvent.client.serverId)
					events.push(nextEvent)
				else:
					# SE HOUVER ALGUEM, TEM QUE TESTAR SE EH DO TIPO 1 OU 2
					# se for trocar o tipo de evento e continuar
					print("PREEMPÇÃO")
			elif (nextEvent.eventType == EventType.SERVICE):
				# calcular o tempo de servico e adicionar ao cliente
				print(" SERVICE")
				## implementar servico
			else:
				print("INTERRUPTION")
				## tratar preempção
			
			## colocar um ARRIVAL aqui (acho)
		# COLETAR A INFORMACAO DO CLIENTE
		i+=1
	print("MEDIA W1 " + str(np.mean(allServicesTime)))
	W1_t = arrivalRate/(1-arrivalRate)
	print("MEDIA TEORICA W1 " + str(W1_t))
	
if __name__ == "__main__":
    main()