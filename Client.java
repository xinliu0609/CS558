package acs;

import java.net.*;
import java.io.*;

public class Client
{
	public static void main(String args[]) throws IOException
	{
		int filesize = 1022386;
		int bytesread;
		int currenttot=0;
		Socket socket = new Socket("127.0.0.1",15123);
		byte[] bytearray = new byte[filesize];
		InputStream is = socket.getInputStream();
		FileOutputStream fos = new FileOutputStream("copy.txt");
		BufferedOutputStream bos = new BufferedOutputStream(fos);
		bytesread = is.read(bytearray,0,bytearray.length);
		currenttot = bytesread;
		do {
			bytesread = is.read(bytearray,currenttot,(bytearray.length-currenttot));
			if(bytesread >=0)currenttot+=bytesread;
		}while(bytesread > -1);
		bos.write(bytearray,0,currenttot);
		bos.flush();
		bos.close();
		socket.close();
	}
}