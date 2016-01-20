package in.inocular.www.quicksplit;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

/**
 * Created by goks on 13/10/15.
 */

public class SigninActivity  extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    String email;
    ProgressDialog progress;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public SigninActivity(Context context,String username) {
        this.context = context; //this.statuss = status;
        progress= new ProgressDialog(this.context);
        email=username;


    }

    protected void onPreExecute(){



        progress.setTitle("Logging In");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Authenticating User");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            String email = (String)arg0[0];
            String password = (String)arg0[1];

            String link="http://inocular.in/php/login.php";
            String data  = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("password", "UTF-8")
                    + "=" + URLEncoder.encode(password, "UTF-8");
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter wr = new OutputStreamWriter
                    (conn.getOutputStream());
            wr.write( data );
            wr.flush();
            BufferedReader reader = new BufferedReader
                    (new InputStreamReader(conn.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                sb.append(line);
                break;
            }

            return sb.toString();

        }catch(Exception e){
            return new String("Exception: " + e.getMessage());
        }
    }

    @Override
    protected void onPostExecute(String result){

        Log.d("RESULT", result);
        if(result.contains("success")) {

            String a[] = result.split(" ");
            int uid = Integer.parseInt(a[1]);
            prefs = context.getSharedPreferences("file",0);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("user_id",uid);
            editor.putBoolean("logged_in",true);
            editor.commit();

            Intent i = new Intent(context, intro.class);
            context.startActivity(i);
            ;
            Toast.makeText(context, "Successfuly Logged in !", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        else
        {
            Toast.makeText(context,"Invalid user credentials !",Toast.LENGTH_SHORT).show();
            //this.statuss.setText("Invalid Credentials!!!");
            progress.dismiss();
        }
    }
}
