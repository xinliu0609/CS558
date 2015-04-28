package com.iit.xin.testing.shared;

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
public class ContactServer extends AsyncTask<String, Void, String[]>{

    protected Context context;
    protected InetAddress address;
    protected final int portNumber = 8888;

    public ContactServer(Context con, InetAddress addr){
        context = con;
        address = addr;
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
    protected void onPostExecute(String[] result){
        super.onPostExecute(result);
    }

    @Override
    protected String[] doInBackground(String... params) {

        Log.d("tag1", "this is do in background");
        String[] strArray = new String[2];

        Socket socket;

        try {
            socket = new Socket(address, portNumber);
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

            if(message.equals("Yes")){
                strArray[0] = "Yes";
                // set up another thread to receive the streams
            }else if(message.equals("No")){
                strArray[0] = "No";
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strArray;
    }

}
