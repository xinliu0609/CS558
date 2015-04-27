package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.concurrent.BlockingQueue;

/**
 * Created by xin on 4/27/15.
 */
public class RemoteStreaming implements Runnable{

    Context context;
    String broadCastAddr;
    String user = "spondob-tab";
    final int PORT = 17012;
    BlockingQueue<Bitmap> blockingQueue;

    InetAddress group;
    MulticastSocket s;
    WifiManager.MulticastLock lock;
    Bitmap image = null;

    public RemoteStreaming(Context c, String s, BlockingQueue<Bitmap> frameQueue) {
        context = c;
        broadCastAddr = s;
        blockingQueue = frameQueue;

        WifiManager wifi = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("MainActivity");
        lock.setReferenceCounted(true);
        lock.acquire();

    }

    @Override
    public void run() {

        try {
            group = InetAddress.getByName(broadCastAddr);
            s = new MulticastSocket(PORT);
            s.setReuseAddress(true);
            s.joinGroup(group);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        byte[] buffer = new byte[15000];
        DatagramPacket data = new DatagramPacket(buffer, buffer.length);


        while (true) {

            try{
                s.receive(data);
                image = BitmapFactory.decodeByteArray(data.getData(), 0, data.getLength());

                while(image == null){
                    Thread.sleep(40);
                    s.receive(data);
                    image = BitmapFactory.decodeByteArray(data.getData(), 0, data.getLength());
                }

                Log.d("tag1", "Get out of the while loop-----------");
                if(image == null){
                    Log.d("tag2", "the image is still NULL---------------");
                }
                else{
                    Log.d("tag3", "the image is NOT NULL");
                }

                blockingQueue.put(image);
                Log.d("tag0", "put the image to the blocking queue");

            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
