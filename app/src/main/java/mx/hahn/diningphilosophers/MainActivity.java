package mx.hahn.diningphilosophers;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            Philosophers.setContext(this);
        for(int i = 0; i < 5; i++) {
            Thread t = new Philosophers(i);
            t.start();
        }

    }

    protected void update() {
        PhilosopherStatus[] args = Philosophers.status;
        String s = "";
        for(PhilosopherStatus p : args) {
            s += p.name() + "\n";
        }
        TextView t = findViewById(R.id.text);
        t.setText(s);

    }
}
