package apps.betan9ne.flagnotflag;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

import apps.betan9ne.flagnotflag.helper.HighScoreHandler;
import apps.betan9ne.flagnotflag.helper.SQLiteHandler;
import apps.betan9ne.flagnotflag.helper.SessionManager;

public class MainActivity extends AppCompatActivity {
    Button play, logout;
    ImageView share, rate, leaderboard, about, left, right;
    TextView option, counter;
    SharedPreferences prefs;
    GoogleSignInClient googleSignInClient;
    private SQLiteHandler db;
    private HighScoreHandler hdb;
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
        about = findViewById(R.id.imageView7);
        leaderboard = findViewById(R.id.imageView6);
        option = findViewById(R.id.options);
        counter= findViewById(R.id.counter);
        right = findViewById(R.id.right);
        left= findViewById(R.id.left);

        session = new SessionManager(getApplicationContext());
        db = new SQLiteHandler(getApplicationContext());
        hdb = new HighScoreHandler(getApplicationContext());
        prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        int timer = prefs.getInt("timer", 0);

        HashMap<String, String> user = db.getUserDetails(MainActivity.class.getSimpleName());
        String name_ = user.get("name");
        String email_= user.get("email");

       /* HashMap<String, String> highscore = hdb.getUserScore(MainActivity.class.getSimpleName(), email_, timer);
        String score_ = highscore.get("score");
        Toast.makeText(this, score_+"", Toast.LENGTH_LONG).show();
*/
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        Snackbar snackbar = Snackbar.make(container, "Hi "+name_+", Welcome to Fast Flag.", Snackbar.LENGTH_LONG);
        snackbar.show();

       MobileAds.initialize(getApplicationContext(), "ca-app-pub-4118077067837317~1079558788");
       AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        final ArrayList<String> aList = new ArrayList<>();
        aList.add("11");
        aList.add("16");
        aList.add("21");
        aList.add("26");
        aList.add("31");
        aList.add("36");
        final ListIterator<String> listIterator = aList.listIterator();
        counter.setText(aList.get(0) + "");

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(getApplicationContext(), QuizActivity.class);
                asd.putExtra("count",counter.getText());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("timer", Integer.parseInt(counter.getText().toString()));
                editor.putInt("score", 0);
                editor.apply();
                startActivity(asd);
            }
        });

        rate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlf = "https://play.google.com/store/apps/details?id=apps.betan9ne.flagnotflag";
                Intent iff = new Intent(Intent.ACTION_VIEW);
                iff.setData(Uri.parse(urlf));
                startActivity(iff);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String urlf = "http://88radium.com/flag/";
                Intent iff = new Intent(Intent.ACTION_VIEW);
                iff.setData(Uri.parse(urlf));
                startActivity(iff);
            }
        });
        leaderboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaderboardFragment bottomSheetFragment = new LeaderboardFragment();
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "");
                        String shareMessage = "Test your attention to detail on FLag Not a Flag Game" ;
                        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT,shareMessage+ ". https://play.google.com/store/apps/details?id=apps.betan9ne.flagnotflag");
                        startActivity(Intent.createChooser(shareIntent,"FLAG NOT A FLAG"));
                    }
                });
            }
        });
        left.setVisibility(View.INVISIBLE);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!listIterator.hasNext())
                {
                    right.setVisibility(View.INVISIBLE);
                }
                else {
                    left.setVisibility(View.VISIBLE);
                    counter.setText(listIterator.next() + "");
                }
              }
        });
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(!listIterator.hasPrevious())
               {
                   left.setVisibility(View.INVISIBLE);
               }
               else
               {
                   right.setVisibility(View.VISIBLE);
                   counter.setText(listIterator.previous()+"");
               }
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
