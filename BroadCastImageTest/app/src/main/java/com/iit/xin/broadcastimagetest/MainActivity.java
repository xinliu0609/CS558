package com.iit.xin.broadcastimagetest;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiManager.MulticastLock;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity {
    String ip = "224.0.0.251";
    String user = "spondob-tab";
    int port = 17012;

    InetAddress group;
    MulticastSocket s;
    MulticastLock lock;

    Bitmap image = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        lock = wifi.createMulticastLock("MainActivity");
        lock.setReferenceCounted(true);
        lock.acquire();

        try {
            group = InetAddress.getByName(ip);
            s = new MulticastSocket(port);
            //s.setTimeToLive(2);
            s.setReuseAddress(true);
            //s.setNetworkInterface(NetworkInterface.getByName("wlan0"));
            s.joinGroup(group);
        } catch (IOException e) {
            System.out.println(e.toString());
        }

        receive();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onDestroy() {
        if (lock != null) {
            lock.release();
            lock = null;
        }
    }

    public void receive() {

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    byte[] buffer = new byte[10000];
                    DatagramPacket data = new DatagramPacket(buffer, buffer.length);
                    while (true) {
                        s.receive(data);
                        image = BitmapFactory.decodeByteArray(data.getData(), 0, data.getLength());

                        while(image == null){
                            sleep(1000);
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

                        runOnUiThread(new Runnable() {
                            public void run() {
                                ImageView view = (ImageView)findViewById(R.id.imageview1);
                                view.setImageBitmap(image);
                                //setContentView(view);
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }
}