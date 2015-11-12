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
 * Created by goks on 12/11/15.
 */
public class CreateFriend  extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    String email;
    ProgressDialog progress;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public CreateFriend(Context context) {
        this.context = context; //this.statuss = status;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){
        progress.setTitle("Adding friend");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Authenticating user");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            String email = (String)arg0[0];
            String group_id = (String)arg0[1];

            String link="http://inocular.in/php/addfriend.php";
            String data  = URLEncoder.encode("email", "UTF-8")
                    + "=" + URLEncoder.encode(email, "UTF-8");
            data += "&" + URLEncoder.encode("group_id", "UTF-8")
                    + "=" + URLEncoder.encode(group_id, "UTF-8");

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
        if(result.equals("success")) {
            Toast.makeText(context, "Successfully added friend!", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        else if(result.equals("failure")) {
            Toast.makeText(context, "Unable to connect!", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        else
        {
            Toast.makeText(context,"Invalid user!",Toast.LENGTH_SHORT).show();
            //this.statuss.setText("Invalid Credentials!!!");
            progress.dismiss();
        }
    }
}
