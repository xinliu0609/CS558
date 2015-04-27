package com.iit.xin.testing;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity{

    final Context context = this;
    MyView myView;

    String serverip="";
    String vidname="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("tag0", "button clicked");
                showDialog(context);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onPause(){
        super.onPause();
        myView.onPause();
    }

    public void onResume(){
        super.onResume();
        //v.onResume();
    }


    public void showDialog(final Context context){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Video name");
        dialog.setMessage("Please input the name of the video");
        dialog.setCancelable(false);

        final EditText input = new EditText(context);
        dialog.setView(input);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                String value = input.getText().toString();
                int id = context.getResources().getIdentifier(value, "raw", context.getPackageName());
                if(id != 0){
                    playFromLocal(id);
                }else{
                    //ask for ip address of the server
                    askForAddress(value);
                }
            }
        });

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void playFromLocal(int id){

        BlockingQueue<Bitmap> frameQueue = new ArrayBlockingQueue<Bitmap>(10);

        Thread t1 = new Thread(new LocalStreaming(context, id, frameQueue));
        myView = new MyView(context, frameQueue);

        t1.start();

        setContentView(myView);
        myView.onResume();
    }

    public void askForAddress(String value){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        dialog.setTitle("Server IP");
        dialog.setMessage("Please input the IP address of the server");
        dialog.setCancelable(false);

        final EditText input = new EditText(context);
        dialog.setView(input);

        dialog.setPositiveButton("OK", new askForAddressOKListener(value, input));

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public class askForAddressOKListener implements DialogInterface.OnClickListener {

        protected String name;
        protected EditText input;

        public askForAddressOKListener(String value, EditText editText){
            name = value;
            input = editText;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {

            InetAddress address = null;
            String value = input.getText().toString();

            try {
                address = InetAddress.getByName(value);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }

            // input string array: { requestingMovie, grantedMovie1, grantedMovie2, ...}
            // result string array: { "Yes/No", broadcast address }

            String[] inputStringArray = {name};
            String[] resultStringArray = null;

            try {
                resultStringArray = new ContactServer(context, address).execute(inputStringArray).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            if(resultStringArray != null){

                if(resultStringArray[0].equals("Yes")){
                    //Log.d("tag", "Server has it !!!!!!!!!");
                    Toast.makeText(context,"Video "+name+" exists on server side",Toast.LENGTH_LONG).show();

                    // start a new thread
                }else{
                    // it's all over!
                    //Log.d("tag1", "Server doesn't have it !!!!1");
                    Toast.makeText(context,"Video "+name+" doesn't exists on server side",Toast.LENGTH_LONG).show();
                    dialog.dismiss();
                }
            }
        }
    }
}
