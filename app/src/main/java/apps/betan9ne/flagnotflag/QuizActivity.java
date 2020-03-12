package apps.betan9ne.flagnotflag;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class QuizActivity extends AppCompatActivity {
    private List<Quiz> flags = new ArrayList<>();
    Button flag, notflag;
    TextView timer;
    int counter = 4;
    ImageView _flag;
    ArrayList<Integer> answeredAraay = new ArrayList<>();
    private static CountDownTimer countDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        flag = findViewById(R.id.flag);
        _flag = findViewById(R.id.imageView);
        timer= findViewById(R.id.timer);
        notflag = findViewById(R.id.notflag);
        flags = new ArrayList<>();
        flags();
        startTimer(60);



        flag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });
try
        {
            JSONObject resObject = new JSONObject(loadJSONFromAsset());
            JSONArray jsonArray = resObject.getJSONArray("flags");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONArray jsonArray1 = jsonArray.getJSONArray(i);
                for (int j = 0; j < jsonArray1.length(); j++) {
                    Log.i("Value","->" +jsonArray1.getString(j));
                }
            }

        }
        catch (JSONException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("flags.json");
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
        if(answeredAraay.size() == 4)
        {
          //  Intent asd = new Intent(getApplicationContext(), MainActivity.class);
         /*   Bundle bundle = new Bundle();
            bundle.putInt("correct_answer",correct_Answer);
            bundle.putInt("wrong_answer",wrong_answer);
            asd.putExtras(bundle);*/
         //   startActivity(asd);
            answeredAraay.clear();
            flag.setVisibility(View.GONE);
            notflag.setVisibility(View.GONE);
        //    finish();
        }
    }

    public void getNext(int i)
    {
        Quiz quiz = flags.get(i);
        _flag.setImageBitmap(getBitmapFromAssets(quiz.getFlag()));
        Toast.makeText(this, quiz.getCountry()+"", Toast.LENGTH_SHORT).show();

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

    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
              //  timer.setText(hms);//set text
            }

            public void onFinish() {

            //    timer.setText("TIME'S UP!!"); //On finish change timer text
                countDownTimer = null;//set CountDownTimer to null
                stopCountdown();
             }
        }.start();

    }
    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }
    public void flags()
    {
        Quiz quiz;
        int i = 0;
        quiz = new Quiz(i++,"ad.png","Andorra");
        flags.add(quiz);
        quiz = new Quiz(i++,"ae.png","United Arab Emirates");
        flags.add(quiz);
        quiz = new Quiz(i++,"af.png","Afghanistan");
        flags.add(quiz);
        quiz = new Quiz(i++,"ag.png","Antigua and Barbuda");
        flags.add(quiz);
        quiz = new Quiz(i++,"al.png","Albania");
        flags.add(quiz);
        quiz = new Quiz(i++,"am.png","Armenia");
        flags.add(quiz);
        quiz = new Quiz(i++,"ao.png","Angola");
        flags.add(quiz);
        quiz = new Quiz(i++,"ar.png","Argentina");
        flags.add(quiz);

        Toast.makeText(this, flags.size()+"", Toast.LENGTH_SHORT).show();
    }


}
