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

    private Person person;
    private ImageView imageView;
    private int position;

    public AsyncImageDownloader(ImageView imageView, int position, Person p){
        this.imageView = imageView;
        this.position = position;
        this.person = p;
    }

    protected Bitmap doInBackground(String... urls){
        int first;
        int last;
        try {
            first = MainActivity.mLayoutManager.findFirstVisibleItemPosition();
            last = MainActivity.mLayoutManager.findLastVisibleItemPosition();
        } catch (NullPointerException e){
            return null;
        }
        if (!(position >= first && position <= last)){

            return null;
        } else if (person.getBitpic() != null){ //check to see if a cached version is available
                return person.getBitpic();
        }

        String url = person.getPicture();
        Bitmap mIcon11 = null;
        try {
            Log.d("OUTPUT","Downloading image for " + position);
            InputStream in = new java.net.URL(url).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) person.setBitpic(result);
        int first = MainActivity.mLayoutManager.findFirstVisibleItemPosition();
        int last = MainActivity.mLayoutManager.findLastVisibleItemPosition();
        if (position >= first && position <= last) {
            imageView.setImageBitmap(result);
            Log.d("OUTPUT","Setting image for " + position);
        }
    }
}
