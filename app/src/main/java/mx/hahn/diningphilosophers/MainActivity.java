package mx.hahn.diningphilosophers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView[] img = new ImageView[5];
    ImageView[] f = new ImageView[5];
    public static Philosophers[] th = new Philosophers[5];
    public static boolean deadlock = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Philosophers.setContext(this);
        final Button startBtn = findViewById(R.id.startButton);
        final Button stopBtn = findViewById(R.id.stopButton);
        final Button deadlockBtn = findViewById(R.id.deadlockButton);
        startBtn.setEnabled(true);
        stopBtn.setEnabled(false);
        deadlockBtn.setEnabled(false);

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
        f[0] = findViewById(R.id.fork1);
        f[1] = findViewById(R.id.fork2);
        f[2] = findViewById(R.id.fork3);
        f[3] = findViewById(R.id.fork4);
        f[4] = findViewById(R.id.fork5);

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startBtn.setEnabled(false);
                stopBtn.setEnabled(true);
                deadlockBtn.setEnabled(true);

                for(int i = 0; i < 5; i++) {
                    th[i] = new Philosophers(i);
                    th[i].start();
                }

            }
        });
        findViewById(R.id.deadlockButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(deadlock) {
                    deadlockBtn.setText("Cause Deadlock");
                    Philosophers.causeDeadlock = false;
                    for(int i = 0; i < 5; i++) {
                        th[i].stopPhilosopher();
                    }
                    Philosophers.resetForks();
                    for(int i = 0; i < 5; i++) {
                        th[i] = new Philosophers(i);
                        th[i].start();
                    }
                    deadlock = false;

                }
                else {
                    deadlockBtn.setText("Solve Deadlock");
                    Philosophers.causeDeadlock = true;
                    deadlock = true;
                }

            }
        });
        findViewById(R.id.stopButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startBtn.setEnabled(true);
                stopBtn.setEnabled(false);
                deadlockBtn.setEnabled(false);

                for(int i = 0; i < 5; i++) {
                    th[i].stopPhilosopher();
                }

            }
        });

    }

    protected void update() {
        PhilosopherStatus[] args = Philosophers.status;
        boolean[] forks = Philosophers.forkStatus;
        for(int i = 0; i < 5; i++) {
            switch (args[i]){
                case ABSENT:
                    img[i].setImageDrawable(getDrawable(R.drawable.absent));
                    break;
                case EATING:
                    img[i].setImageDrawable(getDrawable(R.drawable.eating));
                    break;
                case THINKING:
                    img[i].setImageDrawable(getDrawable(R.drawable.thinking));
                    break;
                case WAITING:
                    img[i].setImageDrawable(getDrawable(R.drawable.waiting));
                    break;
            }
            if(forks[i]) {
                f[i].setVisibility(View.VISIBLE);
            }
            else {
                f[i].setVisibility(View.INVISIBLE);
            }
        }

    }
}
