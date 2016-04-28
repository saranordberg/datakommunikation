package client;

import javafx.concurrent.Task;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.*;

/**
 * Created by Bente on 28/04/2016.
 */
public class Client {

    private DatagramSocket socket;
    private int port;
    private InetAddress ip;

    private final int window = 5;
    private int timer = 1000;

    private int maximumPacketSize = 556;

    public Client(int port, InetAddress ip) throws IOException {
        this.port = port;
        this.ip = ip;
        this.socket = new DatagramSocket(port);
    }

    public void sendPackets(byte[] data) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(window);

        for(int i = 1; i*maximumPacketSize <= data.length; i++) {
            int currentTimer = timer;
            DatagramPacket packet = new DatagramPacket(data, (i-1)*maximumPacketSize, data.length, ip, port);
            Future future = pool.submit(new ClientThread(socket, packet, timer));

            boolean success = false;
            while(!success) {
                try {
                    future.get(currentTimer, TimeUnit.MILLISECONDS);
                    success = true;
                } catch(TimeoutException | InterruptedException | ExecutionException e) {
                    success = false;
                    currentTimer = currentTimer + timer;
                }
            }

            //Runnable r = new ClientThread(socket, packet, timer);
            //pool.execute(r);
        }
    }
}
