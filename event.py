class Event:
    W = [0,0]
    # SERVICO COM TAXA MU = 1s
    # T = W + X
    # T = W1+W2+ 2X 
    # W1 É SOH O TEMPO DA FILA 1 + RESIDUAL DA FILA 1
    # W2 É O TEMPO DA FILA 2 + FILA1 + RESIDUAL DA FILA 2 E FILA 1
    def __init__(self, _id, _entryTime, _roundID):
        self.id = _id
        self.entryTime = _entryTime
        self.roundID = _roundID

    # TALVEZ SEJA MAIS FACIL DEIXAR SEPARADO POR SERVIÇO (startService2)
    def startService(self, _startServiceTime, _serverId):
        self.W[serverId] = _startServiceTime - self.entryTime
        self.currentServer = _serverId

    def finishService(self, _finishServiceTime):
        self.endTime = _finishServiceTime
