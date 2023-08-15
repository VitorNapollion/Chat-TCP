import java.io.*; 
import java.net.*; 
import java.util.concurrent.*;


 class ClientHandler extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private static final ConcurrentHashMap<Socket, String> clientMap = new ConcurrentHashMap<>();
    private ExecutorService threadPool;

    public ClientHandler(Socket socket, ExecutorService threadPool) {
        clientSocket = socket;
        this.threadPool = threadPool;
        try {
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println("Erro ao criar canais de comunicação: " + e.getMessage());
        }
    }

    public void run() {
        try {
            out.writeUTF("Digite seu nome:");
            String clientName = in.readUTF();
            clientMap.put(clientSocket, clientName);

            broadcast(">>>> " + clientName + " entrou no chat <<<<");
            
            String welcomeMessage = "Bem-vindo ao chat, " + clientName + "!";
            out.writeUTF(welcomeMessage);

            while (true) {
                String message = in.readUTF();
                if (message.equalsIgnoreCase("/sair")) {
                    break;
                }
                broadcast(clientName + ": " + message);
            }

            clientSocket.close();
            clientMap.remove(clientSocket);
            broadcast(clientName + " saiu do chat.");

        } catch (IOException e) {
            System.out.println("Erro na comunicação com o cliente: " + e.getMessage());
        }
    }

    private void broadcast(String message) {
        for (Socket socket : clientMap.keySet()) {
            threadPool.submit(() -> {
                try {
                    DataOutputStream clientOut = new DataOutputStream(socket.getOutputStream());
                    clientOut.writeUTF(message);
                } catch (IOException e) {
                    System.out.println("Erro ao enviar mensagem para cliente: " + e.getMessage());
                }
            });
        }
    }
}