package com.iit.xin.testing;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import java.io.IOException;

/**
 * Created by xin on 4/7/15.
 */
public class ContentProvider {

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
        else{
            ;
        }

        return vs;
    }
}
