package com.example.countdown;

import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView numHour;
    TextView numMin;
    TextView numSec;

    Random random = new Random();

    Integer hour = random.nextInt(23);
    Integer minutes = random.nextInt(59);
    Integer second = random.nextInt(59);

    Handler handler = new Handler();
    Runnable runnable;
    int delay = 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numHour = findViewById(R.id.num_hour);
        numMin = findViewById(R.id.num_min);
        numSec = findViewById(R.id.num_sec);

        numSec.setText(String.valueOf(second));
        numMin.setText(String.valueOf(minutes));
        numHour.setText(String.valueOf(hour));
    }

    @Override
    protected void onResume() {
        handler.postDelayed(runnable = () -> {
            handler.postDelayed(runnable, delay);
            if (second > 0){
                second -= 1;
                if (second > 9) {
                    numSec.setText(String.valueOf(second));
                }else {
                    numSec.setText("0" + String.valueOf(second));
                }
            } else if (minutes > 0){
                second = 59;
                minutes -= 1;
                numSec.setText(String.valueOf(second));
                if (minutes > 9) {
                    numMin.setText(String.valueOf(minutes));
                }else {
                    numMin.setText("0" + String.valueOf(minutes));
                }
                numMin.setText(String.valueOf(minutes));
            } else if (hour > 0){
                second = 59;
                minutes = 59;
                hour -= 1;
                numSec.setText(String.valueOf(second));
                numMin.setText(String.valueOf(minutes));
                if (hour > 9) {
                    numHour.setText(String.valueOf(hour));
                }else {
                    numHour.setText("0" + String.valueOf(hour));
                }
            } else {
                Toast toast = Toast.makeText(MainActivity.this, "شما مردید", Toast.LENGTH_LONG);
                toast.show();
                handler.removeCallbacks(runnable);
            }

        }, delay);
        super.onResume();
    }
}