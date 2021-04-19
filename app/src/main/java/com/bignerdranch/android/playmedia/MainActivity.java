package com.bignerdranch.android.playmedia;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Button playButton;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playButton = findViewById(R.id.play_button);
        seekBar = findViewById(R.id.seek_bar_id);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource("https://buildappswithpaulo.com/music/watch_me.mp3");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.party);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                int duration = mp.getDuration();
                Toast.makeText(MainActivity.this, String.valueOf((duration/1000)/60), Toast.LENGTH_LONG).show();
            }
        });
        MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(final MediaPlayer mp) {
                seekBar.setMax(mp.getDuration());
                playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mp.isPlaying()) {
                            mp.pause();
                            playButton.setText(R.string.play_text);
                        } else {
                            mp.start();
                            playButton.setText(R.string.pause_text);
                        }
                    }
                });
            }
        };
        mediaPlayer.setOnPreparedListener(preparedListener);
        mediaPlayer.prepareAsync();

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser) {
                    mediaPlayer.seekTo(progress);
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

 /*   public void pauseMusic() {
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            playButton.setText(R.string.play_text);
        }
    }
    public void playMusic() {
       if(mediaPlayer != null) {
           mediaPlayer.pause();
           playButton.setText(R.string.pause_text);
       }
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer != null) {
            mediaPlayer.pause();
            mediaPlayer.release();
        }
    }
}