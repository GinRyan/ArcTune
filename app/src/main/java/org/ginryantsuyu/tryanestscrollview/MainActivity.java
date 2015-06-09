package org.ginryantsuyu.tryanestscrollview;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private LaView lavapie;
    int progress = 0;
    private android.widget.Button refreshbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.refreshbutton = (Button) findViewById(R.id.refresh_button);
        this.lavapie = (LaView) findViewById(R.id.lavapie);

        lavapie.setMaxProgress(100);
        lavapie.setPieColor("#512DA8");
        lavapie.setShowingTextColor("#D1C4E9");
        lavapie.setNextProgress(progress);
        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress < 100) {
                    progress += 5;
                    lavapie.setNextProgress(progress);
                }
            }
        });
    }
}
