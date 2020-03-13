package apps.betan9ne.flagnotflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DoneActivity extends AppCompatActivity {
    TextView points, hiscoret;
    Button tryagian, share;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        points = findViewById(R.id.points);
        tryagian= findViewById(R.id.tryagain);
        hiscoret= findViewById(R.id.hiscore);


        SharedPreferences prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        int score = prefs.getInt("score", 0); //0 is the default value
        int hiscore = prefs.getInt("hiscore", 0); //0 is the default value
        int attempts= prefs.getInt("attempts", 0); //0 is the default value

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score);
        if(score > hiscore)
        {
            editor.putInt("hiscore", score);
            hiscoret.setText(score+"");
        }
        else
        {
            hiscoret.setText(hiscore+"");
        }
        editor.commit();



        tryagian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(asd);
                finish();
            }
        });
        share= findViewById(R.id.share);
        points.setText(score+"/"+attempts);
    }
}
