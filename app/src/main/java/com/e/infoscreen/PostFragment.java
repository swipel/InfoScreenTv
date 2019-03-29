package com.e.infoscreen;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.e.infoscreen.Model.PostDTO;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class PostFragment extends Fragment {
    private TextView textViewHeadline;
    private TextView textViewBody;
    private ImageView picture;


    public PostFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_post, container, false);
        textViewHeadline = view.findViewById(R.id.txtHeadlineDisplay);
        textViewBody = view.findViewById(R.id.txtBodyDisplay);
        picture = view.findViewById(R.id.imageDisplay);
        PostDTO post = getArguments().getParcelable("post");
        if (post != null) {
            textViewHeadline.setText(post.headline);
            if (post.postType.name.equals("Text")) {
                textViewBody.setText(post.text);
                textViewBody.setVisibility(View.VISIBLE);
                picture.setVisibility(View.INVISIBLE);

            } else {
                byte[] decodedString = Base64.decode(post.picture,Base64.NO_WRAP);
                InputStream inputStream  = new ByteArrayInputStream(decodedString);
                Bitmap bitmap  = BitmapFactory.decodeStream(inputStream);
                picture.setImageBitmap(bitmap);
                picture.setVisibility(View.VISIBLE);
                textViewBody.setVisibility(View.INVISIBLE);



/*                Drawable myDrawable = getResources().getDrawable(R.drawable.ic_launcher_foreground);
                imageView.setImageDrawable(myDrawable);
                imageView.setVisibility(View.VISIBLE);
                textViewBody.setVisibility(View.INVISIBLE);*/
            }
        } else {
            textViewHeadline.setText("Loading");
        }
        return view;
    }
}
