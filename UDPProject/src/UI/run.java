package UI;

import client.Client;
import server.ServerThread;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Created by Bente on 29/04/2016.
 */
public class Run {
    public static int clientPort = 9923;
    public static int serverPort = 9876;
    public static InetAddress serverIP;

    public static void main(String[] args) {

        try {
            serverIP = InetAddress.getByName("127.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        //START SERVER
        try {
            Thread server = new ServerThread("Test", serverPort);

            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Client client = new Client(serverPort, serverIP, clientPort);

            String longString = "DETTE ER EN SAMMENHÆNGENDE TEKST"
                                +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                            +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                            +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                            +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST"
                    +   "\nDETTE ER EN SAMMENHÆNGENDE TEKST";



            client.sendPackets(longString.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
