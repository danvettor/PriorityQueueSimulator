from utils import *
class Client:
   
    # SERVICO COM TAXA MU = 1s
    # T = W + X
    # T = W1+W2+ 2X 
    # W1 eh SOH O TEMPO DA FILA 1 + RESIDUAL DA FILA 1
    # W2 eh O TEMPO DA FILA 2 + FILA1 + RESIDUAL DA FILA 2 E FILA 1
    def __init__(self, id, entryTime, serverId):
        self.id = id
        self.entryTime = entryTime
        self.serverId = serverId
        self.W = [0,0]
        # self.roundID = _roundID

    # TALVEZ SEJA MAIS FACIL DEIXAR SEPARADO POR SERVIcO (startService2)
    def startService(self, serverId):
        # print("Starting client "+ str(self.id) + " service at server " + str(_serverId+1))
        self.W[serverId] =  self.entryTime

    def finishService(self):
        # print("Finished client "+ str(self.id) + " service at server" + str(self.currentServer+1))
        self.T = self.W[self.serverId] + 1

    def getTotalWaitTime(self):
        return self.T
