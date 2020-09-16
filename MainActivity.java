package edu.utep.cs4381.timer;

/*
    Author: Fernando Marquez
 */
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView timeDisplay; // Display clock
    private Button startButton; // Button 1
    private Button stopButton; // Button 2
    private TimerModel timerModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timeDisplay = findViewById(R.id.timeDisplay);
        startButton = findViewById(R.id.startButton); // Retrieve a view
        stopButton = findViewById(R.id.stopButton);

        startButton.setOnClickListener(this::startClicked); // Register a handler
        stopButton.setOnClickListener(this::stopClicked);

        timerModel = new TimerModel();
    }

    private void startClicked(View view) {
        timerModel.start();
       // displayTime();
        new Thread(() -> {
            while (timerModel.isRunning()) {
                this.runOnUiThread(this::displayTime);
                try {
                    Thread.sleep(200); // in millis
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    private void stopClicked(View view) {
        displayTime();
        timerModel.stop();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
    }

    private void displayTime() {
        long sec = timerModel.elapsedTime() / 1000;
        long min = sec / 60; sec %= 60;
        long hour = min / 60; min %= 60;
        timeDisplay.setText(String.format("%d:%02d:%02d",
                hour,
                min,
                sec));
    }
}
