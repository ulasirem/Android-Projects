package com.iremulas.runnablehandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView textView;
    int number;
    Runnable runnable;
    Handler handler;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);
        number = 0;
    }

    public void start(View view){
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                textView.setText("Time : "+number);
                number++;
                textView.setText("Time : "+number);
                handler.postDelayed(runnable,1000); //handler ile 1 saniye ilerlerken textView de görebiliriz(1 saniye gecikme)
            }
        };
        handler.post(runnable); //handler yapılan işlemler için runnable'yı bilgilendirir
        button.setEnabled(false);   //eğer false yapmazsam butona basmaya devam eder ve zaman hızla ilerler

    }

    public void stop(View view){
        button.setEnabled(true);
        handler.removeCallbacks(runnable);  //zamanı durdurur.
        textView.setText("Time : "+number);
        number = 0;

    }
}