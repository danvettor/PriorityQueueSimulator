class Client:
   
    # SERVICO COM TAXA MU = 1s
    # T = W + X
    # T = W1+W2+ 2X 
    # W1 eh SOH O TEMPO DA FILA 1 + RESIDUAL DA FILA 1
    # W2 eh O TEMPO DA FILA 2 + FILA1 + RESIDUAL DA FILA 2 E FILA 1
    def __init__(self, _id, _entryTime):
        self.id = _id
        self.entryTime = _entryTime
        self.W = [0,0]
        # self.roundID = _roundID

    # TALVEZ SEJA MAIS FACIL DEIXAR SEPARADO POR SERVIcO (startService2)
    def startService(self, _startServiceTime, _serverId):
        # print("Starting client "+ str(self.id) + " service at server " + str(_serverId+1))
        self.W[_serverId] = _startServiceTime - self.entryTime
        self.currentServer = _serverId

    def finishService(self):
        # print("Finished client "+ str(self.id) + " service at server" + str(self.currentServer+1))
        self.T = self.W[self.currentServer] + 1

    def getTotalWaitTime(self):
        return self.T
