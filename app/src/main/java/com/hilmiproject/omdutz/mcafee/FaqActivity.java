package com.hilmiproject.omdutz.mcafee;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.hilmiproject.omdutz.mcafee.Adapter.CostumMediaController;
import com.hilmiproject.omdutz.mcafee.Interface.VideoListener;
import com.hilmiproject.omdutz.mcafee.VolleyClass.DownloadVideo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FaqActivity extends AppCompatActivity {

    private VideoView videoView;
    private ProgressDialog progressDialog;
    public static int getLastMinute = 0;
    private MediaPlayer mediaPlayer;
    public static boolean isPlayedBefore = false;
    public List<String> pertanyaaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq2);
        videoView = (VideoView)findViewById(R.id.videoview);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
        listFaq();
        ArrayAdapter<String> jkAdapter = new ArrayAdapter<String>(this,
                R.layout.item_pertanyaan,R.id.text,pertanyaaList);
        ListView listView = (ListView)findViewById(R.id.listpertanyaan);
        listView.setAdapter(jkAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent is = new Intent(getApplicationContext(),FaqAnswer.class);
                is.putExtra("posisi",i);
                startActivity(is);
            }
        });
        if(getLastMinute == 0){
            ((ImageView)findViewById(R.id.playvideo)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ImageView)findViewById(R.id.playvideo)).setVisibility(View.GONE);
                    File fileVideo = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            +"/kimsfile/video/videomacfee.mp4");

                    if(fileVideo.exists() == false){
                        new DownloadVideo(FaqActivity.this, FaqActivity.this, new VideoListener() {
                            @Override
                            public void lakukan() {
                                playVideo();
                            }
                        });
                    }else{
                        playVideo();
                    }
                }
            });
        }else{
            ((ImageView)findViewById(R.id.playvideo)).setVisibility(View.GONE);
            playVideo();
        }

    }

    public void playVideo(){
        ((ProgressBar)findViewById(R.id.loadingvideo)).setVisibility(View.VISIBLE);
        try {
            // Start the MediaController
            CostumMediaController mediacontroller = new CostumMediaController(
                    FaqActivity.this);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT ,
                    ViewGroup.LayoutParams.WRAP_CONTENT);

            params.setMargins(5,115,5,5);
            // mediacontroller.setLayoutParams(params);
            mediacontroller.setManager(getWindowManager(),this);
            mediacontroller.setAnchorView(videoView);
            Uri uri = Uri.parse(Environment.getExternalStorageDirectory().getAbsolutePath()
                    +"/kimsfile/video/videomacfee.mp4");
            videoView.setMediaController(mediacontroller);
            if(getLastMinute > 0) videoView.seekTo(getLastMinute);
            videoView.setVideoURI(uri);

        }catch (Exception e){
            progressDialog.dismiss();
            //  Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        videoView.requestFocus();
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlay) {
                mediaPlayer = mediaPlay;
                ((ProgressBar)findViewById(R.id.loadingvideo)).setVisibility(View.GONE);
               // videoView.start();
                mediaPlay.start();
                mediaPlayer.setOnSeekCompleteListener(new MediaPlayer.OnSeekCompleteListener() {
                    @Override
                    public void onSeekComplete(MediaPlayer mediaPlayer) {
                        mediaPlayer.start();
                    }
                });

            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mediaPlayer, int i, int i1) {
                Log.d("error code video",String.valueOf(i));

                ((ProgressBar)findViewById(R.id.loadingvideo)).setVisibility(View.GONE);
                return false;
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        getLastMinute = videoView.getCurrentPosition();
        videoView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(getLastMinute > 0){
            videoView.seekTo(getLastMinute);
            videoView.start();
        }
        if(isPlayedBefore == true) fullscreen();
    }

    @Override
    public void onBackPressed() {
        if(isPlayedBefore == true){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            isPlayedBefore = false;
        }else{
            super.onBackPressed();
        }
    }

    public void fullscreen(){
        LinearLayout.LayoutParams layot = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        layot.weight =  0;
        getSupportActionBar().hide();
        videoView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ((RelativeLayout)findViewById(R.id.rel)).setLayoutParams(layot);
    }
    public void listFaq(){
        pertanyaaList = new ArrayList<>();
        pertanyaaList.add("Apa itu KIMS Members?");
        pertanyaaList.add("Bagaimana cara scan produk McAfee?");
        pertanyaaList.add("Bagaimana cara claim hadiah?");
        pertanyaaList.add("Apa penyebab status member menjadi tidak akftif?");
        pertanyaaList.add("Bagaimana cara mengaktifkan kembali virtual member saya?");
        pertanyaaList.add("Apa itu McAfee?");
        pertanyaaList.add("Bagaimana cara melakukan instalasi McAfee?");
        pertanyaaList.add("Bagaimana cara top up McAfee?");
        pertanyaaList.add("Bagaimana cara aktivasi McAfee secara online?");
        pertanyaaList.add("McAfee anda trouble installasi?");
        pertanyaaList.add("Anda perlu bantuan?");
    }


}
