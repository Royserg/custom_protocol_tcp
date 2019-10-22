import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class MathClient {
    public static void main(String args[]) {
        String hostname = "localhost";
        int port = 9998;
        try {
            // Create a socket
            Socket socket = new Socket(hostname, port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Get input from user
            Scanner sc = new Scanner(System.in);
            System.out.println("<operator>:<value1>:<value2>");
            String userInput = sc.nextLine();

            writer.write(userInput);
            writer.newLine();
            writer.flush();
            // Get result from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println(reader.readLine());

            // Close streams
            reader.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
