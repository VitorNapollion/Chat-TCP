import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorMSG {
    public static void main(String args[]) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        int serverPort = 6666;

        try (ServerSocket serverSocket = new ServerSocket(serverPort)) {
            System.out.println("Servidor Iniciado... Endereço: " + InetAddress.getLocalHost() + ":" + serverPort);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão estabelecida com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());

                ClientHandler handler = new ClientHandler(clientSocket, threadPool);
                threadPool.submit(handler);
            }
        } catch (IOException e) {
            System.out.println("Erro no socket: " + e.getMessage());
        }
    }
}