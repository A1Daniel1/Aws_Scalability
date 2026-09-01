package co.edu.escuelaing.httpServer;

import java.net.*;
import java.io.*;

public class HttpServer {
    public static void main(String[] args) throws IOException, URISyntaxException {
        ServerSocket serverSocket = new ServerSocket(35000);
        Boolean runnig = true;

        // while para mantener esto corriendo
        while (runnig) {

            System.out.println("Ready to receive...");

            Socket clientSocket = serverSocket.accept();

            PrintWriter out = new PrintWriter(
                    clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));

            boolean isFirtsLine = true;
            String reqURIStr = "";
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (isFirtsLine) {
                    reqURIStr = inputLine.split(" ")[1];
                    isFirtsLine = false;
                }

                System.out.println("Received: " + inputLine);
                if (!in.ready())
                    break;
            }

            URI reqURI = new URI(reqURIStr);
            reqURI.getPath();

            String output = "";
            if (reqURI.getPath().startsWith("/hello")) {
                String queryStr = reqURI.getQuery();
                System.out.println("Query str: " + queryStr);
                output = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n"
                        + "{\"response\":\"Hello, World\",\"query\":\"" + queryStr + "\"}";
            }

            else {

                // mensaje completo
                output = "HTTP/1.1 200 OK\r\n"
                        + "Content-Type: text/html\r\n\r\n"
                        + "<!DOCTYPE html>\n"
                        + "<html>\n"
                        + "    <head>\n"
                        + "        <title>Form Example</title>\n"
                        + "        <meta charset=\"UTF-8\">\n"
                        + "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
                        + "    </head>\n"
                        + "    <body>\n"
                        + "        <h1>Form with GET</h1>\n"
                        + "        <form action=\"/hello\">\n"
                        + "            <label for=\"name\">Name:</label><br>\n"
                        + "            <input type=\"text\" id=\"name\" name=\"name\" value=\"John\"><br><br>\n"
                        + "            <input type=\"button\" value=\"Submit\" onclick=\"loadGetMsg()\">\n"
                        + "        </form>\n"
                        + "        <div id=\"getrespmsg\"></div>\n"
                        + "\n"
                        + "\n"
                        + "        <script>\n"
                        + "            function loadGetMsg() {\n"
                        + "                let nameVar = document.getElementById(\"name\").value;\n"
                        + "                const xhttp = new XMLHttpRequest();\n"
                        + "                xhttp.onload = function() {\n"
                        + "                    document.getElementById(\"getrespmsg\").innerHTML =\n"
                        + "                    this.responseText;\n"
                        + "                }\n"
                        + "                xhttp.open(\"GET\", \"/hello?name=\"+nameVar);\n"
                        + "                xhttp.send();\n"
                        + "            }\n"
                        + "        </script>\n"
                        + "\n"
                        + "\n"
                        + "        <h1>Form with POST</h1>\n"
                        + "        <form action=\"/hellopost\">\n"
                        + "            <label for=\"postname\">Name:</label><br>\n"
                        + "            <input type=\"text\" id=\"postname\" name=\"name\" value=\"John\"><br><br>\n"
                        + "            <input type=\"button\" value=\"Submit\" onclick=\"loadPostMsg(postname)\">\n"
                        + "        </form>\n"
                        + "        \n"
                        + "        <div id=\"postrespmsg\"></div>\n"
                        + "        \n"
                        + "        <script>\n"
                        + "            function loadPostMsg(name){\n"
                        + "                let url = \"/hellopost?name=\" + name.value;\n"
                        + "\n"
                        + "\n"
                        + "                fetch (url, {method: 'POST'})\n"
                        + "                    .then(x => x.text())\n"
                        + "                    .then(y => document.getElementById(\"postrespmsg\").innerHTML = y);\n"
                        + "            }\n"
                        + "        </script>\n"
                        + "    </body>\n"
                        + "</html>";
                out.println(output);

                out.close();
                in.close();
                clientSocket.close();
            }
        }
        serverSocket.close();
    }
}