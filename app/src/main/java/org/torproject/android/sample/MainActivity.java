package org.torproject.android.sample;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.torproject.jni.TorService;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("UnspecifiedRegisterReceiverFlag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PackageManager pm = this.getPackageManager();
        String packageName = this.getPackageName();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
        WebView webView = findViewById(R.id.webview);
        TextView statusTextView = findViewById(R.id.status);

        GenericWebViewClient webViewClient = new GenericWebViewClient();
        webViewClient.setRequestCounterListener(requestCount -> runOnUiThread(
                () -> statusTextView.setText("Request Count: " + requestCount))
        );
        webView.setWebViewClient(webViewClient);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String status = intent.getStringExtra(TorService.EXTRA_STATUS);
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    webView.loadUrl("https://check.torproject.org/");
                }
            }, new IntentFilter(TorService.ACTION_STATUS), Context.RECEIVER_NOT_EXPORTED);
        }else {
            registerReceiver(new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    String status = intent.getStringExtra(TorService.EXTRA_STATUS);
                    Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
                    webView.loadUrl("https://check.torproject.org/");
                }
            }, new IntentFilter(TorService.ACTION_STATUS));
        }

        bindService(new Intent(this, TorService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                TorService torService = ((TorService.LocalBinder) service).getService();
                while (torService.getTorControlConnection() == null){
                    try {
                        Thread.sleep(500);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }
}