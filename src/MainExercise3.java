import java.io.IOException;

public class MainExercise3 {
	
    public static void main(String[] args) throws IOException {
        
    	// Start the server
        Server server = new Server(2345);
        server.startServer();

        // Connect the client
        Client client = new Client("localhost", 2345);
        client.connectToServer();

        // Send text from client to server
        String text = "Hello, how are you?";
        client.sendText(text);

        // Receive word count from server
        int wordCount = client.receiveWordCount();

        // Close the connection and server
        client.closeConnection();
        server.closeServer();
    }
}
