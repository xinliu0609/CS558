import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Stack;


public class VideoStream {
	
	FileInputStream fis;
	int currentFrame;
	public VideoStream(String filename) throws Exception{
		fis = new FileInputStream(filename);
		currentFrame = 0;
	}
	
	public int getNextFrame(byte[] frame) throws Exception{
		int length = 0;
		String length_string;
		byte[] header = new byte[5];
		
		fis.read(header, 0, 5);
		length=Integer.parseInt(new String(header));
		System.out.println("Image size is "+ length +" bytes");
		return(fis.read(frame, 0, length));
	}
	
	/*
	
	public static int readHeader(byte[] headerBuffer, FileInputStream fis) throws IOException{
		fis.read(headerBuffer, 0, 5);
		int length = Integer.parseInt(new String(headerBuffer));
		System.out.println("next frame has length "+length);
		return length;
	}
	
	public static void readFrame(byte[] frameBuffer, int length, FileInputStream fis) throws IOException{
		fis.read(frameBuffer, 0, length);
	}
	
	public static void main(String[] args) throws IOException{
	
		Stack<byte[]> byteStack = new Stack<byte[]>();
		byte[] header = new byte[5];
		byte[] frameBuffer = new byte[15000];
		
		
		File file = new File("result.mjpeg");
		FileOutputStream fos = new FileOutputStream(file);
		FileInputStream fis = new FileInputStream("movie.mjpeg");
		
		int times = 0;
		
		while(times < 500){
			
			int length = VideoStream.readHeader(header, fis);
			VideoStream.readFrame(frameBuffer, length, fis);
			
			byte[] combined = new byte[5+length];
			
			System.arraycopy(header, 0, combined, 0, 5);
			System.arraycopy(frameBuffer, 0, combined, 5, length);
			
			byteStack.add(combined);
						
			times++;
		}
		
		while(!byteStack.empty()){
			fos.write(byteStack.pop());
		}
		
		fos.close();
		
	}
	*/
}
