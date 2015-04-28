
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * Created by xin on 4/28/15.
 */
public class IndexPacket {

    //public int length_1;
    //public int length_2;
    
    public int[] frameLength = new int[2];	//frame length of forward, backward
    public int[] byteCount = new int[2];	//byte count of forward, backward
    
    public byte[] payload;

    public IndexPacket(int l1, int l2, int c1, int c2, byte[] b){
        frameLength[0] = l1;	//forward
        frameLength[1] = l2;	//backward
        byteCount[0] = c1;		//forward
        byteCount[1] = c2;		//backward
        payload = b;
    }

    public IndexPacket(byte[] whole) throws IOException {
        InputStream is = new ByteArrayInputStream(whole);
        byte[] header = new byte[4];
        
        is.read(header, 0, 4);
        frameLength[0] = ByteBuffer.wrap(header).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
        is.read(header, 0, 4);
        frameLength[1] = ByteBuffer.wrap(header).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
        is.read(header, 0, 4);
        byteCount[0] = ByteBuffer.wrap(header).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
        is.read(header, 0, 4);
        byteCount[1] = ByteBuffer.wrap(header).order(java.nio.ByteOrder.BIG_ENDIAN).getInt();
        
        payload = new byte[15000];
        is.read(payload, 0, 15000);
        
    }

    public byte[] generate(){
        //byte[] head1 = ByteBuffer.allocate(4).putInt(length_1).array();
        //byte[] head2 = ByteBuffer.allocate(4).putInt(length_2).array();
        
    	
    	byte[] head1 = intToByteArray(frameLength[0]);
    	byte[] head2 = intToByteArray(frameLength[1]);
    	byte[] head3 = intToByteArray(byteCount[0]);
    	byte[] head4 = intToByteArray(byteCount[1]);

    	byte[] combined = new byte[16+payload.length];
        System.arraycopy(head1, 0, combined, 0, 4);
        System.arraycopy(head2, 0, combined, 4, 4);
        System.arraycopy(head3, 0, combined, 8, 4);
        System.arraycopy(head4, 0, combined, 12, 4);
        System.arraycopy(payload, 0, combined, 16, payload.length);
        
        return combined;
    }
    
    public static byte[] intToByteArray(int a)
    {
        byte[] ret = new byte[4];
        ret[3] = (byte) (a & 0xFF);   
        ret[2] = (byte) ((a >> 8) & 0xFF);   
        ret[1] = (byte) ((a >> 16) & 0xFF);   
        ret[0] = (byte) ((a >> 24) & 0xFF);
        return ret;
    }

}
