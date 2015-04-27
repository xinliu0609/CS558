package com.example.chaitanya.uinterface;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {
    final Context context = this;
    private Button button;
    String vidname="";
    String serverip="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater li = LayoutInflater.from(context);
                View promptsView = li.inflate(R.layout.dialogbox,null);
                AlertDialog.Builder ab = new AlertDialog.Builder(context);
                ab.setView(promptsView);
                final EditText ui = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
                ab
                        .setCancelable(false)
                        .setPositiveButton("OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // get user input and set it to result
                                        //final TextView result = new TextView(Groups_Reg.this);
                                        //result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                                        vidname = ui.getText().toString();

                                        /*To check if movie exists in cache*/
                                        if(vidname.contentEquals("movie"))
                                        {
                                            Toast.makeText(context,"You entered "+vidname,Toast.LENGTH_LONG).show();
                                            /* Play the video in cache */
                                        }
                                        else
                                        {
                                            Toast.makeText(context,"Video does not exist",Toast.LENGTH_LONG).show();
                                            LayoutInflater ll = LayoutInflater.from(context);
                                            View promptsView1 = ll.inflate(R.layout.serverdialog,null);
                                            AlertDialog.Builder aa = new AlertDialog.Builder(context);
                                            aa.setView(promptsView1);
                                            final EditText ipadd = (EditText) promptsView1.findViewById(R.id.ipaddr);
                                            aa
                                                    .setCancelable(false)
                                                    .setPositiveButton("OK",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    // get user input and set it to result
                                                                    //final TextView result = new TextView(Groups_Reg.this);
                                                                    //result.setTextSize(TypedValue.COMPLEX_UNIT_SP, 32);
                                                                    serverip = ipadd.getText().toString();
                                                                    Toast.makeText(context,"ipaddr "+serverip+""+vidname,Toast.LENGTH_LONG).show();
                                                                    /*Connect to the server and request the movie to be played
                                                                    * If movie does not exist use toast message to display movie does not exist
                                                                    * You can also use sleep here to make it wait*/

                                                                }
                                                            })
                                                    .setNegativeButton("Cancel",
                                                            new DialogInterface.OnClickListener() {
                                                                public void onClick(DialogInterface dialog, int id) {
                                                                    dialog.cancel();
                                                                }
                                                            });
                                            AlertDialog ae = aa.create();
                                            ae.show();
                                        }
                                    }
                                })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                AlertDialog ad = ab.create();
                ad.show();
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
}
