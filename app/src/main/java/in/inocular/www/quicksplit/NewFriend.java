package in.inocular.www.quicksplit;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class NewFriend extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend);


        Bundle extras = getIntent().getExtras();
        final int itemId = extras.getInt("Group_Id");
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_new_friend, menu);
        return true;
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
