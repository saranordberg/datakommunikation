package client;

import javafx.concurrent.Task;

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
    private int port;
    private InetAddress ip;

    private final int window = 5;
    private int timer = 1000;

    private int maximumPacketSize = 556;
    private int seq;
    private Integer ack;
    private String dataToSend;

    public Client(int port, InetAddress ip) throws IOException {
        this.port = port;
        this.ip = ip;
        this.socket = new DatagramSocket(port);
    }

    public void sendPackets(byte[] data) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(window);
        seq = new Random().nextInt(1000000000)+1;

        for(int i = 1; i*maximumPacketSize <= data.length; i++) {

            dataToSend = seq + "," + ack + "," + new String(Arrays.copyOfRange(data, (i-1)*maximumPacketSize, i*maximumPacketSize));
            int currentTimer = timer;
            DatagramPacket packet = new DatagramPacket(dataToSend.getBytes(StandardCharsets.UTF_8), data.length, ip, port);

            Future<Integer> future = pool.submit(new ClientThread(socket, packet, seq,ack));

            boolean success = false;
            while(!success) {
                try {
                    ack = future.get(currentTimer, TimeUnit.MILLISECONDS) +1;
                    if(ack != null)
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
