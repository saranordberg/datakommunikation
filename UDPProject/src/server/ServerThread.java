package server;

import DataTypeHelpers.ConverterHelper;

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
    protected int receivedAck = 0, receivedSeq = 0;
    protected String received, sended;
    protected final String data = ",";
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
            byte[] buf = new byte[256];
            try {
                // receive request
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                //Receive seq,ack,data from packet
                byte[] data = packet.getData();
                received = new String(data, StandardCharsets.UTF_8);
                receivedArray = received.split(",", 3);
                receivedSeq = ConverterHelper.toInt(receivedArray[0]);
                receivedAck = ConverterHelper.toInt(receivedArray[1]);
                received = receivedArray[2];
                System.out.println(received);

                // send the response to the client at "address" and "port"

                ack = receivedSeq + 1;
                seq++;
                buf = new String(seq + "," + ack + data).getBytes(StandardCharsets.UTF_8);

                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }

            socket.close();
        }
    }
}
