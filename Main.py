import FIFOQueue
from client import Client
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
	
	queue1 = FIFOQueue.FIFOQueue()
	

	print("======================================")
	print("Starting with rate: " + str(arrivalRate))
	print("======================================")
	#Starting simulation
	
	i=0
	j=0
	server = []
	numArrivals = 0
	allServicesTime = []
	while(i < 10000)	:
		newClient = Client(i, np.random.exponential(beta))
		queue1.push(newClient)
		numArrivals += 1
		if(len(server) == 0):
			nextClient = queue1.pop()
			nextClient.startService(nextClient.entryTime, 0)
			server.append(newClient)
			nextClient.finishService()
			server = []
			allServicesTime.append(nextClient.getTotalWaitTime())
		i+=1
	print("MEDIA W1 " + str(np.mean(allServicesTime)))
	W1_t = arrivalRate/(1-arrivalRate)
	print("MEDIA TEORICA W1 " + str(W1_t))
	
if __name__ == "__main__":
    main()