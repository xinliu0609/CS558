package com.iit.xin.broadcastimagetest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * Created by xin on 4/13/15.
 */
public class Multicast {

    String ip = "224.0.0.251";
    String user = "spondob-tab";
    int port = 17012;

    InetAddress group;
    MulticastSocket s;

    boolean flag = false;

    public boolean getFlag(){
        return flag;
    }

    public Multicast() throws UnknownHostException {
        try {
            group = InetAddress.getByName(ip);
            s = new MulticastSocket(port);
            //s.setTimeToLive(2);
            s.setReuseAddress(true);
            //s.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            s.joinGroup(group);
            Log.d("tag5", "constructor succeed------------------------------------------");
            flag = true;
        } catch (IOException e) {
            Log.d("tag2", "error Constructor---------------------------------------------");
            System.out.println(e.toString());
        }
    }

    public Bitmap receive()
    {
        Bitmap image = null;
        Thread thread = new Thread()
        {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[10000];
                    DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                    while (true) {
                        s.receive(datagramPacket);
                        Bitmap image = BitmapFactory.decodeByteArray(datagramPacket.getData(), 0, datagramPacket.getLength());
                        Log.d("tag1", "received something----------------------------------------");
                        sleep(2000);
                        //Toast.makeText(MainActivity.this, msgRcv, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.d("tag3", "error Receive()--------------------------------------------");
                    e.printStackTrace();
                }
            }
        };
        thread.start();
        return image;
    }
}
