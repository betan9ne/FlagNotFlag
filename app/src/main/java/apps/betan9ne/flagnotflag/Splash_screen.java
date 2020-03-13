package apps.betan9ne.flagnotflag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import apps.betan9ne.flagnotflag.helper.SessionManager;

public class Splash_screen extends AppCompatActivity {
    private SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

            session = new SessionManager(getApplicationContext());

            Thread timer = new Thread(){
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                public void run(){
                    try{
                        sleep(1000);
                    }
                    catch(InterruptedException ex)
                    {
                        ex.printStackTrace();
                    }
                    finally
                    {
                        if (session.isLoggedIn()) {
                            Intent asd = new Intent(Splash_screen.this, MainActivity.class);
                            startActivity(asd);
                            finish();
                        }
                        else
                        {
                            finishAndRemoveTask();
                            Intent ads = new Intent(Splash_screen.this, SignInActivity.class);
                            ads.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(ads);
                            finish();
                        }
                    }
                }
            };
            timer.start();
        }

        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }
    }


    /*public void flags()
    {
        Quiz quiz;
        int i = 0;
        quiz = new Quiz(i++,"ad.png","Andorra",0);
        flags.add(quiz);
        quiz = new Quiz(i++,"ae.png","United Arab Emirates",0);
        flags.add(quiz);
        quiz = new Quiz(i++,"af.png","Afghanistan",0);
        flags.add(quiz);
        quiz = new Quiz(i++,"ag.png","Antigua and Barbuda",0);
        flags.add(quiz);
        quiz = new Quiz(i++,"al.png","Albania", 0);
        flags.add(quiz);
        quiz = new Quiz(i++,"am.png","Armenia", 0);
        flags.add(quiz);
        quiz = new Quiz(i++,"ao.png","Angola", 0);
        flags.add(quiz);
        quiz = new Quiz(i++,"ar.png","Argentina",0);
        flags.add(quiz);

        Toast.makeText(this, flags.size()+"", Toast.LENGTH_SHORT).show();
    }*/
