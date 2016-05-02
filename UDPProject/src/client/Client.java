package client;

import wrappers.PacketWrapper;

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
            int currentTimer = timer;

            PacketWrapper dataPacket = new PacketWrapper(Arrays.copyOfRange(data, (i-1)*maximumPacketSize, (i*maximumPacketSize > data.length) ? data.length : i*maximumPacketSize), seq, ack);
            DatagramPacket packet = new DatagramPacket(dataPacket.getFullData(), dataPacket.getFullData().length, serverIP, serverPort);

            Future future = pool.submit(new ClientThread(socket, packet, seq, ack));

            while(!future.isDone()) {
                try {
                    future.get(currentTimer, TimeUnit.MILLISECONDS);
                } catch(TimeoutException | InterruptedException | ExecutionException e) {
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
