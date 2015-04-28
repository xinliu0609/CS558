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

    String requestOne;

    InetAddress group;
    MulticastSocket s;
    WifiManager.MulticastLock lock;
    Bitmap image = null;

    public RemoteStreaming(Context c, String s, BlockingQueue<Bitmap> frameQueue, String string) {
        context = c;
        broadCastAddr = s;
        blockingQueue = frameQueue;
        requestOne = string;

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


        VideoStreamFromLocal vsl;

        if(requestOne.equals("forward")){
            vsl = new VideoStreamFromLocal("backward", context); //what i have
        }else{
            vsl = new VideoStreamFromLocal("forward", context); //what i have
        }

        int currentCacheFrame = 0;
        int framelength;
        int counter;

        while (true) {

            byte[] buffer = new byte[15000];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            try{
                s.receive(packet);

                byte[] data = packet.getData();

                IndexPacket indexPacket = new IndexPacket(data);

                if(requestOne.equals("forward")){
                    framelength = indexPacket.frameLength[0];   //what i'm going to use
                }else{
                    framelength = indexPacket.frameLength[1];
                }

                int framelength1 = indexPacket.frameLength[0];
                int framelength2 = indexPacket.frameLength[1];

                if(requestOne.equals("forward")){
                    counter = indexPacket.byteCount[1];
                }else{
                    counter = indexPacket.byteCount[0];
                }

                int counter1 = indexPacket.byteCount[0];
                int counter2 = indexPacket.byteCount[1];
                byte[] mixed = indexPacket.payload;


                /*
                Log.d("tag0", "framelength1 is "+framelength1);
                Log.d("tag1", "framelength2 is "+framelength2);

                Log.d("tag2", "byte count 1 is "+counter1);
                Log.d("tag2", "byte count 2 is "+counter2);
                */

                /*

                byte[] cache = vsl.getNextByteArray();

                byte[] trueByte = mix(mixed, cache);

                image = BitmapFactory.decodeByteArray(trueByte, 0, framelength1);
                blockingQueue.put(image);

                */

                while(currentCacheFrame < counter){
                    vsl.getNextByteArray();
                    currentCacheFrame++;
                }

                if(currentCacheFrame == counter){
                    byte[] cache = vsl.getNextByteArray();
                    byte[] trueByte = mix(mixed, cache);
                    image = BitmapFactory.decodeByteArray(trueByte, 0, framelength);
                    blockingQueue.put(image);
                    currentCacheFrame++;
                }

                //else, do nothing

                Thread.sleep(40);

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
