package com.example.countdown;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
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

    String ChannelId="ChannelId";
    String ChannelName="ChannelName";
    NotificationManager notificationManager;

    Handler handler = new Handler();
    Runnable runnable;
    public Intent servIntent;
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
        servIntent = new Intent(this, Services.class);
        startService(servIntent);
        numHour = findViewById(R.id.num_hour);
        numMin = findViewById(R.id.num_min);
        numSec = findViewById(R.id.num_sec);

        if (hour > 9) {
            numHour.setText(String.valueOf(hour));
        }else {
            numHour.setText("0" + String.valueOf(hour));
        }
        if (minutes > 9) {
            numMin.setText(String.valueOf(minutes));
        }else {
            numMin.setText("0" + String.valueOf(minutes));
        }
        if (second > 9) {
            numSec.setText(String.valueOf(second));
        }else {
            numSec.setText("0" + String.valueOf(second));
        }
    }

    @Override
    protected void onResume() {
        startService(servIntent);
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

            if(hour == 0 && minutes == 0 && second == 0){
                notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    Intent intent=new Intent(MainActivity.this,MainActivity.class);
                    PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    NotificationChannel notificationChannel;
                    notificationChannel=new NotificationChannel(ChannelId,ChannelName,NotificationManager.IMPORTANCE_DEFAULT);
                    notificationManager.createNotificationChannel(notificationChannel);
                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, ChannelId)
                            .setSmallIcon(R.drawable.download)
                            .setContentTitle("Dead Time!")
                            .setContentText("You are fucking dead!")
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent);
                    notificationManager.notify(1 , builder.build());
                }
            }
        }, delay);
        super.onResume();
    }
}