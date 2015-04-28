package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import com.iit.xin.testing.IndexCodingPacket;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
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
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);


        while (true) {

            try{
                s.receive(packet);

                while(packet == null){
                    Thread.sleep(20);
                    s.receive(packet);
                }

                byte[] data = packet.getData();
                packet = null;

                /*
                IndexPacket indexPacket = new IndexPacket(packet.getData());
                packet = null;

                int framelength1 = indexPacket.length_1;
                int framelength2 = indexPacket.length_2;
                byte[] mixed = indexPacket.payload;
                byte[] side = indexPacket.side;


                Log.d("tag0", "framelength1 is "+framelength1);
                Log.d("tag1", "framelength2 is "+framelength2);
                */


                /*
                VideoStreamFromLocal vsl = new VideoStreamFromLocal("backward", context);
                byte[] cache = vsl.getNextByteArray();
                */


                //image = BitmapFactory.decodeByteArray(trueByte, 0, framelength1);
                //blockingQueue.put(image);


            } catch (IOException e) {
                e.printStackTrace();
            }

            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return is.readObject();
    }

    public static byte[] mix(byte[] b1, byte[] b2){

        byte[] mixed = new byte[b1.length];

        int i = 0;
        for(byte b : b1){
            mixed[i] = (byte)(0xff & (int)b ^ (int)b2[i++]);
        }
        return mixed;
    }
}
