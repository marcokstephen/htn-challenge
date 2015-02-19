package com.sm.htnchallengelist5.Downloaders;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.sm.htnchallengelist5.MainActivity;
import com.sm.htnchallengelist5.Person;
import com.sm.htnchallengelist5.RecycleViewAdapter;
import com.sm.htnchallengelist5.TeamMembers;

import java.io.InputStream;

/**
 * Created by stephen on 15-02-17.
 */
public class AsyncImageDownloader extends AsyncTask<String, Void, Bitmap> {

    private Person person;
    private ImageView imageView;
    private int position;
    private RecycleViewAdapter.OnClickMode mode;

    public AsyncImageDownloader(ImageView imageView, int position, Person p, RecycleViewAdapter.OnClickMode mode){
        this.imageView = imageView;
        this.position = position;
        this.person = p;
        this.mode = mode;
    }

    protected Bitmap doInBackground(String... urls){
        int first = 0;
        int last = 0;
        try {
            if (mode == RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER) {
                first = MainActivity.mLayoutManager.findFirstVisibleItemPosition();
                last = MainActivity.mLayoutManager.findLastVisibleItemPosition();
            } else if (mode == RecycleViewAdapter.OnClickMode.REMOVE_TEAM_MEMBER){
                first = TeamMembers.mLayoutManager.findFirstVisibleItemPosition();
                last = TeamMembers.mLayoutManager.findLastVisibleItemPosition();
            }
        } catch (NullPointerException e){
            return null;
        }
        if ((position < first-3 || position > last+3) && first != last){
            Log.d("DOINBACKGROUND","Position " + position + " is not showing, returning null. First = " + first + " and last = " + last);
            return null;
        } else if (person.getBitpic() != null){ //check to see if a cached version is available
                return person.getBitpic();
        } else { //no cached version is available, we need to download a new image
            String url = person.getPicture();
            Bitmap mIcon11 = null;
            try {
                Log.d("DOINBACKGROUND", "Downloading image for " + position);
                InputStream in = new java.net.URL(url).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return mIcon11;
        }
    }

    protected void onPostExecute(Bitmap result) {
        if (result != null) person.setBitpic(result);
        int first = 0;
        int last = 0;
        if (mode == RecycleViewAdapter.OnClickMode.ADD_TEAM_MEMBER) {
            first = MainActivity.mLayoutManager.findFirstVisibleItemPosition();
            last = MainActivity.mLayoutManager.findLastVisibleItemPosition();
        } else if (mode == RecycleViewAdapter.OnClickMode.REMOVE_TEAM_MEMBER){
            first = TeamMembers.mLayoutManager.findFirstVisibleItemPosition();
            last = TeamMembers.mLayoutManager.findLastVisibleItemPosition();
        }
        if (position >= first && position <= last) {
            imageView.setImageBitmap(result);
            Log.d("ONPOSTEXECUTE","Setting image for " + position);
        }
    }
}
