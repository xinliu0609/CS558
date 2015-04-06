package com.iit.xin.testing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xin on 4/3/15.
 */
public class VideoStream {
    InputStream is;
    int currentFrame;
    byte[] buffer = new byte[10000];

    public VideoStream(InputStream inputStream) throws Exception{
        is = inputStream;
        currentFrame = 0;
    }

    /*
    public int getNextFrame(byte[] frame) throws Exception{
        int length;
        byte[] header = new byte[5];

        is.read(header, 0, 5);
        length=Integer.parseInt(new String(header));
        System.out.println("Frame "+currentFrame+":, "+length +" bytes");
        currentFrame++;
        return(is.read(frame, 0, length));
    }
    */

    public Bitmap getNextBitmap() throws IOException {
        int length;
        byte[] header = new byte[5];

        is.read(header, 0, 5);
        length = Integer.parseInt(new String(header));
        currentFrame++;
        is.read(buffer, 0, length);
        return BitmapFactory.decodeByteArray(buffer, 0, length);

    }
}
