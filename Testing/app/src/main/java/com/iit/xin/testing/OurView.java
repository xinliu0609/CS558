package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * Created by xin on 4/5/15.
 */
public class OurView extends SurfaceView implements Runnable{

    Thread thread = null;
    SurfaceHolder holder;
    boolean goodToDraw = false;

    private Bitmap bitmap;

    //private byte[] bytes = new byte[10000];
    //private int imageLength = 0;

    private VideoStream vs;

    public OurView(Context context, VideoStream videoStream){
        super(context);
        holder = getHolder();
        vs = videoStream;
    }


    @Override
    public void run() {

        while(goodToDraw){

            // perform canvas drawing
            if(!holder.getSurface().isValid()){
                continue;
            }

            Canvas canvas = holder.lockCanvas();

            try {
                bitmap = vs.getNextBitmap();
            } catch (IOException e) {
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

    public void onPause(){

        goodToDraw = false;
        while(true){
            try{
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            break;
        }
        thread = null;
    }

    public void onResume(){

        goodToDraw = true;
        thread = new Thread(this);
        thread.start();

    }
}