package com.iit.xin.testing;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;

/**
 * Created by xin on 4/7/15.
 */
public class VideoStreamFromLocal{

    InputStream sourceInputStream;

    public VideoStreamFromLocal(int identifier, Context context){
        this.sourceInputStream = context.getResources().openRawResource(identifier);
    }

    public Bitmap getNextBitmap() throws IOException {

        int length;
        byte[] header = new byte[5];
        byte[] buffer = new byte[15000];

        sourceInputStream.read(header, 0, 5);
        length = Integer.parseInt(new String(header));

        Log.d("tag0", "imageLength is "+length+" bytes");
        sourceInputStream.read(buffer, 0, length);

        return BitmapFactory.decodeByteArray(buffer, 0, length);


        /* experiment to see of the xor actually works

        byte[] b1 = new byte[buffer.length];
        byte[] mixed = new byte[buffer.length];

        // mixed = buffer xor b1
        int i = 0;
        for(byte b : buffer){
            mixed[i] = (byte)(0xff & (int)b ^ (int)b1[i++]);
        }

        // new_buffer = mixed xor b1
        byte[] new_buffer = new byte[buffer.length];
        int j = 0;
        for(byte b : mixed){
            new_buffer[j] = (byte)(0xff & (int)b ^ (int)b1[j++]);
        }

        return BitmapFactory.decodeByteArray(new_buffer, 0, length);

        */

    }
}