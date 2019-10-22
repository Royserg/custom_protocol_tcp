import java.io.*;
import java.net.*;

public class MathServer {

    private MathService mathService;
    private Socket socket;


    public void setMathService(MathService mathService) {
        this.mathService = mathService;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    // Custom protocol for getting math operations
    // operator:value1:value2
    private double parseExecution(String line) {
        double result, value1, value2;

        String elements[] = line.split(":");

        if (elements.length != 3) {
            throw new IllegalArgumentException("Usage: <operator>:<value1>:<value2>");
        }

        try {
            value1 = Double.parseDouble(elements[1]);
            value2 = Double.parseDouble(elements[2]);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid arguments");
        }

        switch (elements[0].charAt(0)) {
            case '+':
                result = mathService.add(value1, value2);
                break;
            case '-':
                result = mathService.sub(value1, value2);
                break;
            case '/':
                result = mathService.div(value1, value2);
                break;
            case '*':
                result = mathService.mul(value1, value2);
                break;
            default:
                throw new IllegalArgumentException("Invalid math operator! ('+', '-', '/', '*') ");
        }

        return result;
    }


    public void execute() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Read message from the client
            String line = reader.readLine();
            double result = parseExecution(line);

            // Write result back to the client
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            writer.write("result: " + result);
            writer.newLine();
            writer.flush();

            // Close the stream
            reader.close();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        int port = 9998;
        System.out.println("Math server is running..");
        // Create a server socket and wait for client's connection
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            // Create a socket when client connects to the server
            Socket socket = serverSocket.accept();
            // Run math server that talks to the client
            MathServer mathServer = new MathServer();
            mathServer.setMathService(new MathServiceImpl());
            mathServer.setSocket(socket);
            mathServer.execute();
            System.out.println("Math Server is closed..");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
