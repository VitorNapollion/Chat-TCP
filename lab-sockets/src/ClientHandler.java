import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


   class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private SimpleTCPServer server;

    public ClientHandler(Socket clientSocket, SimpleTCPServer server) {
        try {
            this.server = server;
            socket = clientSocket;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        try {
            output.writeUTF(message);
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            while (true) {
                String msg = input.readUTF();
                System.out.println("[S4] Mensagem recebida de " + socket.getRemoteSocketAddress() + ": " + msg);

                if (msg.equalsIgnoreCase("exit")) {
                    break;
                }

                server.forwardMessage(this, msg); 
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                input.close();
                output.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}