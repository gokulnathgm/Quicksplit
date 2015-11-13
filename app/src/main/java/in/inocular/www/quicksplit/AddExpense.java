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
public class AddExpense extends AsyncTask<String,Void,String> {

    String gname,gid;
    SharedPreferences prefs;
    ProgressDialog progress;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public AddExpense(Context context) {
        this.context = context;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){

        progress.setTitle("Adding Expense");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Connecting");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{

            gid = (String)arg0[0];
            String title = (String)arg0[1];
            String expense = (String)arg0[2];
            gname = (String)arg0[3];

            Log.d("gid", gid);
            Log.d("title", title);
            Log.d("expense", expense);

            String link="http://inocular.in/php/addexpense.php";
            String data  = URLEncoder.encode("title", "UTF-8")
                    + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("gid", "UTF-8")
                    + "=" + URLEncoder.encode(gid, "UTF-8");

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

            Toast.makeText(context, "Successfully added expense", Toast.LENGTH_SHORT).show();
            progress.dismiss();
             Intent i = new Intent(context, GroupActivity.class);
                int g_id = Integer.parseInt(gid);
                i.putExtra("Group_Id",g_id);
                i.putExtra("Group_Name",gname);
                context.startActivity(i);
        }
        else
        {
            Toast.makeText(context,"Error while adding expense",Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
    }
}
