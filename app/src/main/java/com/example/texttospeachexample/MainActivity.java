package com.example.texttospeachexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private ArrayList<String> data;
    private RecyclerView mRecyclerView;
    private MainAdapter mMainAdapter;
    private TextToSpeech tts;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = findViewById(R.id.recyclerView);
        mMainAdapter = new MainAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setAdapter(mMainAdapter);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        initTts();

        mRecyclerView.setOnTouchListener(new OnSwipeTouchListener(this) {

            public void onSwipeTop() {
                Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeRight() {
                Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeLeft() {
                Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            public void onSwipeBottom() {
                Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
    }

    private void initData() {
        data = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            String text = "2 X " + i + " = " + i * 2;
            data.add(text);
        }
    }

    private void initTts() {
        tts = new TextToSpeech(this, this);
        tts.setLanguage(Locale.US);
        tts.setOnUtteranceProgressListener(new MyListener());
    }

    @Override
    public void onInit(int status) {
        playSound(0);
    }

    private void playSound(int index) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, String.valueOf(index));
        tts.speak(data.get(index), TextToSpeech.QUEUE_ADD, hashMap);
    }

    class MyListener extends UtteranceProgressListener {
        @Override
        public void onStart(String utteranceId) {
            System.out.println("onStart");
            int currentIndex = Integer.parseInt(utteranceId);
            mMainAdapter.setCurrentPosition(currentIndex);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainAdapter.notifyDataSetChanged();
                }
            });
        }

        @Override
        public void onDone(String utteranceId) {
            System.out.println("onDone");
            int currentIndex = Integer.parseInt(utteranceId);
            mMainAdapter.setCurrentPosition(-1);
            handler.post(new Runnable() {
                @Override
                public void run() {
                    mMainAdapter.notifyDataSetChanged();
                }
            });
            if (currentIndex < data.size() - 1) {
                playSound(currentIndex + 1);
            }
        }

        @Override
        public void onError(String utteranceId) {
            System.out.println("onError");
        }
    }
}
