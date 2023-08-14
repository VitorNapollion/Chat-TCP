# Chat-TCP

Usei threads para lidar com múltiplos clientes em uma instância separada da classe ClientHandler assim o cliente é tratado separadamente da classe clientHandler que implementa a Interface Runnable para ser executada em uma Thread. E modificando a classe SimpleTCPServer para que o servidor possua uma lista de ClientHandler para rastrear os clientes conectados onde Também possui um método ForwardMessage() para encaminhar mensagens de um cliente para todos os outros clientes. Assim o servidor encaminha a mensagem de um cliente para os outros permitindo que se comuniquem entre si.
