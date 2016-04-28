package client;

import DataTypeHelpers.ConverterHelper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * Created by Bente on 28/04/2016.
 */
public class ClientThread implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;
    private int timer;

    private int ack = 0, seq = 0;
    private int receivedAck = 0, receivedSeq = 0;
    protected String received, sended;
    private final String data = ",";
    protected String[] receivedArray;

    /**
     *
     * @param socket
     * @param packet
     * @throws IOException
     */
    public ClientThread(DatagramSocket socket, DatagramPacket packet, int timer) throws IOException {
        this.socket = socket;
        this.packet = packet;
        this.timer = timer;
    }

    /**
     * Run method to send a package and wait for ACK
     */
    public void run() {
    }
}
