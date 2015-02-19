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

/*
    This class called by RecyclerViewAdapter to download the images for each Person's
    view in the RecyclerView.
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

    /*
        This method downloads the image and returns it as a Bitmap.

        We perform some checks to ensure that the image actually needs to be downloaded.

        1. Check if the image is appearing on the RecyclerView. For example if we scroll to the 500th
        element, only download the 500th (and surrounding) elements rather than images 0-500.

        2. Check if we have already downloaded the image. When a download is successful, we
        cache the image so that it does not need to be downloaded in the future.

        If those checks deem it necessary, we start the download of the image.
     */
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
            //The position is not showing on the recyclerView. We add a buffer of 3 views
            //on either side of what is currently visible.
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

    /*
        After the image is finished downloading, we check to see if the view is currently
        being displayed on the RecyclerView. If the view is showing, we update the view by
        adding in the newly downloaded image. If the view is not showing, we do nothing.

        This prevents images from seemingly randomly changing due to Android reusing views.
     */
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
