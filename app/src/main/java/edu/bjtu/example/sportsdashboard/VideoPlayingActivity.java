package edu.bjtu.example.sportsdashboard;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;


public class VideoPlayingActivity extends AppCompatActivity {

    MyVideoView videoView;
    String uri = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
    private MediaController mediaController;
    private Button start_btn;
    private Button pause_btn;
    private Button replay_btn;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_video_detail);
        initView();

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Log.i("tag", "准备完毕,可以播放了");
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Log.i("tag", "播放完毕");
            }
        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                Log.i("tag", "播放失败");
                return false;
            }
        });

    }

    private void initView() {
        videoView = (MyVideoView) findViewById(R.id.surface);
//        start_btn = (Button) findViewById(R.id.start_btn);
//        pause_btn = (Button) findViewById(R.id.pause_btn);
//        replay_btn = (Button)findViewById(R.id.replay_btn);

        init();

//        start_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                init();
//            }
//        });
//
//        pause_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                videoView.pause();
//            }
//        });
//
//        replay_btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                videoView.pause();
//                videoView.stopPlayback();
//                videoView.setVideoURI(Uri.parse(uri));
//                videoView.start();
//            }
//        });

    }

    private void init() {
        videoView = (MyVideoView) findViewById(R.id.surface);
        mediaController = new MediaController(this);
        //String uri = "android.resource://" + getPackageName() + "/" + R.raw.aas;

        videoView.setVideoURI(Uri.parse(uri));
        videoView.setMediaController(mediaController);
        mediaController.setMediaPlayer(videoView);
        videoView.requestFocus();
        videoView.start();
    }
}

class MyVideoView extends VideoView{
    public MyVideoView(Context context){
        super(context);
    }

    public MyVideoView(Context context, AttributeSet attributeSet){
        super(context,attributeSet);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = getDefaultSize(getWidth(), widthMeasureSpec);//根据布局的高度来定
        int height = getDefaultSize(getHeight(), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

}
