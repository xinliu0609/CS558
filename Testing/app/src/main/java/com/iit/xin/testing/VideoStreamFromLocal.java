package com.iit.xin.testing;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xin on 4/7/15.
 */
public class VideoStreamFromLocal extends VideoStream {

    public VideoStreamFromLocal(int identifier, Context context){
        this.sourceInputStream = context.getResources().openRawResource(identifier);
    }

    @Override
    public Bitmap getNextBitmap() throws IOException {

        int length;
        byte[] header = new byte[5];
        byte[] buffer = new byte[10000];

        sourceInputStream.read(header, 0, 5);
        length = Integer.parseInt(new String(header));
        //Log.d("tag0", "imageLength is "+length+" bytes");
        sourceInputStream.read(buffer, 0, length);
        return BitmapFactory.decodeByteArray(buffer, 0, length);
    }
}