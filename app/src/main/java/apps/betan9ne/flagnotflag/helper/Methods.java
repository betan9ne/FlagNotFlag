package apps.betan9ne.flagnotflag.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

public class Methods {
    private static String TAG = Methods.class.getSimpleName();

    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    public Methods(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences("flagNoflag", Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void setScore(int timer, int score) {
        editor.putInt(timer+"f", score);
        editor.commit();
     //   Toast.makeText(_context, "score has been set" + timer + " " + score, Toast.LENGTH_LONG).show();
    }

    public int showScore(int timer, int score) {
        int hiscore = pref.getInt(timer+"f", 0);
       // Toast.makeText(_context, "Highscore" + timer + " " + score, Toast.LENGTH_SHORT).show();
        return hiscore;

    }

}
