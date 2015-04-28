package com.iit.xin.testing;

import java.io.Serializable;


public class IndexCodingPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5483357131759357149L;
	
	//String[] videos;			// video 1, video 2, ...
	//long[] startingByteNumber; 	// starting point of video 1, starting point of video 2, ...
	//int[] frameLength;			// length of video 1, length of video 2, ...
	int length1;
	int length2;
	byte[] payload;
	
	public IndexCodingPacket(int l1, int l2, byte[] b){
		length1 = l1;
		length2 = l2;
		payload = b;
	}
}

