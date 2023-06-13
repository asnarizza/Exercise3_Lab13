import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Client(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public void connectToServer() {
        try {
            socket = new Socket(serverAddress, serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            System.out.println("Connected to server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendText(String text) {
        try {
            out.writeObject(text);
            out.flush();
            System.out.println("Text sent to server: " + text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int receiveWordCount() {
        int wordCount = 0;
        try {
            wordCount = in.readInt();
            System.out.println("Received word count from server: " + wordCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordCount;
    }

    public void closeConnection() {
        try {
            in.close();
            out.close();
            socket.close();
            System.out.println("Connection closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
