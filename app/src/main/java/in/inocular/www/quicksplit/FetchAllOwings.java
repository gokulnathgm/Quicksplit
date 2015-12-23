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
 * Created by goks on 14/11/15.
 */
public class FetchAllOwings  extends AsyncTask<String,Void,String> {

    SharedPreferences prefs;
    String gid,gname;
    ProgressDialog progress;
    private Context context;
    private int byGetOrPost = 0;
    //flag 0 means get and 1 means post.(By default it is get.)
    public FetchAllOwings(Context context,String gname) {
        this.gname = gname;
        this.context = context; //this.statuss = status;
        progress= new ProgressDialog(this.context);
    }

    protected void onPreExecute(){



        progress.setTitle("Loading");
        progress.setCanceledOnTouchOutside(false);
        progress.setCancelable(false);
        progress.setMessage("Fetching expenses");
        progress.show();
    }
    @Override
    protected String doInBackground(String... arg0) {

        try{
            gid = (String)arg0[0];
            Log.d("gid",gid);

            prefs = context.getSharedPreferences("file", 0);
            int user = prefs.getInt("user_id",0);
            String uid  = Integer.toString(user);
            Log.d("uid",uid);

            String link="http://inocular.in/php/listexpense.php";
            String data  = URLEncoder.encode("gid", "UTF-8")
                    + "=" + URLEncoder.encode(gid, "UTF-8");
            data += "&" + URLEncoder.encode("uid", "UTF-8")
                    + "=" + URLEncoder.encode(uid, "UTF-8");

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
            Toast.makeText(context, "Error while adding expense", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }

        else
        {
            String p[] = result.split("%");
            int len = p.length;
            /*p[0] = p[0].trim();
            p[1] = p[1].trim();*/

            String s[] = p[0].split("~");
            int l = s.length;
            Log.d("length = "+l,"");
            prefs = context.getSharedPreferences("file",0);
            SharedPreferences.Editor editor = prefs.edit();
            for(int j=0,k=0;j<l;j+=2){
                editor.putString("member"+(k++),s[j]);
            }
            for(int j=1,k=0;j<l;j+=2){
                editor.putInt("owe"+(k++), Integer.parseInt(s[j]));
            }
            editor.putInt("number_of_members", l/2);
            if(len>1) {
                String exp[] = p[1].split("~");
                l = exp.length;
                Log.d("length = " + l, "");
                for (int j = 0, k = 0; j < l; j += 2) {
                    editor.putString("title" + (k++), exp[j]);
                }
                for (int j = 1, k = 0; j < l; j += 2) {
                    editor.putInt("owings" + (k++), Integer.parseInt(exp[j]));
                }
                editor.putInt("number_of_transactions", l / 2);
            }
            editor.commit();


            Intent i = new Intent(context, GroupActivity.class);
            i.putExtra("Group_Id",Integer.parseInt(gid));
            i.putExtra("Group_Name",gname);
            context.startActivity(i);
            Toast.makeText(context, "Group details fetched", Toast.LENGTH_SHORT).show();
            progress.dismiss();
        }


    }
}
