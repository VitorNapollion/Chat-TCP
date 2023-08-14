
import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleTCPServer {
    private ServerSocket serverSocket;
    private List<ClientHandler> clientHandlers;

    public void start(int port) {
        try {
            System.out.println("[S1] Criando server socket para aguardar conexões de clientes em loop");
            serverSocket = new ServerSocket(port);
            clientHandlers = new ArrayList<>();

            while (true) {
                System.out.println("[S2] Aguardando conexão em: " + serverSocket.getLocalSocketAddress());
                Socket socket = serverSocket.accept();

                System.out.println("[S3] Conexão estabelecida com cliente: " + socket.getRemoteSocketAddress());

                ClientHandler handler = new ClientHandler(socket, this); 
                clientHandlers.add(handler);
                Thread handlerThread = new Thread(handler);
                handlerThread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void forwardMessage(ClientHandler sender, String message) {
        for (ClientHandler client : clientHandlers) {
            if (client != sender) {
                client.sendMessage("Cliente " + clientHandlers.indexOf(sender) + ": " + message);
            }
        }
    }

    public static void main(String[] args) {
        int serverPort = 6666;
        try {
            SimpleTCPServer server = new SimpleTCPServer();
            server.start(serverPort);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}