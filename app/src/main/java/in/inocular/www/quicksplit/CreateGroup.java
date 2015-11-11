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
 * Created by goks on 14/10/15.
 */
public class CreateGroup extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    String group;
    ProgressDialog progress;int id;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public CreateGroup(Context context) {
        this.context = context;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){

        progress.setTitle("Creating Group");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Connecting");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{

            group = (String)arg0[0];
            Log.d("group name", group);

            String link="http://inocular.in/php/creategroup.php";
            String data  = URLEncoder.encode("group", "UTF-8")
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
        if(result.contains("success")) {
            String[] a =  result.split(" ");
            id = Integer.parseInt(a[1]);
            prefs = context.getSharedPreferences("file", 0);
            SharedPreferences.Editor editor = prefs.edit();
            int num = prefs.getInt("number_of_groups",0);
            editor.putInt("group_id"+num,id);
            editor.putString("group_name"+num,group);
            num++;
            editor.putInt("number_of_groups",num);
            editor.commit();


            Intent i = new Intent(context, GroupActivity.class);
            context.startActivity(i);

            Toast.makeText(context, "Group Created ", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
        else
        {
            Toast.makeText(context,"Error while creating group",Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
    }
}
