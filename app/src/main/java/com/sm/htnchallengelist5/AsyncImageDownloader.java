package com.sm.htnchallengelist5;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

/**
 * Created by stephen on 15-02-17.
 */
public class AsyncImageDownloader extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private int position;

    public AsyncImageDownloader(ImageView imageView, int position){
        this.imageView = imageView;
        this.position = position;
    }

    protected Bitmap doInBackground(String... urls){
        String url = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        imageView.setImageBitmap(result);
    }
}
