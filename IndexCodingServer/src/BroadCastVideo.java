
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class BroadCastVideo extends Thread{
	
	List<String> videoList;
	List<VideoStream> streamList = new ArrayList<VideoStream>();
	List<byte[]> bufferList = new ArrayList<byte[]>();
	List<Long> startPointCounterList = new ArrayList<Long>();
	
	
	DatagramSocket socket = null;
    DatagramPacket outPacket = null;
    InetAddress address;
    final int PORT = 17012;

	
	public BroadCastVideo(List<String> list) throws Exception{
		
		
		videoList = list;
		for(String s : videoList){
			streamList.add(new VideoStream(s));
			bufferList.add(new byte[15000]);
			startPointCounterList.add(new Long(0));
		}
	}
	
	
	
	
	public void run(){
		
		try {
			socket = new DatagramSocket();
	        address = InetAddress.getByName("224.0.0.251");

		} catch (SocketException | UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		////////////////////////////prepare the headers of the udp packet////////////////////////////////////////////////
		
		/* This is what we use in general
	
		int numVideos = videoList.size();
		String[] videoNames = new String[numVideos];
		for(int i = 0; i < numVideos; i++){
			videoNames[i] = videoList.get(i);
		}
		long[] startingBytes = new long[numVideos];
		int[] frameLength = new int[numVideos];
		byte[] payload = new byte[15000];
		
		*/
		
		// we use this for demo

		VideoStream forwardStream = null;
		VideoStream backwardStream = null;
		
		try {
			forwardStream = new VideoStream("forward");
			backwardStream = new VideoStream("backward");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String str1 = "forward";
		String str2 = "backward";

		int forwardCounter = 0;
		int backwardCounter = 0;
		
		int forwardFrameLength = 0;
		int backwardFrameLength = 0;
		
		byte[] forwardBuffer = new byte[15000];
		byte[] backwardBuffer = new byte[15000];
		
		byte[] payload = new byte[15000];

		//////////////////////////////////////////////////////////////////////////////////////////////

		// send the udp packets
		for(int i = 0; i < 500; i++){
			
			try {
		
				////////////////////prepare the udp packet, header and payload///////////////////////////////////////////////////////
				
				/* This is what we use in general
				 
				for(int j = 0; j < numVideos; j++){
					
					frameLength[j] = streamList.get(j).getNextFrame(bufferList.get(j));
					startingBytes[j] = startCounterPointList.get(j);
					
					// generate the mixed payload
					int index = 0;
					for(byte b : bufferList.get(0)){
						// another for loop to Xor every buffer contents
						...
					}
					
					startPointList.set(j, ((long)startPointList.get(j)+frameLength[j]+5));	//update the starting point for next round
				}
				IndexCodingPacket p = new IndexCodingPacket(videoNames, startingBytes, frameLength, payload);
				*/
				
				// we use this in demo
				
				forwardFrameLength = forwardStream.getNextFrame(forwardBuffer);
				backwardFrameLength = backwardStream.getNextFrame(backwardBuffer);
				
				byte[] mixed = mix(forwardBuffer, backwardBuffer);
				
				IndexPacket packet = new IndexPacket(forwardFrameLength, backwardFrameLength, forwardCounter, backwardCounter, mixed);
				
				forwardCounter++;
				backwardCounter++;
				
				byte[] data = packet.generate();
				
				outPacket = new DatagramPacket(data, data.length, address, PORT);
								
		        System.out.println("sending image");
		        socket.send(outPacket);
		        
                Thread.sleep(40);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	public static byte[] serialize(Object obj) throws IOException {
	    ByteArrayOutputStream out = new ByteArrayOutputStream();
	    ObjectOutputStream os = new ObjectOutputStream(out);
	    os.writeObject(obj);
	    return out.toByteArray();
	}
	
	public static byte[] mix(byte[] b1, byte[] b2){
		
		byte[] mixed = new byte[b1.length];
		
		int i = 0;
		for(byte b : b1){
			mixed[i] = (byte)(0xff & (int)b ^ (int)b2[i++]);
		}
		
		return mixed;
	}
}
