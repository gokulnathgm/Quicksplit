package in.inocular.www.quicksplit;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by goks on 20/9/15.
 */
public class SplashScreen extends Activity {
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }finally{
                    prefs = getSharedPreferences("file",0);
                    Boolean check = prefs.getBoolean("logged_in",false);
                    if(check){
                        Intent intent = new Intent(SplashScreen.this,Home.class);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                        startActivity(intent);
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
