
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xin on 4/28/15.
 */
public class IndexPacket {

    public String videoName;
    public byte[] payload;

    public IndexPacket(String str, byte[] b){
        videoName = str;
        payload = b;
    }

    public IndexPacket(byte[] whole) throws IOException {
        InputStream is = new ByteArrayInputStream(whole);
        byte[] header = new byte[7];
        is.read(header, 0, 7);
        videoName = new String(header);
    }

    public byte[] generate(){
        byte[] head = videoName.getBytes();
        byte[] combined = new byte[head.length+payload.length];
        System.arraycopy(head, 0, combined, 0, head.length);
        System.arraycopy(payload, 0, combined, head.length, payload.length);

        return combined;
    }

}
