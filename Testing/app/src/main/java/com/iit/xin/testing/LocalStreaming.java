package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xin on 4/26/15.
 */
public class LocalStreaming implements Runnable {

    InputStream inputStream;
    Context context;
    OutputStream outputStream;
    BlockingQueue<Bitmap> blockingQueue;
    Bitmap bitmap;


    int length;

    byte[] header = new byte[5];
    byte[] buffer = new byte[15000];

    LocalStreaming(Context c, int id, BlockingQueue<Bitmap> bq){
        context = c;
        inputStream = c.getResources().openRawResource(id);
        blockingQueue = bq;
    }

    @Override
    public void run() {

        for(int i = 0; i < 500; i++){

            try {
                inputStream.read(header, 0, 5);
            } catch (IOException e) {
                e.printStackTrace();
            }

            length = Integer.parseInt(new String(header));

            try {
                inputStream.read(buffer, 0, length);
            } catch (IOException e) {
                e.printStackTrace();
            }

            bitmap = BitmapFactory.decodeByteArray(buffer, 0, length);

            try {
                blockingQueue.put(bitmap);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Log.d("tag0", "I wrote length "+length);

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
