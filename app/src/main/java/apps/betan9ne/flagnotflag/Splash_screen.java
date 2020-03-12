package apps.betan9ne.flagnotflag;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

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

