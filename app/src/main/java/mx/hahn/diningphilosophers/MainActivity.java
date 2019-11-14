package mx.hahn.diningphilosophers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ImageView[] img = new ImageView[5];
    public static Thread[] th = new Thread[5];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img[0] = findViewById(R.id.img1);
        img[0].setImageDrawable(getDrawable(R.drawable.absent));
        img[1] = findViewById(R.id.img2);
        img[1].setImageDrawable(getDrawable(R.drawable.absent));
        img[2] = findViewById(R.id.img3);
        img[2].setImageDrawable(getDrawable(R.drawable.absent));
        img[3] = findViewById(R.id.img4);
        img[3].setImageDrawable(getDrawable(R.drawable.absent));
        img[4] = findViewById(R.id.img5);
        img[4].setImageDrawable(getDrawable(R.drawable.absent));
        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Philosophers.setContext(this);
                for(int i = 0; i < 5; i++) {
                    th[i] = new Philosophers(i);
                    th[i].start();
                }
            }
        });
        findViewById(R.id.deadlockButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Philosophers.causeDeadlock = true;
            }
        });
        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        /*Philosophers.setContext(this);
        for(int i = 0; i < 5; i++) {
            Thread t = new Philosophers(i);
            t.start();
        }*/

    }

    protected void update() {
        PhilosopherStatus[] args = Philosophers.status;
        String s = "";
        for(PhilosopherStatus p : args) {
            s += p.name() + "\n";
            switch (p.)
        }
        TextView t = findViewById(R.id.text);
        t.setText(s);

    }
}
