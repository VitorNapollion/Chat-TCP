import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.io.*;

public class ThreadPoolTCPServer {
    public static void main(String[] args) {
        int serverPort = 6666;

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        try {
            ServerSocket serverSocket = new ServerSocket(serverPort);

            while (serverSocket.isBound()) {
                System.out.println("Aguardando conexao no endereco: " + InetAddress.getLocalHost() + ":" + serverPort);
                Socket clientSocket = serverSocket.accept();

                SimpleTCPServer server = new SimpleTCPServer(); // Criar uma instância do servidor
                ClientHandler handler = new ClientHandler(clientSocket, server); // Passar a instância do servidor

                executorService.execute(handler); // Iniciar a thread do handler
                System.out.println("Conexao feita com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }

            executorService.shutdown();
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}

