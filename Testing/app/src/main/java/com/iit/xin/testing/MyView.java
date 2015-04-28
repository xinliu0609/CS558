package com.iit.xin.testing.shared;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xin on 4/26/15.
 */
public class MyView extends SurfaceView implements Runnable {

    Thread thread = null;
    SurfaceHolder holder;
    boolean goodToDraw = false;

    byte[] buffer = new byte[15000];
    private Bitmap bitmap;
    InputStream inputStream;
    BlockingQueue<Bitmap> blockingQueue;

    public MyView(Context context, BlockingQueue<Bitmap> bq) {
        super(context);
        holder = getHolder();
        blockingQueue = bq;
    }


    @Override
    public void run() {

        while (goodToDraw) {

            // perform canvas drawing
            if (!holder.getSurface().isValid()) {
                continue;
            }

            Canvas canvas = holder.lockCanvas();

            try {
                bitmap = blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            canvas.drawBitmap(Bitmap.createScaledBitmap(bitmap, getWidth(), getHeight(), true), 0, 0, null);

            holder.unlockCanvasAndPost(canvas);

            try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void onPause() {

        goodToDraw = false;
        while (true) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    public void onResume() {

        goodToDraw = true;
        thread = new Thread(this);
        thread.start();

    }
}