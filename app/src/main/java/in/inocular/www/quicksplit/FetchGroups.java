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
 * Created by goks on 23/12/15.
 */
public class FetchGroups extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    ProgressDialog progress;int id;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public FetchGroups(Context context) {
        this.context = context;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){

        progress.setTitle("Fetching Groups");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Connecting");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            prefs = context.getSharedPreferences("file",0);
            String user = String.valueOf(prefs.getInt("user_id", 0));
            Log.d("user id", user);
            String link="http://inocular.in/php/fetchgroups.php";
            String data  = URLEncoder.encode("uid", "UTF-8")
                    + "=" + URLEncoder.encode(user, "UTF-8");

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
        if(result.equals("failure"))
        {
            Toast.makeText(context,"Error while fetching groups",Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
        else{
            String[] a =  result.split("%");
            prefs = context.getSharedPreferences("file", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int l = a.length;
            Log.d("length = "+l,"");
            for(int j=0,k=0;j<l;j+=2){
                editor.putInt("group_id" + (k++), Integer.parseInt(a[j]));
            }
            for(int j=1,k=0;j<l;j+=2){
                editor.putString("group_name"+(k++), a[j]);
            }
            editor.putInt("number_of_groups", l);
            editor.commit();

            Toast.makeText(context, "Groups Fetched ", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
    }
}
