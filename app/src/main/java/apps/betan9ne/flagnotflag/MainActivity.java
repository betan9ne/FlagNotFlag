package apps.betan9ne.flagnotflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button play, share, rate;
    String options[];
    TextView option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        share = findViewById(R.id.share);
        rate = findViewById(R.id.rate);
        option = findViewById(R.id.options);

        options = new String[]{"Baby Steps (10 Flags)", "Hit Me again (20 Flags)","Flexin' (50 Flags)", "Feeling It (70 Flags)"
                , "Bossy (100 Flags)"
        , "Bananas (150 Flags)"
                , "Fast finger er (200 Flags)"};

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(getApplicationContext(), QuizActivity.class);
                startActivity(asd);
            }
        });
        final int[] tCounter = {0};
        option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int Counter = options.length;
                if(tCounter[0] == Counter-1)
                {
                    tCounter[0] = 0;
                }
                else
                {
                    tCounter[0]++;
                }
                option.setText(options[tCounter[0]]);
              }
        });

    }
}
