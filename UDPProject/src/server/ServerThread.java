package server;

import DataTypeHelpers.ConverterHelper;
import wrappers.PacketWrapper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by Bente on 28/04/2016.
 */
public class ServerThread extends Thread {
    protected DatagramSocket socket = null;
    protected BufferedReader in = null;
    protected int ack = 0, seq = 0;
    protected int receivedAck = 0, receivedSeq = 0, lastSeq = 0;
    protected String received, sended;
    protected String[] receivedArray;

    public ServerThread() throws IOException {
        this("QuoteServerThread", 9198);
    }

    /**
     *
     * @param name
     * @param port
     * @throws IOException
     */
    public ServerThread(String name, int port) throws IOException {
        super(name);
        socket = new DatagramSocket(port);
    }

    /**
     * Run method to start the server and receive packets. This also sends receipts for received packets.
     * All packages come in this form:
     * SEQ-number,ACK-number,DATA
     * The different parts are separated by commas and no spaces!
     */
    public void run() {
        //Initialize SEQ number by creating a random number between 1 and 1.000.000.000
        seq = new Random().nextInt(1000000000)+1;

        while (true) {
            try {
                byte[] buf = new byte[256];
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                PacketWrapper data = new PacketWrapper(packet.getData(), packet.getLength());

                System.out.println(new String(data.getData(), StandardCharsets.UTF_8));

                sendAcknowledgement(data, packet.getAddress(), packet.getPort());

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendAcknowledgement(PacketWrapper data, InetAddress address, int port) throws IOException {
        byte[] buf;
        ack = data.getSeq() + 1;
        seq++;
        buf = (seq + "," + ack + ",").getBytes(StandardCharsets.UTF_8);

        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
        socket.send(packet);
    }
}
