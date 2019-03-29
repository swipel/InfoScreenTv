package com.e.infoscreen;


import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import com.e.infoscreen.Model.IntervalDTO;
import com.e.infoscreen.Model.PostDTO;
import com.e.infoscreen.Http.HttpAsyncTaskGet;
import com.e.infoscreen.Http.TaskCompleted;

import java.util.ArrayList;

public class InfoPresenter implements TaskCompleted {
    public interface View {
        void onPostUpdate(PostDTO[] posts);
    }

    private View v;
    private AsyncTask intervalTask;
    private AsyncTask pageTask;
    private int interval;
    private final Handler handler = new Handler();

    public InfoPresenter(View v) {
        this.v = v;
        interval = 10000;
        requestDataFromHttp();
    }

    //When a task is completede it call this method
    public void onTaskCompleted(Object obj) {
        if (obj != null) {
            if (obj.getClass().getComponentType() == PostDTO.class) {

                ArrayList<PostDTO> temp = new ArrayList<>();
                for (PostDTO row : (PostDTO[]) obj) {
                    if (row.featured) {
                        temp.add(row);
                    }
                }

                PostDTO[] postArray = temp.toArray(new PostDTO[temp.size()]);

                onPostUpdate(postArray);
            } else if (obj instanceof IntervalDTO) {
                updateInterval((IntervalDTO) obj);
            } else {
                Log.e("PostType", "false");
            }
        }
    }

    //Create handle and start runnabl start async task and check if they running
    public void requestDataFromHttp() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (pageTask == null || pageTask.getStatus() == AsyncTask.Status.FINISHED) {
                    pageTask = new HttpAsyncTaskGet(getInstance(), new PostDTO[0], "post").execute();
                }
                if (intervalTask == null || intervalTask.getStatus() == AsyncTask.Status.FINISHED) {
                    intervalTask = new HttpAsyncTaskGet(getInstance(), new IntervalDTO(), "interval").execute();
                }

                handler.postDelayed(this, interval);
            }
        }, interval);
    }

    private void updateInterval(IntervalDTO interval) {
        //Multiply by 1000 from milliseconds to seconds
        this.interval = interval.interval * 1000;
    }

    private TaskCompleted getInstance() {
        return this;
    }

    public void onPostUpdate(PostDTO[] posts) {
        v.onPostUpdate(posts);
    }
}
