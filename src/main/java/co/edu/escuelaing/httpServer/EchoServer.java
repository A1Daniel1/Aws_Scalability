package co.edu.escuelaing.httpServer;

import java.net.*;
import java.io.*;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        //creates a port
        ServerSocket serverSocket = new ServerSocket(35000);
        Socket clientSocket = serverSocket.accept();

        PrintWriter out = new PrintWriter(
            clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(
            new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            System.out.println("Message: " + inputLine);
            String outputLine = "Response: " + inputLine;
            out.println(outputLine);
            if (outputLine.equals("Response: Bye.")) break;
        }

        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}