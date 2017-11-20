import FIFOQueue
import Event
import numpy as np
import datetime

def main():

	# beta  = 1/lambda
	beta = input("Informe beta:\n")
	if(beta == None or beta == ""):
		print("beta nao informado, definindo para 2\n")
		beta = 20
	beta = int(beta)

	trasientCount = input("Informe o numero de coletas da fase transiente:\n")
	if(trasientCount == None or trasientCount == ""):
		print("Numero de coletas da fase transiente nao informado, definindo para 10000\n")
		trasientCount = 10000
	trasientCount = int(trasientCount)

	roundCount = input("Informe o numero de rodadas:\n")
	if(roundCount == None or roundCount == ""):
		print("Numero de rodadas nao informado, definindo para 5\n")
		roundCount = 5
	roundCount = int(roundCount)
		
	collectCount = input("Informe o numero de coletas por rodada:\n")
	if(collectCount == None or collectCount == ""):
		print("Numero de coletas por rodada nao informado, definindo para 1000\n")
		collectCount = 1000
	collectCount = int(collectCount)

	queue1 = FIFOQueue.FIFOQueue();
	queue2 = FIFOQueue.FIFOQueue();

	#Trasient fase
	i = 0
	x=[]
	np.random.seed(40)
	#Generating events transient fase
	while (i < trasientCount):
		x.append(np.random.exponential(beta))
		i+=1
	i=0
	j=0
	#Starting simulation
	while(i < roundCount)	:
		while ( j < collectCount):
			queue1.push(Event.Event(np.random.exponential(beta), datetime.datetime.now(), i))
			j+=1
		i+=1

if __name__ == "__main__":
    main()