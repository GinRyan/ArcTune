package org.ginryantsuyu.tryanestscrollview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button refreshbutton;
    private LaView lavapie;
    private LaView lavapie2;

    int progress = 0;
    int progress2 = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lavapie2 = (LaView) findViewById(R.id.lavapie2);
        this.refreshbutton = (Button) findViewById(R.id.refresh_button);
        this.lavapie = (LaView) findViewById(R.id.lavapie);

        lavapie.setMaxProgress(100);
        lavapie.setPieColor("#512DA8");
        lavapie.setShowingTextColor("#D1C4E9");
        lavapie.setPaintStyle(LaView.FILL);
        lavapie.setNextProgress(progress);

        lavapie2.setMaxProgress(100);
        lavapie2.setPieColor("#512DA8");
        lavapie2.setShowingTextColor("#D1C4E9");
        lavapie2.setPaintStyle(LaView.STROKE);
        lavapie2.setNextProgress(progress2);

        refreshbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (progress < 100) {
                    progress += 5;
                    lavapie.setNextProgress(progress);
                }
                if (progress2 > 0) {
                    progress2 -= 5;
                    lavapie2.setNextProgress(progress2);
                }
            }
        });
    }
}
