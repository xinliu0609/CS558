package com.iit.xin.testing;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by xin on 4/28/15.
 */
public class IndexPacket {

    public int length_1;
    public int length_2;
    public byte[] payload;
    public byte[] side;

    public IndexPacket(int l1, int l2, byte[] b, byte[] b2){
        length_1 = l1;
        length_2 = l2;
        payload = b;
        side = b2;
    }

    public IndexPacket(byte[] whole) throws IOException {
        InputStream is = new ByteArrayInputStream(whole);
        byte[] header = new byte[4];
        is.read(header, 0, 4);
        
        length_1 = ByteBuffer.wrap(header).getInt();
        is.read(header, 0, 4);
        length_1 = ByteBuffer.wrap(header).getInt();
 
        payload = new byte[15000];
        is.read(payload, 0, 15000);
        
        side = new byte[15000];
        is.read(side, 0, 15000);
    }

    public byte[] generate(){
        byte[] head1 = ByteBuffer.allocate(4).putInt(length_1).array();
        byte[] head2 = ByteBuffer.allocate(4).putInt(length_2).array();
        byte[] combined = new byte[8+payload.length+side.length];
        System.arraycopy(head1, 0, combined, 0, 4);
        System.arraycopy(head2, 0, combined, 4, 4);
        System.arraycopy(payload, 0, combined, 7, payload.length);
        System.arraycopy(side, 0, combined, 7+payload.length, side.length);
        
        //System.out.println("header1 has value "+ByteBuffer.wrap(head1).getInt());
        //System.out.println("header2 has value "+ByteBuffer.wrap(head2).getInt());


        return combined;
    }

}
