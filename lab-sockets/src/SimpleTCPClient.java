import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;


public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public void start(String serverIp, int serverPort) throws IOException {
        System.out.println("[C1] Conectando com servidor " + serverIp + ":" + serverPort);
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());

        Scanner scanner = new Scanner(System.in);
        try {
            while (true) {
                System.out.print("Você: ");
                String msg = scanner.nextLine();

                if (msg.equalsIgnoreCase("sair")) {
                    break;
                }

                output.writeUTF(msg);
                System.out.println("[C2] Mensagem enviada, aguardando resposta");

                String response = input.readUTF();
                System.out.println("[C3] Resposta do servidor: " + response);
            }
        } finally {
            scanner.close();
            stop();
        }
    }

    public void stop() {
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String serverIp = "127.0.0.1"; 
        int serverPort = 6666;
        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}