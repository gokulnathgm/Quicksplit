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
public class AddExpense  extends AsyncTask<String,Void,String> {

    String gid,gname;
    ProgressDialog progress;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public AddExpense(Context context,String gname) {
        this.gname = gname;
        this.context = context; //this.statuss = status;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){



        progress.setTitle("Loading");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Adding expense");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            String title = (String)arg0[1];
            gid = (String)arg0[0];
            String uid = (String)arg0[4];
            String paid_owe = (String)arg0[5];

            Log.d("title",title);
            Log.d("gid",gid);
            Log.d("uid",uid);
            Log.d("paid_owe",paid_owe);
            Log.d("gname",gname);

            String link="http://inocular.in/php/addexpense.php";
            String data  = URLEncoder.encode("title", "UTF-8")
                    + "=" + URLEncoder.encode(title, "UTF-8");
            data += "&" + URLEncoder.encode("gid", "UTF-8")
                    + "=" + URLEncoder.encode(gid, "UTF-8");
            data += "&" + URLEncoder.encode("uid", "UTF-8")
                    + "=" + URLEncoder.encode(uid, "UTF-8");
            data += "&" + URLEncoder.encode("paid_owe", "UTF-8")
                    + "=" + URLEncoder.encode(paid_owe, "UTF-8");
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

            Intent i = new Intent(context, GroupActivity.class);
            i.putExtra("Group_Id",Integer.parseInt(gid));
            i.putExtra("Group_Name",gname);
            context.startActivity(i);
            Toast.makeText(context, "New expense added", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        else
        {
            Toast.makeText(context,"Error while adding expense",Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }
    }
}
