package wrappers;

import DataTypeHelpers.ConverterHelper;

import java.nio.charset.StandardCharsets;

/**
 * Created by Bente on 02/05/2016.
 */
public class PacketWrapper {
    private byte[] fullData;
    private byte[] data;
    private int ack, seq;

    public PacketWrapper(byte[] fullData, int length) {
        this.fullData = fullData;

        String[] received = new String(this.fullData, 0, length, StandardCharsets.UTF_8).split(",", 3);

        seq = ConverterHelper.toInt(received[0]);
        ack = ConverterHelper.toInt(received[1]);
        data = received[2].getBytes();
    }

    public PacketWrapper(byte[] data, int seq, int ack) {
        this.data = data;
        this.seq = seq;
        this.ack = ack;

        this.fullData = new String(seq + "," + ack + "," + new String(data, StandardCharsets.UTF_8)).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getFullData() {
        return fullData;
    }

    public byte[] getData() {
        return data;
    }

    public int getAck() {
        return ack;
    }

    public int getSeq() {
        return seq;
    }
}
