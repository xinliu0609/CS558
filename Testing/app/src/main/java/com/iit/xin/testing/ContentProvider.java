package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by xin on 4/7/15.
 */
public class ContentProvider extends AsyncTask<String, Void, VideoStream>{

    protected Context context;

    public ContentProvider(Context con){
        context = con;
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        Log.d("tag0", "this is OnPreExecute");
    }

    /*
    @Override
    protected void onProgressUpdate(){
        super.onProgressUpdate();
    }
    */

    @Override
    protected void onPostExecute(VideoStream result){
        super.onPostExecute(result);
    }

    @Override
    protected VideoStream doInBackground(String... params) {

        Log.d("tag1", "this is do in background");

        VideoStream vs = null;

        int id = context.getResources().getIdentifier(params[0], "raw", context.getPackageName());

        if(id != 0){
            Log.d("tag", "the video already exists");
            vs = new VideoStreamFromLocal(id, context);
        }
        else{

            //if there is no such movie locally, ask server if it has it
            String serverIP = "104.194.118.179";
            int portNumber = 8888;
            InetAddress serverAddress;
            Socket socket;

            try {

                serverAddress = Inet4Address.getByName(serverIP);
                socket = new Socket(serverAddress, portNumber);
                Log.d("tag0", "connection established");

                OutputStream out = socket.getOutputStream();
                OutputStreamWriter outWriter = new OutputStreamWriter(out);
                PrintWriter pw = new PrintWriter(outWriter);
                pw.println(params[0]);
                outWriter.flush();

                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                Log.d("tag2", "respond from the server is "+message);

                if(message == "Yes"){
                    // set up another thread to receive the streams
                }else{
                    ;
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return vs;
    }

}
