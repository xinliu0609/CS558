import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.List;


public class BroadCastVideo extends Thread{
	
	List<String> videoList;
	List<VideoStream> streamList;
	List<byte[]> bufferList;
	
	DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    InetAddress address;
    final int PORT = 17012;

	
	public BroadCastVideo(List<String> list) throws Exception{
		
		/*
		videoList = list;
		for(String s : videoList){
			streamList.add(new VideoStream(s));
			bufferList.add(new byte[15000]);
		}
		*/
	}
	
	
	
	
	public void run(){
		
		try {
			socket = new DatagramSocket();
	        address = InetAddress.getByName("224.0.0.251");

		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		VideoStream vs = null;
		try {
			vs = new VideoStream("forward");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		byte[] outBuf = new byte[15000];

		for(int i = 0; i < 300; i++){
			
			try {
				int size = vs.getNextFrame(outBuf);
		        outPacket = new DatagramPacket(outBuf, outBuf.length,address, PORT);
		        System.out.println("sending image");
		        socket.send(outPacket);
                Thread.sleep(50);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
}
