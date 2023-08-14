import java.net.*;
import java.io.*;

public class MultiTCPServer {
   public static void main(String args[]) {
        try {
            int serverPort = 6666; // the server port
            ServerSocket serverSocket = new ServerSocket(serverPort);
            while (serverSocket.isBound()) {
                System.out.println("Aguardando conexao no endereco: " + InetAddress.getLocalHost() + ":" + serverPort);
                Socket clientSocket = serverSocket.accept();
                SimpleTCPServer server = new SimpleTCPServer(); // Cria uma instância do servidor
                ClientHandler handler = new ClientHandler(clientSocket, server); // Passa a instância do servidor
                Thread handlerThread = new Thread(handler); // Cria uma nova thread para o ClientHandler
                handlerThread.start(); // Inicia a thread
                System.out.println("Conexao feita com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
            }
            serverSocket.close();
        } catch (IOException e) {
            System.out.println("Listen socket:" + e.getMessage());
        }
    }
}