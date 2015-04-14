package com.iit.xin.testing;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by xin on 4/3/15.
 */
public abstract class VideoStream {
    protected InputStream sourceInputStream;
    abstract public Bitmap getNextBitmap() throws IOException;
}
