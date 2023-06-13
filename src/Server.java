import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private int port;
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    public Server(int port) {
        this.port = port;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started. Listening on port " + port);
            waitForClientConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForClientConnection() {
        try {
            clientSocket = serverSocket.accept();
            out = new ObjectOutputStream(clientSocket.getOutputStream());
            in = new ObjectInputStream(clientSocket.getInputStream());
            System.out.println("Client connected.");
            processText();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void processText() {
        try {
            String text = (String) in.readObject();
            int wordCount = calculateWordCount(text);
            sendWordCount(wordCount);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private int calculateWordCount(String text) {
        // Word count logic goes here
        String[] words = text.split("\\s+");
        return words.length;
    }

    private void sendWordCount(int wordCount) {
        try {
            out.writeInt(wordCount);
            out.flush();
            System.out.println("Word count sent to client: " + wordCount);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServer() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
            System.out.println("Server closed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}