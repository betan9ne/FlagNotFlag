package apps.betan9ne.flagnotflag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.HashMap;

import apps.betan9ne.flagnotflag.helper.SQLiteHandler;
import apps.betan9ne.flagnotflag.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    Button play, logout;
        ImageView share, rate;
    String options[];
    TextView option, counter;
    ImageView left, right;
    final int[] tCounter = {0};
    SharedPreferences prefs;
    GoogleSignInClient googleSignInClient;
    private SQLiteHandler db;
    private SessionManager session;
    View container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = findViewById(R.id.play);
        container = findViewById(R.id.container);
        logout= findViewById(R.id.play2);
        share = findViewById(R.id.imageView4);
        rate = findViewById(R.id.imageView5);
        option = findViewById(R.id.options);
        counter= findViewById(R.id.counter);
        right = findViewById(R.id.right);
        left= findViewById(R.id.left);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails(MainActivity.class.getSimpleName());
        String name_ = user.get("name");
         prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        int timer = prefs.getInt("timer", 0);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        Snackbar snackbar = Snackbar.make(container, "Hi "+name_+", Welcome to Fast Flag.", Snackbar.LENGTH_LONG);
        snackbar.show();

       MobileAds.initialize(getApplicationContext(), "ca-app-pub-4118077067837317~1079558788");
       AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        options = new String[]{"11","16", "21","26","31","36"};
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

        googleSignInClient = GoogleSignIn.getClient(getApplicationContext(), gso);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        session.setLogin(false);
                        Intent intent=new Intent(getApplicationContext(), Splash_screen.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        db.deleteUsers();
                    }
                });
            }
        });


    }
}
