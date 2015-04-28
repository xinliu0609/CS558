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

    public VideoStreamFromLocal(String str, Context context){
        int id = context.getResources().getIdentifier(str, "raw", context.getPackageName());
        this.sourceInputStream = context.getResources().openRawResource(id);
    }

    public byte[] getNextByteArray() throws IOException{
        int length;
        byte[] header = new byte[5];
        byte[] buffer = new byte[15000];

        sourceInputStream.read(header, 0, 5);
        length = Integer.parseInt(new String(header));

        Log.d("tag0", "imageLength is "+length+" bytes");
        sourceInputStream.read(buffer, 0, length);

        return buffer;
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
    }
}