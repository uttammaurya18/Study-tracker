package com.nexus.studytracker;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;

public class MainActivity extends Activity {
    EditText subjectInput;
    Button addBtn, startStopBtn;
    TextView timerText;
    ListView listView;
    ArrayList<String> subjects = new ArrayList<>();
    ArrayAdapter<String> adapter;
    
    Handler handler = new Handler();
    int seconds = 0;
    boolean running = false;
    
    Runnable runnable = new Runnable() {
        public void run() {
            int hrs = seconds / 3600;
            int mins = (seconds % 3600) / 60;
            int secs = seconds % 60;
            timerText.setText(String.format("%02d:%02d:%02d", hrs, mins, secs));
            if(running) {
                seconds++;
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40,40,40,40);
        
        TextView title = new TextView(this);
        title.setText("Study Tracker");
        title.setTextSize(28);
        layout.addView(title);
        
        timerText = new TextView(this);
        timerText.setText("00:00:00");
        timerText.setTextSize(48);
        layout.addView(timerText);
        
        startStopBtn = new Button(this);
        startStopBtn.setText("Start");
        layout.addView(startStopBtn);
        
        subjectInput = new EditText(this);
        subjectInput.setHint("Subject ka naam likho");
        layout.addView(subjectInput);
        
        addBtn = new Button(this);
        addBtn.setText("Add Subject");
        layout.addView(addBtn);
        
        listView = new ListView(this);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjects);
        listView.setAdapter(adapter);
        layout.addView(listView);
        
        setContentView(layout);
        
        startStopBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                running = !running;
                startStopBtn.setText(running ? "Stop" : "Start");
                if(running) handler.post(runnable);
            }
        });
        
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String s = subjectInput.getText().toString().trim();
                if(!s.isEmpty()) {
                    subjects.add(s + " - 0 min");
                    adapter.notifyDataSetChanged();
                    subjectInput.setText("");
                    Toast.makeText(MainActivity.this, "Added!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
