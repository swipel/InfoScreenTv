package com.e.infoscreen;

import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.e.infoscreen.Model.PostDTO;

public class MainActivity extends AppCompatActivity implements InfoPresenter.View {
    InfoPresenter presenter = new InfoPresenter(this);
    private ViewPager viewPager;
    private PostFragmentAdapter adapter;
    private int currentPage = 0;
    private final Handler handler = new Handler();
    private PostDTO[] posts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        viewPager = findViewById(R.id.mainPage);
        adapter = new PostFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        automateSlider();
    }

    public void onPostUpdate(PostDTO[] posts) {
        this.posts = posts;
        adapter.updatePost(posts);
        adapter.notifyDataSetChanged();
    }

    private void automateSlider() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                currentPage++;
                if (posts != null) {
                    if (posts.length < currentPage) {
                        currentPage = 0;
                    }
                }
                viewPager.setCurrentItem(currentPage);
                handler.postDelayed(this, 5000);
            }
        }, 5000);
    }
}
