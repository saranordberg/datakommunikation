package client;

import DataTypeHelpers.ConverterHelper;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

/**
 * Created by Bente on 28/04/2016.
 */
public class ClientThread implements Runnable {
    private DatagramSocket socket;
    private DatagramPacket packet;

    private byte[] buf = new byte[256];

    private boolean correctAck = true;

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
    public ClientThread(DatagramSocket socket, DatagramPacket packet, int seq, int ack) throws IOException {
        this.socket = socket;
        this.packet = packet;
        this.seq = seq;
        this.ack = ack;
    }

    /**
     * Run method to send a package and wait for receipt
     */
    public void run() {

        try {
            socket.send(packet);

            while(true) {
            	//Get receipt
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                //Get string
                byte[] data = packet.getData();
                received = new String(data, StandardCharsets.UTF_8);
                receivedArray = received.split(",", 3);
                receivedSeq = ConverterHelper.toInt(receivedArray[0]);
                receivedAck = ConverterHelper.toInt(receivedArray[1]);
                
                if(receivedAck == seq + 1) {
                    return;
                }
            }


        } catch (IOException e) {
        }

    }
/*
    @Override
    public Integer call() throws Exception {
        try {
            socket.send(packet);

            while(true) {
                //Get receipt
                packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                //Get string
                byte[] data = packet.getData();
                received = new String(data, StandardCharsets.UTF_8);
                receivedArray = received.split(",", 3);
                receivedSeq = ConverterHelper.toInt(receivedArray[0]);
                receivedAck = ConverterHelper.toInt(receivedArray[1]);

                if(receivedAck == seq + 1) {
                    socket.close();
                    return receivedSeq;
                }
            }


        } catch (IOException e) {
            return null;
        }
    }*/
}
