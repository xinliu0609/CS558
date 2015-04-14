package acs;
import java.net.*;
import java.io.*;

public class Server {
	public static void main(String args[]) throws IOException {
		ServerSocket serverSocket = new ServerSocket(15123);
		Socket socket = serverSocket.accept();
		System.out.println("Accepted connection: "+socket);
		File transferfile = new File("hello");
		byte[] bytearray =  new byte[(int)transferfile.length()];
		FileInputStream fin = new FileInputStream(transferfile);
		BufferedInputStream bin = new BufferedInputStream(fin);
		bin.read(bytearray,0,bytearray.length);
		OutputStream os = socket.getOutputStream();
		System.out.println("Sending files.....");
		os.write(bytearray,0,bytearray.length);
		os.flush();
		socket.close();
		System.out.println("File transfer complete");
	}
}