package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.*;

/**
 * Created by Bente on 28/04/2016.
 */
public class Client {

    private DatagramSocket socket;
    private int serverPort;
    private InetAddress serverIP;
    private int clientPort;

    private final int window = 5;
    private int timer = 1000;

    private int maximumPacketSize = 200;//542; //removed 2x integers and 2 commas
    private int seq, ack = 0;
    private String dataToSend;
    private byte[] byteToSend;

    public Client(int serverPort, InetAddress ip, int clientPort) throws IOException {
        this.serverPort = serverPort;
        this.serverIP = ip;
        this.clientPort = clientPort;
        this.socket = new DatagramSocket(clientPort);
    }

    public void sendPackets(byte[] data) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(window);
        seq = new Random().nextInt(1000000000)+1;

        for(int i = 1; data.length - ((i-1)* maximumPacketSize) > 0; i++) {

            dataToSend = seq + "," + ack + "," + new String(Arrays.copyOfRange(data, (i-1)*maximumPacketSize, (i*maximumPacketSize > data.length) ? data.length : i*maximumPacketSize));

            int currentTimer = timer;
            byteToSend = dataToSend.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(byteToSend, byteToSend.length, serverIP, serverPort);

            Future future = pool.submit(new ClientThread(socket, packet, seq, ack));

            boolean success = false;
            while(!future.isDone()) {
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
            seq++;
        }

        pool.shutdown();
    }
}
