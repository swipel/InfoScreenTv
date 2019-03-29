package com.e.infoscreen;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.e.infoscreen.Model.PostDTO;

public class PostFragmentAdapter extends FragmentStatePagerAdapter {
    private PostDTO[] posts;
    private int count = 1;

    public PostFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        PostFragment postFragment = new PostFragment();

        Bundle bundle = new Bundle();
        if (posts != null) {
            bundle.putParcelable("post", posts[position]);
        }
        postFragment.setArguments(bundle);
        return postFragment;
    }

    //Recreate all frament when notifyDataSetChanged is called
    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    //Called from from to update posts value
    public void updatePost(PostDTO[] posts){
        this.posts = posts;
        setCount(posts.length);
    }

    //TODO UPTIMIZE THIS no reason to update count?
    //Updating amount of pages
    public void setCount(int count){
        this.count = count;
    }

    //Get current amount of pages
    @Override
    public int getCount() {
        return count;
    }
}
