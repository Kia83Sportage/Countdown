package com.example.countdown;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.security.Provider;

public class Services extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Toast.makeText(this, "سرویس ساخته شد", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "سرویس شروع شد", Toast.LENGTH_SHORT).show();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this, "سرویس متوقف شد", Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
