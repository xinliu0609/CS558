package com.iit.xin.button_test;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Created by xin on 4/26/15.
 */
public class ButtonListener implements View.OnClickListener {

    protected Context context;

    public ButtonListener(Context c){
        context = c;
    }
    @Override
    public void onClick(View v) {

        Log.d("tag0", "clicked");


    }
}
