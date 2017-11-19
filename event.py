class Event:
    W = [0,0]
    # SERVICO COM TAXA MU = 1s
    # T = W + X
    # T = W1+W2+ 2X 
    # W1 É SOH O TEMPO DA FILA 1 + RESIDUAL DA FILA 1
    # W2 É O TEMPO DA FILA 2 + FILA1 + RESIDUAL DA FILA 2 E FILA 1
    def __init__(self, id, entryTime):
        self.id = id
        self.entryTime = entryTime
   
    # TALVEZ SEJA MAIS FACIL DEIXAR SEPARADO POR SERVIÇO (startService2)
    def startService(self, startServiceTime, serverId):
        self.W[serverId] = startServiceTime - entryTime
        self.currentServer = serverId

    def finishService(self, finishServiceTime, serverId):
        # VERIFICAR SE ELE TA TERMINANDO O SERVIÇO QUE COMEÇOU DE FATO 
        if(self.currentServer == serverId):
            self.T = W
            # DO SHIT

        else:
            # DEU MERDA AQUI
            pass
