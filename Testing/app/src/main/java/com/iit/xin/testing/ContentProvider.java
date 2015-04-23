package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xin on 4/7/15.
 */
public class ContentProvider extends AsyncTask{

    protected Context context;

    public ContentProvider(Context con){
        context = con;
    }

    public VideoStream getVideoStream(String videoName) {

        VideoStream vs = null;

        int id = context.getResources().getIdentifier(videoName, "raw", context.getPackageName());

        if(id != 0){
            Log.d("tag", "the video already exists");
            vs = new VideoStreamFromLocal(id, context);
        }
        else{   // if there is no such movie locally, ask server if it has it

            //if the server responded, create a VideoStream from server and return to surfaceView
            String msg = "Do you have movie"+videoName;
            String serverIP = "216.47.157.49";
            int portNumber = 8888;
            InetAddress serverAddress;
            Socket socket;

            try {
                serverAddress = Inet4Address.getByName(serverIP);
                socket = new Socket(serverAddress, portNumber);
                Log.d("tag0", "connection established");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


        }

        return vs;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        return null;
    }
}
