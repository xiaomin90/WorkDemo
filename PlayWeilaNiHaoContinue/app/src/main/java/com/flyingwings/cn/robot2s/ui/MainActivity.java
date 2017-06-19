package com.flyingwings.cn.robot2s.ui;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.flyingwings.cn.robot2s.R;

import java.io.IOException;


public class MainActivity extends AppCompatActivity {

    private   MediaPlayer mediaplayer = new MediaPlayer();
    private Button mButton1;
    private Button mButton2;
    public String TAG = "MainActivity";
    private boolean isplayer1 = false;
    private boolean isIsplayer2 = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        mButton1 = (Button) this.findViewById(R.id.button1);
        mButton2 = (Button) this.findViewById(R.id.button2);


        mButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isplayer1) {
                    isplayer1 = false;
                } else {
                    isplayer1 = true;
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(isplayer1) {
                            mButton1.setText("停止");
                        } else {
                            mButton1.setText("播放唤醒");
                        }
                    }
                });

                if(!isplayer1) {
                    mediaplayer.start();
                    mediaplayer.release();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playSoundTips("weilanihao.mp3");
                    }
                }).start();

            }
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isIsplayer2) {
                    isIsplayer2 = false;

                } else {
                    isIsplayer2 = true;
                }


                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                if(isIsplayer2) {
                    mButton2.setText("停止");
                } else {
                    mButton2.setText("播放跳舞");
                }
                    }});

                if(!isIsplayer2) {
                    mediaplayer.start();
                    mediaplayer.release();
                    return;
                }

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        playSoundTips("tiaowu.mp3");
                    }
                }).start();

            }
        });


        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaplayer.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void playSoundTips(String musicPath) {
        mediaplayer  = new MediaPlayer();
        Context context = this;
        try {
            AssetManager assMg = context.getAssets();
            AssetFileDescriptor fileDescriptor = assMg.openFd(musicPath);
            mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if(!isIsplayer2 || !isplayer1) {
                        Log.d(TAG,"停止1111");
                        mediaplayer.release();
                    }
                }
            });
            mediaplayer.setLooping(true);
            mediaplayer.setDataSource(fileDescriptor.getFileDescriptor(), fileDescriptor.getStartOffset(), fileDescriptor.getLength());
            mediaplayer.prepare();
            fileDescriptor.close();
        } catch (IllegalArgumentException | IllegalStateException | IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.toString());
            return;
        }
        if (mediaplayer == null) {
            return;
        }
        mediaplayer.start();
        Log.e(TAG, "开始播放 : " + musicPath);
        try {
            Thread.sleep(mediaplayer.getDuration() + 50);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
