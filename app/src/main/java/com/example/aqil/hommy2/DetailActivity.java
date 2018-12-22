package com.example.aqil.hommy2;


import android.content.Intent;
import android.os.Bundle;
import android.app.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.aqil.hommy2.Entity.TrashEntitiy;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class DetailActivity extends YouTubeBaseActivity {
    TextView detailTitle, detailCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        detailTitle = findViewById(R.id.detail_name);
        detailCategory = findViewById(R.id.detail_category);
        Intent intent = getIntent();
        final TrashEntitiy trashEntitiy = intent.getParcelableExtra("EXTRA");
        String imagePath = trashEntitiy.getImagePath();
        String thubmPath = trashEntitiy.getThumbPath();
        String namePath = trashEntitiy.getNamePath();
        detailTitle.setText(trashEntitiy.getHomeTitle());
        int categoryno = trashEntitiy.getCategoryno();
        detailCategory.setText("House Category: "+String.valueOf(trashEntitiy.getCategoryno()));

        ImageView imageView = findViewById(R.id.img_backdrop);
        Glide.with(this).load(imagePath).apply(new RequestOptions().centerCrop()).into(imageView);
        final YouTubePlayerView youtubePlayerView = findViewById(R.id.youtubePlayerView);
        youtubePlayerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playVideo(trashEntitiy.getVideoPath(), youtubePlayerView);
            }
        });
        findViewById(R.id.basic_license).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                Fragment prev = getFragmentManager().findFragmentByTag("dialog");
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);
                DialogFragment dialogFragment = new BlankFragment();
                dialogFragment.show(ft,"TAG");

            }
        });
    }

    public void playVideo(final String videoId, YouTubePlayerView youTubePlayerView) {
        //initialize youtube player view
        youTubePlayerView.initialize("AIzaSyAU9kvtAPNLCC8RWr9wyTIG0nybEtjD4l4",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(videoId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });
    }
}