package apps.betan9ne.flagnotflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
    Button play, share, rate;
    String options[];
    TextView option, counter;
    ImageView left, right;
    final int[] tCounter = {0};
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        option = findViewById(R.id.options);
        counter= findViewById(R.id.counter);
        right = findViewById(R.id.right);
        left= findViewById(R.id.left);

         prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        int timer = prefs.getInt("timer", 0);

        MobileAds.initialize(this, "ca-app-pub-4118077067837317/4025479840");

        options = new String[]{"11","17", "23","29","36","42"};
        if(timer == 0)
        {
            counter.setText(options[tCounter[0]]);
        }
        else
        {
            counter.setText(timer+"");
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(getApplicationContext(), QuizActivity.class);
                asd.putExtra("count",counter.getText());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("timer", Integer.parseInt(counter.getText().toString()));
                editor.commit();
                startActivity(asd);
            }
        });

        left.setVisibility(View.INVISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Counter = options.length;
                if(tCounter[0] == Counter-1)
                {
                    tCounter[0] = 0;
                    right.setVisibility(View.INVISIBLE);
                }
                else
                {
                    tCounter[0]++;
                    left.setVisibility(View.VISIBLE);
                }
                counter.setText(options[tCounter[0]]);
              }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Counter = options.length;
                if(tCounter[0] == Counter-1)
                {
                    tCounter[0] = 0;
                }
                else
                {
                    tCounter[0]--;
                }
                counter.setText(options[tCounter[0]]);
            }
        });


    }
}
