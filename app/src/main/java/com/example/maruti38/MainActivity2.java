package com.example.maruti38;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.maruti38.databinding.ActivityMain2Binding;

public class MainActivity2 extends AppCompatActivity {

    ActivityMain2Binding binding;
    TextView videoNameTv, videoTimeTv;
    ImageButton backIB,forwardiB,playPauseIB;
   VideoView videoView;

   RelativeLayout controlRl,videoRl;
   Boolean isopen=true;
    SeekBar videoSeekBar;
    String videoName ,videoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        videoName=getIntent().getStringExtra("videoname");
        videoPath=getIntent().getStringExtra("videoPath");


        videoNameTv=findViewById(R.id.videoTitle);
        videoTimeTv=findViewById(R.id.idTvtime);
        backIB=findViewById(R.id.idbcak);
        playPauseIB=findViewById(R.id.idplay);
        forwardiB=findViewById(R.id.idforward);
        videoSeekBar=findViewById(R.id.idseekbarprogress);
        videoView=findViewById(R.id.idvideoview);
        controlRl=findViewById(R.id.idRLcontrol);
        videoRl=findViewById(R.id.idRLvideo);


videoView.setVideoURI(Uri.parse(videoPath));



setHandler();
initialiseSeekBar();
videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
    @Override
    public void onPrepared(MediaPlayer mp) {
        videoSeekBar.setProgress(videoView.getDuration() );
        videoView.start();

    }
});

videoNameTv.setText(videoName);
backIB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        videoView.seekTo(videoView.getDuration()-10000);
    }
});

forwardiB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        videoView.seekTo(videoView.getDuration()+10000);
    }
});
playPauseIB.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if (videoView.isPlaying()){
            videoView.pause();
            playPauseIB.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_pause_circle_outline_24));

        }else {
            videoView.start();
            playPauseIB.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_play_circle_outline_24));
        }
    }


});



videoRl.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (isopen) {
        hideControl();
        isopen=false;
        }else {
            showcontrol();
            isopen=true;
        }
    }
});


       // binding.idplay
    }

public  void  hideControl(){


    controlRl.setVisibility(View.GONE);
    Window window=this.getWindow();
    if (window ==null){
        return;
    }
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

    View decorView=window.getDecorView();
    if (decorView != null){
        int viOption =decorView.getWindowSystemUiVisibility();

        if (Build.VERSION.SDK_INT  > 14 ){
            viOption |=  View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        }

        if (Build.VERSION.SDK_INT  > 14 ){
            viOption  |=  View.SYSTEM_UI_FLAG_LOW_PROFILE;

        }
        if (Build.VERSION.SDK_INT  > 19 ){
            viOption   |=  View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        }

        decorView.setSystemUiVisibility(viOption);
    }



}
public  void  showcontrol(){
        controlRl.setVisibility(View.VISIBLE);
    Window window=this.getWindow();
    if (window ==null){
        return;
    }
    window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

    View decorView=window.getDecorView();
    if (decorView != null){
        int viOption=decorView.getWindowSystemUiVisibility();

        if (Build.VERSION.SDK_INT  > 14 ){
            viOption=View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;

        }

        if (Build.VERSION.SDK_INT  > 14 ){
            viOption=View.SYSTEM_UI_FLAG_LOW_PROFILE;

        }
        if (Build.VERSION.SDK_INT  > 19 ){
            viOption=View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;

        }

        decorView.setSystemUiVisibility(viOption);
    }
}
public  void  initialiseSeekBar(){


        videoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (videoSeekBar.getId()  ==R.id.idseekbarprogress){
                    if (fromUser){
                        videoView.seekTo(progress);
                        videoView.start();
                        int curPos=videoView.getCurrentPosition();
                        videoTimeTv.setText(""+ConvertTime(videoView.getDuration()-curPos));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
}

public  void  setHandler(){
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {

            if (videoView.getDuration() > 0){
                int currPos=videoView.getCurrentPosition();
                videoSeekBar.setProgress(currPos);
                videoTimeTv.setText(""+ConvertTime(videoView.getDuration()-currPos));
            }

            handler.postDelayed(this, 0);
        }
    };

    handler.postDelayed(runnable, 500);
}
public  String  ConvertTime(int ms){

       String time;
       int x,secound,minute,hour;
       x=ms/1000;
       secound=x%60;
       x /=60;
       minute=x%60;
       x/=60;
       hour=x%24;
       if (hour !=0){
           time=String.format("%02d", hour)+" : "+String.format("%02d", minute) +" : "+String.format("%02d", secound);


       }else {
         time=  String.format("%02d", minute) +" : "+String.format("%02d", secound);
       }

        return  time ;
}

}



