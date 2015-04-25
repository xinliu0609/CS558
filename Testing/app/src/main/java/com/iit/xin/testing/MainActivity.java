package com.iit.xin.testing;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.util.concurrent.ExecutionException;


public class MainActivity extends ActionBarActivity{

    private OurView v;
    private ContentProvider cp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cp = new ContentProvider(this);
        String[] str = {"result"};

        VideoStream vs = null;

        try {
            vs = cp.execute(str).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if(vs == null){
            // it's done, tell the user there's nothing we can do
        }

        v = new OurView(this, vs);
        setContentView(v);

        v.onResume();
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
        v.onPause();
    }

    public void onResume(){
        super.onResume();
        v.onResume();
    }

}
