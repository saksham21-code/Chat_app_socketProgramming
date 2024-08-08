import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ChatClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (Socket socket = new Socket(SERVER_IP, SERVER_PORT)) {
            System.out.println("Connected to the chat server.");

            new Thread(new ReadMessage(socket)).start();
            new Thread(new WriteMessage(socket)).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ReadMessage implements Runnable {
    private Socket socket;
    private BufferedReader in;

    public ReadMessage(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String message;
        try {
            while ((message = in.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class WriteMessage implements Runnable {
    private Socket socket;
    private PrintWriter out;
    private Scanner scanner;

    public WriteMessage(Socket socket) {
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        scanner = new Scanner(System.in);
    }

    @Override
    public void run() {
        String message;
        while (true) {
            message = scanner.nextLine();
            out.println(message);
        }
    }
}
