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
 * Created by goks on 13/11/15.
 */
public class FetchFriends extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    String group;
    ProgressDialog progress;int id;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public FetchFriends(Context context) {
        this.context = context;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){

        progress.setTitle("Loading");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Fetching friends");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{

            group = (String)arg0[0];
            Log.d("group id", group);
            prefs = context.getSharedPreferences("file",0);

            String link="http://inocular.in/php/fetchfriends.php";
            String data  = URLEncoder.encode("group_id", "UTF-8")
                    + "=" + URLEncoder.encode(group, "UTF-8");

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
        if(result.equals("failure")) {
            Toast.makeText(context, "Error while fetching group members", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
        else {
            prefs = context.getSharedPreferences("file",0);
            SharedPreferences.Editor editor = prefs.edit();
            String[] members = result.split("%");
            String group_members="";
            String members_id="";
            for(int i=0;i<members.length;i+=2)
                group_members+=members[i]+" ";
            for(int i=1;i<members.length;i+=2)
                members_id+=members[i]+" ";
            Log.d("group members"+members.length, group_members);
            Log.d("members id", members_id);
            editor.putString("group_members",group_members);
            editor.putString("members_id",members_id);
            editor.commit();
            Toast.makeText(context, "Group fetched ", Toast.LENGTH_SHORT).show();
            progress.dismiss();

        }
    }
}