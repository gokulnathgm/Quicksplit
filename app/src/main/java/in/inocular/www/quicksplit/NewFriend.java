package in.inocular.www.quicksplit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewFriend extends ActionBarActivity {

    int itemId;
    String grpName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);


        Bundle extras = getIntent().getExtras();
        itemId = extras.getInt("Group_Id");
        grpName = extras.getString("Group_Name");
        //Toast.makeText(getApplicationContext(),itemId + "",Toast.LENGTH_SHORT).show();
        Button add;
        final EditText userid;
        userid = (EditText)findViewById(R.id.userid);
        add = (Button)findViewById(R.id.addfriend);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userid.getText().toString();
                String userid = String.valueOf(itemId);
                new CreateFriend(NewFriend.this).execute(email,userid);
            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Intent intent = new Intent(this,GroupActivity.class);
        intent.putExtra("Group_Id",itemId);
        intent.putExtra("Group_Name",grpName);
        startActivity(intent);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
