package co.edu.escuelaing.httpServer;

import java.net.URI;
import java.net.URL;

public class ReadURL {
    public static void main(String[] args) {
        try {
            URL personalSite = new URI("http://ldbn.escuelaing.edu.co/respuestasexamen.txt").toURL();

            System.out.println("Protocol: " + personalSite.getProtocol());
            System.out.println("getAuthority " + personalSite.getAuthority());
            System.out.println("getHost: "+ personalSite.getHost());
            // TODO: complete whit the rest...
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}