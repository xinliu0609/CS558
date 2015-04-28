package com.iit.xin.testing;

import java.io.Serializable;


public class IndexCodingPacket implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5483357131759357149L;
	
	String[] videos;			// video 1, video 2, ...
	long[] startingByteNumber; 	// starting point of video 1, starting point of video 2, ...
	int[] frameLength;			// length of video 1, length of video 2, ...
	byte[] payload;
	
	public IndexCodingPacket(String[] s, long[] l, int[] i, byte[] b){
		videos = s;
		startingByteNumber = l;
		frameLength = i;
		payload = b;
	}

}

