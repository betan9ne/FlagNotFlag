package apps.betan9ne.flagnotflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;


public class QuizActivity extends AppCompatActivity {
    private List<Quiz> flags = new ArrayList<>();
    Button flag, notflag, done;
    TextView timer;
    String counter;
    ImageView _flag;
    Bundle b;
    int isFlag = 0;
    int isNotflag = 0;
    int attempts = 0;
    ArrayList<Integer> answeredAraay = new ArrayList<>();
    int answerCode;
    Vibrator vibe;
    private long startTime;  // 1000 = 1 second
    private final long interval = 1000;
    SharedPreferences prefs;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(getIntent().getExtras() != null) {
            b = getIntent().getExtras();
            startTime = Long.parseLong(b.getString("count"))*1000;
            startTime = startTime+1000;
        }

        final MyCountDown countdown = new MyCountDown(startTime,interval);
        prefs = this.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);

        vibe= (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        flag = findViewById(R.id.flag);
        _flag = findViewById(R.id.imageView);
        timer= findViewById(R.id.timer);
        notflag = findViewById(R.id.notflag);
        done = findViewById(R.id.done);
        AdView mAdView = findViewById(R.id.adView2);

        flags = new ArrayList<>();

        MobileAds.initialize(getApplicationContext(), "ca-app-pub-4118077067837317~1079558788");

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int finalScore;
                finalScore = isFlag;
                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("score", finalScore*100);
                editor.putInt("attempts", attempts);
                editor.apply();

                Intent asd = new Intent(getApplicationContext(), DoneActivity.class);
                startActivity(asd);
//                mediaPlayer.stop();
                mediaPlayer.release();
                 finish();
            }
        });
        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answeredAraay.size() == 259)
                {
                    Intent asd = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(asd);
                    answeredAraay.clear();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    finish();

                }
                else
                {
                    if(answerCode == 0)
                    {
                           isFlag++;
                    }
                    else
                    {
                        isNotflag++;
                          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibe.vibrate(500);
                        }
                    }
                    attempts++;
                    next();
                }
            }
        });
        notflag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answeredAraay.size() == 259)
                {
                    Intent asd = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(asd);
                    answeredAraay.clear();
                    mediaPlayer.release();
                    mediaPlayer = null;
                    finish();
                }
                else
                {
                    if(answerCode == 1)
                    {
                    //    Toast.makeText(QuizActivity.this, "NOt flag Correct", Toast.LENGTH_SHORT).show();
                        isNotflag++;
                     //   notflag.setBackgroundColor(Color.parseColor("#009d00"));

                    }
                    else
                    {
                        isFlag++;
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            vibe.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                        } else {
                            vibe.vibrate(500);
                        }
                   //     notflag.setBackgroundColor(Color.parseColor("#e60000"));
                      //  Toast.makeText(QuizActivity.this, "Is flag Wrong", Toast.LENGTH_SHORT).show();
                    }
                    attempts++;
                    next();
                }
            }
        });
        try
        {
            JSONObject resObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = resObject.getJSONArray("flags");

            for (int i = 0; i < jsonArray.length(); i++) {
                    int c= 0;
                    JSONObject feedObj = (JSONObject) jsonArray.get(i);
                    Quiz item = new Quiz();
                    item.setId(c++);
                    item.setFlag(feedObj.getString("file"));
                    item.setCountry(feedObj.getString("name"));
                    item.setStatus(feedObj.getInt("status"));
                    flags.add(item);
                }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        next();
        countdown.start();
        mediaPlayer= MediaPlayer.create(QuizActivity.this, R.raw.tick);
        mediaPlayer.start();
    }

    public class MyCountDown extends CountDownTimer {
        public MyCountDown(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            // TODO Auto-generated constructor stub
        }

        @Override
        public void onFinish() {
            // TODO Auto-generated method stub
            TextView result = (TextView) findViewById(R.id.timer);
            result.setText("0");
             flag.setVisibility(View.GONE);
             notflag.setVisibility(View.GONE);
             done.setVisibility(View.VISIBLE);
           //  mediaPlayer.stop();
             mediaPlayer.release();
        }

        @Override
        public void onTick(long remain) {

            TextView result =   findViewById(R.id.timer);
            int timeRemain = (int) (remain) / 1000;
            result.setText(timeRemain+"");
        }

    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("flag.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void next()
    {
   //     Toast.makeText(this, flags.size()+"", Toast.LENGTH_SHORT).show();
        Random r=new Random();
        int randomNumber;
        do{
            randomNumber = r.nextInt((flags.size() - 1)+1) + 1;
        }
        while (answeredAraay.contains(randomNumber));

        if(!answeredAraay.contains(randomNumber))
        {
            getNext(randomNumber);
            answeredAraay.add(randomNumber);
        }
    }

    public void getNext(int i)
    {
        Quiz quiz = flags.get(i);
        _flag.setImageBitmap(getBitmapFromAssets(quiz.getFlag()));
        answerCode = quiz.getStatus();
    }

    // Custom method to get assets folder image as bitmap
    private Bitmap getBitmapFromAssets(String fileName){
        AssetManager am = getAssets();
        InputStream is = null;
        try{is= am.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }





}
