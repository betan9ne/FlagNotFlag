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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

//import com.google.firebase.firestore.CollectionReference;
//import com.google.firebase.firestore.Query;
//import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import apps.betan9ne.flagnotflag.helper.AppConfig;
import apps.betan9ne.flagnotflag.helper.HighScoreHandler;
import apps.betan9ne.flagnotflag.helper.Methods;
import apps.betan9ne.flagnotflag.helper.SQLiteHandler;
import apps.betan9ne.flagnotflag.helper.leader_item;

public class DoneActivity extends AppCompatActivity {
    TextView points, hiscoret;
    Button tryagian, share;

    private SQLiteHandler db;
    private HighScoreHandler hdb;
    String name, email, u_id;
    int hiscore , timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        points = findViewById(R.id.points);
        tryagian= findViewById(R.id.tryagain);
        hiscoret= findViewById(R.id.hiscore);

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4118077067837317~1079558788");
        AdView mAdView = findViewById(R.id.adView2);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        db = new SQLiteHandler(getApplicationContext());
        HashMap<String, String> user = db.getUserDetails(DoneActivity.class.getSimpleName());
        u_id = user.get("u_id");
        name = user.get("name");
        email = user.get("email");

        Methods methods = new Methods(getApplicationContext());
        hdb = new HighScoreHandler(getApplicationContext());
        SharedPreferences prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        int score = prefs.getInt("score", 0); //0 is the default value
        hiscore= prefs.getInt("hiscore", 0); //0 is the default value
        timer= prefs.getInt("timer", 0); //0 is the default value
        int attempts= prefs.getInt("attempts", 0); //0 is the default value

        hiscore = prefs.getInt(timer+"f", 0);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("score", score);
        if(score > hiscore)
        {
            methods.setScore(timer, score);
            hiscore = score;
            hiscoret.setText(score+"");
            updateLederboard();
        }
        else
        {
            hiscoret.setText(hiscore+"");
        }
        editor.apply();
        hdb.addScore(email, timer+"", hiscore+"");
        tryagian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent asd = new Intent(getApplicationContext(), MainActivity.class);
                asd.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(asd);
                finish();
            }
        });
        share= findViewById(R.id.share);
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
        points.setText(score+"/"+attempts);
    }



    private void updateLederboard() {

        StringRequest strReq = new StringRequest(Request.Method.POST,
                AppConfig.update, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jObj = new JSONObject(response);
                    boolean error = jObj.getBoolean("error");
                    // Check for error node in json
                    if (!error) {
                        String email = jObj.getString("message");
                    //    Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMsg = jObj.getString("message");
                       // Toast.makeText(getApplicationContext(), "Sorry we were not able to update your scores on our servers", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    // JSON error
                    e.printStackTrace();
                }
            }
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // Toast.makeText(getApplicationContext(),	"Could not update high scores", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("u_id", u_id);
                params.put("f_name", name);
                params.put("email", email);
                params.put("_time", timer+"");
                params.put("score", hiscore+"");
                return params;
            }
        };
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(strReq);
    }
}
